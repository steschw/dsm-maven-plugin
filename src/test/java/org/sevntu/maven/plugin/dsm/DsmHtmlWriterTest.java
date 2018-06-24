package org.sevntu.maven.plugin.dsm;

import org.hjug.dtangler.core.analysisresult.AnalysisResult;
import org.hjug.dtangler.core.dsm.Dsm;
import org.hjug.dtangler.core.dsm.DsmRow;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class DsmHtmlWriterTest {

  @Test
  public void dsmHtmlWriterTest() {
    Exception ex = null;
    try {
      new DsmHtmlWriter(null, false);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("Path to the report directory should not be null or empty", e.getMessage());
    }
    assertNotNull(ex);

    ex = null;
    try {
      new DsmHtmlWriter("", false);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("Path to the report directory should not be null or empty", e.getMessage());
    }
    assertNotNull(ex);
  }

  @Test
  public void printDsmTest() {
    final DsmHtmlWriter dsmHtmlWriter = new DsmHtmlWriter("target/testDir", false);
    Exception ex = null;
    try {
      dsmHtmlWriter.printDsm(null, null, null, null, false, false);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("DSM structure should not be null", e.getMessage());
    }
    assertNotNull(ex);

    ex = null;
    try {
      dsmHtmlWriter.printDsm(new Dsm(new ArrayList<DsmRow>()), null, null, null, false, false);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("Analysis structure should not be null", e.getMessage());
    }
    assertNotNull(ex);

    ex = null;
    try {
      dsmHtmlWriter.printDsm(new Dsm(new ArrayList<>()), new AnalysisResult(
          new HashMap<>(), new HashSet<>(), true),
          null, null, false, false);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("Title of DSM should not be empty", e.getMessage());
    }
    assertNotNull(ex);
  }


  @Test
  public void printNavigateDsmPackagesTest() {
    final DsmHtmlWriter dsmHtmlWriter = new DsmHtmlWriter("target/testDir", false);
    Exception ex = null;
    try {
      dsmHtmlWriter.printDsmPackagesNavigation(null);
      Assert.fail();
    } catch (Exception e) {
      ex = e;
      assertEquals("List of package names should not be null", e.getMessage());
    }
    assertNotNull(ex);
  }

}
