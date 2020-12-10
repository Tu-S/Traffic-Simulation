package ru.nsu.team.entity.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.team.entity.statistics.HeatMap;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.entity.statistics.TrafficStatistics;

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
