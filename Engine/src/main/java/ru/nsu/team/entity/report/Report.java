package ru.nsu.team.entity.report;

import ru.nsu.team.entity.statistics.HeatMap;
import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.TrafficStatistics;

import java.util.List;

public class Report {
  private TrafficStatistics trafficStatistics;

  public Report(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
  }

  public void makeReport() {
    List<RoadState> roadStates = trafficStatistics.getRoadStatistics();
    HeatMap heatMap = new HeatMap(roadStates);
    // List<RoadCongestion> roadCongestionList = heatMap.calculateCongestion(begin, end);
  }
}
