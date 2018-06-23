package org.sevntu.maven.plugin.dsm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DsmRowModelTest {

  @Test
  public void dsmRowModelTest() {
    final List<DsmCellModel> cells = new ArrayList<>();
    cells.add(new DsmCellModel(true, 1, false));
    cells.add(new DsmCellModel(true, 2, false));
    final DsmRowModel dsmRowData = new DsmRowModel(1, "row name", "", cells);

    assertEquals("row name", dsmRowData.getName());
    assertEquals(1, dsmRowData.getCells().get(0).getDependencyCount());
    assertEquals(2, dsmRowData.getCells().get(1).getDependencyCount());
  }

  @Test
  public void testObfuscation() {
    final DsmRowModel rowModel = new DsmRowModel(1, "com.puppycrawl.tools.checkstyle.checks.coding",
        "c.p.t.c.c.coding", Collections.emptyList());
    assertEquals("c.p.t.c.c.coding", rowModel.getObfuscatedPackageName());
  }

  @Test
  public void testCutNames() {
    final DsmRowModel rowModel = new DsmRowModel(1, "com.puppycrawl.tools.checkstyle.checks.coding",
        "...puppycrawl.tools.checkstyle.checks.coding", Collections.emptyList());
    assertEquals("...puppycrawl.tools.checkstyle.checks.coding", rowModel.getObfuscatedPackageName());
  }

}
