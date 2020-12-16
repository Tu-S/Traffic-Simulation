package ru.nsu.team.entity.report;


import ru.nsu.team.entity.statistics.TrafficStatistics;

public class ReportBuilder {

  public Reporter getReport(TrafficStatistics trafficStatistics) {
    return new Reporter(trafficStatistics);
  }

}
