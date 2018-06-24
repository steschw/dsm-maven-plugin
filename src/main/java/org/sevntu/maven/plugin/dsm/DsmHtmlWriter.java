package org.sevntu.maven.plugin.dsm;

import com.google.common.base.Strings;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.hjug.dtangler.core.analysisresult.AnalysisResult;
import org.hjug.dtangler.core.analysisresult.Violation;
import org.hjug.dtangler.core.analysisresult.Violation.Severity;
import org.hjug.dtangler.core.dsm.Dsm;
import org.hjug.dtangler.core.dsm.DsmCell;
import org.hjug.dtangler.core.dsm.DsmRow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Generate site content and write to HTML file.
 * 
 * @author Yuri Balakhonov
 * @author Ilja Dubinin
 * @author Stefan Schweitzer
 */
class DsmHtmlWriter {

  public final static String FILE_FORMAT = ".html";
  public final static String IMAGE_FOLDER_NAME = "images";
  public final static String CSS_FOLDER_NAME = "css";
  public final static String FTL_CLASSES_PAGE = "classes_page.ftl";
  public final static String FTL_PACKAGES_PAGE = "packages_page.ftl";
  public final static String FTL_PACKAGES_MENU = "packages_menu.ftl";

  /**
   * Path to your site report dir
   */
  private final String reportSiteDirectory;

  /**
   * Obfuscate package names.
   */
  private final boolean obfuscatePackageNames;

  /**
   * Freemarker configuration
   */
  private final Configuration configuration;

  /**
   * @param aReportSiteDirectory
   * @param obfuscate
   */
  DsmHtmlWriter(final String aReportSiteDirectory, final boolean obfuscate) {

    if (Strings.isNullOrEmpty(aReportSiteDirectory)) {
      throw new IllegalArgumentException(
          "Path to the report directory should not be null or empty");
    }

    obfuscatePackageNames = obfuscate;
    reportSiteDirectory = aReportSiteDirectory;

    new File(reportSiteDirectory).mkdirs();

    configuration = createConfiguration();
  }

  private static Configuration createConfiguration() {
    final Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
    cfg.setClassForTemplateLoading(DsmHtmlWriter.class, File.separator + "templates");
    cfg.setDefaultEncoding("UTF-8");
    cfg.setOutputEncoding("UTF-8");
    cfg.setLocale(Locale.ENGLISH);
    return cfg;
  }

  /**
   * @param aDataModel
   * @param aTemplateName
   * @return
   * @throws Exception
   */
  private ByteArrayOutputStream renderTemplate(final Map<String, Object> aDataModel,
      final String aTemplateName)
      throws Exception {

    final Template tpl = configuration.getTemplate(aTemplateName);

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final Writer outputStreamWriter = new OutputStreamWriter(outputStream);

    tpl.process(aDataModel, outputStreamWriter);
    return outputStream;
  }

  /**
   * @param byteOutputStream
   * @param aFileName
   * @throws Exception
   */
  private void writeStreamToFile(final ByteArrayOutputStream byteOutputStream, final String aFileName)
      throws Exception {

    final String filePath = reportSiteDirectory + File.separator + aFileName + FILE_FORMAT;

    try (final OutputStream outputStream = new FileOutputStream(filePath)) {
      outputStream.write(byteOutputStream.toByteArray());
    }
  }

  /**
   * Print navigation on site by packages
   *
   * @param aPackageNames
   *            List of package names
   */
  public void printDsmPackagesNavigation(final List<String> aPackageNames)
      throws Exception {

    if (aPackageNames == null) {
      throw new IllegalArgumentException("List of package names should not be null");
    }

    aPackageNames.sort(Comparator.naturalOrder());

    final Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("aPackageNames", aPackageNames);

    writeModelToFile("packages", FTL_PACKAGES_MENU, dataModel);

    try (final ByteArrayOutputStream outputStream = renderTemplate(dataModel, FTL_PACKAGES_MENU)) {
      writeStreamToFile(outputStream, "packages");
    }
  }

