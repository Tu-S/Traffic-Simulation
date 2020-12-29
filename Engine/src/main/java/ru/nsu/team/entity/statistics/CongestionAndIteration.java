package ru.nsu.team.entity.statistics;

public class CongestionAndIteration {
  private int congestion;
  private int iteration;

  public CongestionAndIteration() {
    congestion = 0;
    iteration = 0;
  }

  public int getCongestion() {
    return congestion;
  }

  public void setCongestion(int congestion) {
    this.congestion = congestion;
  }

  public int getIteration() {
    return iteration;
  }

  public void setIteration(int iteration) {
    this.iteration = iteration;
  }
}
