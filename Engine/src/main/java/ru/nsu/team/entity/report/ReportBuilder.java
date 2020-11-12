package ru.nsu.team.entity.report;


import ru.nsu.team.entity.statistics.TrafficStatistics;

public class ReportBuilder {

  public Report getReport(TrafficStatistics trafficStatistics) {
    return new Report(trafficStatistics);
  }

}
