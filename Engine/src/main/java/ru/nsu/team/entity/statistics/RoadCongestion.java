package ru.nsu.team.entity.statistics;

public class RoadCongestion {
  private int roadId;
  private int congestion;

  public RoadCongestion(int roadId, int congestion) {
    this.roadId = roadId;
    this.congestion = congestion;
  }

  public int getRoadId() {
    return roadId;
  }

  public int getCongestion() {
    return congestion;
  }
}
