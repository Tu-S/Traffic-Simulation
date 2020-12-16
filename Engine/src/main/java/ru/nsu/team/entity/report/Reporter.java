package ru.nsu.team.entity.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.team.entity.statistics.HeatMap;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.entity.statistics.TrafficStatistics;

public class Reporter {
  private TrafficStatistics trafficStatistics;
  private Map<Timeline, List<RoadCongestion>> roadTimelineToCongestion;

  public Reporter(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
    roadTimelineToCongestion = new HashMap<>();
  }

  public List<RoadCongestion> makeReport(Timeline timeline) {
    List<RoadState> roadStates = trafficStatistics.getRoadStatistics();
    HeatMap heatMap = new HeatMap(roadStates);
    List<RoadCongestion> roadCongestion = heatMap.calculateCongestion(timeline);
    roadTimelineToCongestion.put(timeline, roadCongestion);
    return roadCongestion;
  }

  public Map<Timeline, List<RoadCongestion>> getRoadTimelineToCongestion() {
    return roadTimelineToCongestion;
  }
}
