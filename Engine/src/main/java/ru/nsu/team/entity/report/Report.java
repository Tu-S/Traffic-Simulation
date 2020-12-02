package ru.nsu.team.entity.report;

import ru.nsu.team.entity.statistics.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {
  private TrafficStatistics trafficStatistics;
  private Map<Timeline, List<RoadCongestion>> roadCongestion;

  public Report(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
    roadCongestion = new HashMap<>();
  }

  public void makeReport(Timeline timeline) {
    List<RoadState> roadStates = trafficStatistics.getRoadStatistics();
    HeatMap heatMap = new HeatMap(roadStates);
    roadCongestion.put(timeline, heatMap.calculateCongestion(timeline));
  }

  public Map<Timeline, List<RoadCongestion>> getRoadCongestion() {
    return roadCongestion;
  }
}
