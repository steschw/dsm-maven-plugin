package org.sevntu.maven.plugin.dsm;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.reporting.MavenReport;
import org.codehaus.plexus.PlexusTestCase;
import org.junit.Test;

import java.io.File;
import java.util.Locale;

public class DsmReportMojoTest
    extends AbstractMojoTestCase {


  @Test
  public void testGetDescription() {
    final DsmReportMojo dsmReportMojo = new DsmReportMojo();

    final String description = dsmReportMojo.getDescription(Locale.ENGLISH);
    assertNotNull(description);
  }


  @Test
  public void testGetName() {
    final DsmReportMojo dsmReportMojo = new DsmReportMojo();

    final String name = dsmReportMojo.getName(Locale.ENGLISH);
    assertNotNull(name);
  }


  @Test
  public void testGetSiteRenderer() {
    final DsmReportMojo dsmReportMojo = new DsmReportMojo();

    assertNull(dsmReportMojo.getSiteRenderer());
  }


  @Test
  public void testGetProject() {
    final DsmReportMojo dsmReportMojo = new DsmReportMojo();

    assertNull(dsmReportMojo.getProject());
  }


  @Test
  public void testDsmReportMojo()
      throws Exception {

    final Mojo mojo = lookupMojo("dsm", PlexusTestCase.getBasedir()
        + "/src/test/resources/report-plugin-config.xml");

    final MavenReport reportMojo = (MavenReport) mojo;

    assertTrue("Should be able to generate a report", reportMojo.canGenerateReport());

    assertTrue("Should be an external report", reportMojo.isExternalReport());

    final File outputHtml = new File(reportMojo.getReportOutputDirectory() + File.separator
        + "index.html");
    outputHtml.delete();

    mojo.execute();

    assertTrue("Test for generated html file " + outputHtml, outputHtml.exists());
  }


  @Test
  public void testSkip()
      throws Exception {

    final Mojo mojo = lookupMojo("dsm", PlexusTestCase.getBasedir()
        + "/src/test/resources/report-plugin-config-skip.xml");

    final MavenReport reportMojo = (MavenReport) mojo;

    assertTrue("Should be able to generate a report", reportMojo.canGenerateReport());

    assertTrue("Should be an external report", reportMojo.isExternalReport());

    final File outputHtml = new File(reportMojo.getReportOutputDirectory() + File.separator
        + "index.html");
    outputHtml.delete();

    mojo.execute();

    assertFalse("Test for generated html file " + outputHtml, outputHtml.exists());
  }


}
