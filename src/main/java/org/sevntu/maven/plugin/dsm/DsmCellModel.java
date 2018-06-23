package org.sevntu.maven.plugin.dsm;

public class DsmCellModel {

  private final boolean valid;

  private final int dependencyCount;

  private final boolean cycle;

  public DsmCellModel(final boolean valid, final int dependencyCount, final boolean cycle) {
    this.valid = valid;
    this.dependencyCount = dependencyCount;
    this.cycle = cycle;
  }

  public boolean isValid() {
    return valid;
  }

  public int getDependencyCount() {
    return dependencyCount;
  }

  public boolean isCycle() {
    return cycle;
  }

  @Override
  public String toString() {
    if (!valid) {
      return "X";
    } else if (dependencyCount == 0) {
      return "";
    } else {
      return dependencyCount + (cycle ? "C" : "");
    }
  }

}
