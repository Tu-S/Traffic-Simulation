package ru.nsu.team.entity.report;


import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.TrafficStatistics;

public class ReporterBuilder {
  private TrafficStatistics trafficStatistics;

  public ReporterBuilder() {
    this.trafficStatistics = new TrafficStatistics();
  }

  public Reporter getReporter() {
    return new Reporter(trafficStatistics);
  }

  public void addStatistics(RoadState roadState) {
    trafficStatistics.addRoadState(roadState);
  }
}
