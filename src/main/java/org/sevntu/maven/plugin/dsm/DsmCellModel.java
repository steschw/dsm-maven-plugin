package org.sevntu.maven.plugin.dsm;

public class DsmCellModel {

  private final boolean valid;

  private final int dependencyCount;

  private final int cycleCount;

  private final double dependencyRatio;

  public DsmCellModel(final boolean valid, final int dependencyCount, final int cycleCount, final double dependencyRatio) {
    this.valid = valid;
    this.dependencyCount = dependencyCount;
    this.cycleCount = cycleCount;
    this.dependencyRatio = dependencyRatio;
  }

  public boolean isValid() {
    return valid;
  }

  public int getDependencyCount() {
    return dependencyCount;
  }

  public int getCycleCount() {
    return cycleCount;
  }

  public double getDependencyRatio() {
    return dependencyRatio;
  }

  @Override
  public String toString() {
    if (!valid) {
      return "X";
    } else if (dependencyCount == 0) {
      return "";
    } else {
      return dependencyCount + ((cycleCount > 0) ? "C" : "");
    }
  }

}
