package org.sevntu.maven.plugin.dsm;

import java.util.List;

/**
 * 
 * @author Yuri Balakhonov
 * @author Ilja Dubinin
 */
public class DsmRowModel {

  /**
   * Package or class name
   */
  private final String name;

  private final String obfuscatedPackageName;

  /**
   * List of cells. Contain number of cells with other classes/packages
   */
  private final List<DsmCellModel> cells;

  /**
   * Package or class index in dsm
   */
  private int positionIndex;

  public DsmRowModel(final int positionIndex,
      final String name, final String obfuscatedPackageName,
      final List<DsmCellModel> cells) {
    this.positionIndex = positionIndex;
    this.name = name;
    this.obfuscatedPackageName = obfuscatedPackageName;
    this.cells = cells;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return truncated name
   */
  public String getObfuscatedPackageName() {
    return obfuscatedPackageName;
  }

  /**
   * @return the cells
   */
  public List<DsmCellModel> getCells() {
    return cells;
  }

  /**
   * @return the positionIndex
   */
  public int getPositionIndex() {
    return positionIndex;
  }

}
