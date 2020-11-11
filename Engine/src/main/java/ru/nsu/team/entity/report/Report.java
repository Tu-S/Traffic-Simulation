package ru.nsu.team.entity.report;

import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.TrafficStatistics;

import java.util.List;

public class Report {
  private TrafficStatistics trafficStatistics;

  public Report(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
  }

  // make report from traffic statistics
  public void makeReport() {
    List<RoadState> roadStates = trafficStatistics.getRoadStatistics();
    // prepare heatmap data for editor:
    // ...
    // HeatMap heatMap = new HeatMap(roadStates, begin, end);
    // ...
  }
}