  /**
   * Calculate max count of dependencies in Dsm structure
   *
   * @param dsm
   *            Dsm structure
   * @return
   *            Max count
   */
  private static int getMaxDependencyCount(final Dsm dsm) {
    final OptionalInt maxCount = dsm.getRows().stream()
        .flatMap(row -> row.getCells().stream())
        .mapToInt(DsmCell::getDependencyWeight)
        .max();
    return maxCount.orElse(0);
  }

  /**
   * Print dependency structure matrix
   *
   * @param aDsm
   *            Dsm structure
   * @param aAnalysisResult
   *            Analysis structure
   * @param aName
   *            Name of package
   */
  public void printDsm(final Dsm aDsm, final AnalysisResult aAnalysisResult, final String aName,
      final String templateName, final boolean showNumbers, final boolean showColNames)
      throws Exception {

    if (aDsm == null) {
      throw new IllegalArgumentException("DSM structure should not be null");
    }
    if (aAnalysisResult == null) {
      throw new IllegalArgumentException("Analysis structure should not be null");
    }
    if (Strings.isNullOrEmpty(aName)) {
      throw new IllegalArgumentException("Title of DSM should not be empty");
    }

    final List<DsmRowModel> dsmRowsData = new ArrayList<>();
    final List<String> names = new ArrayList<>();
    final int[] numberOfClassesInPackage = new int[aDsm.getRows().size()];

    final int maxDependencyCount = getMaxDependencyCount(aDsm);

    for (int packageIndex = 0; packageIndex < aDsm.getRows().size(); packageIndex++) {
      final DsmRow dsmRow = aDsm.getRows().get(packageIndex);

      final String packageName = dsmRow.getDependee().getDisplayName();
      names.add(packageName);
      numberOfClassesInPackage[packageIndex] = dsmRow.getDependee().getContentCount();

      final String truncatedPackageName = truncatePackageName(packageName, 20);
      final List<DsmCellModel> cells = new ArrayList<>();
      for (final DsmCell dep : dsmRow.getCells()) {
        final int dependencyCount = dep.getDependencyWeight();
        final Set<Violation> violations = aAnalysisResult.getViolations(dep.getDependency(), Severity.error);
        final int cycleCount = violations.size();
        double dependencyRatio = 0.;
        if (maxDependencyCount != 0) {
          dependencyRatio = (double) dependencyCount / maxDependencyCount;
        }
        final DsmCellModel cell = new DsmCellModel(dep.isValid(), dependencyCount, cycleCount, dependencyRatio);
        cells.add(cell);
      }

      final DsmRowModel rowData = new DsmRowModel(packageIndex + 1, packageName,
          truncatedPackageName, cells);
      dsmRowsData.add(rowData);
    }

    final Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("showNumbers", showNumbers);
    dataModel.put("showColNames", showColNames);
    dataModel.put("title", aName);
    dataModel.put("rows", dsmRowsData);
    dataModel.put("names", names);
    dataModel.put("numberOfClasses", numberOfClassesInPackage);
    writeModelToFile(aName, templateName, dataModel);
  }

  /**
   * Write model to output file.
   * @param aFileName
   * @param aTemplateName
   * @param aDataModel
   * @throws Exception
   */
  private void writeModelToFile(final String aFileName, final String aTemplateName,
      final Map<String, Object> aDataModel)
      throws Exception {

    try (final ByteArrayOutputStream outputStream = renderTemplate(aDataModel, aTemplateName)) {
      writeStreamToFile(outputStream, aFileName);
    }
  }

  private String truncatePackageName(final String name, final int length) {
    String truncatedName = name;
    if (obfuscatePackageNames) {
      final String[] nameTokens = name.split("\\.");
      for (int numberOfToken = 0; numberOfToken < nameTokens.length
          && truncatedName.length() > length; numberOfToken++) {
        final String currentToken = nameTokens[numberOfToken];
        truncatedName = truncatedName.replace(currentToken + ".", currentToken.substring(0, 1) + ".");
      }
    } else {
      if (name.length() - 2 > length) {
        truncatedName = ".." + name.substring(name.length() - length - 2);
      }
    }
    return truncatedName;
  }

}
