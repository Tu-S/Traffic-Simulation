package ru.nsu.team.entity.report;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import ru.nsu.team.entity.statistics.HeatMap;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.entity.statistics.TrafficStatistics;
import ru.nsu.team.other.KeyValuePair;

public class Reporter implements Serializable {
  private TrafficStatistics trafficStatistics;
  private List<KeyValuePair<Timeline, List<RoadCongestion>>> heatmap;

  public Reporter(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
    heatmap = new LinkedList<>();
  }

  public void makeReport(Timeline timeline, long windowSize) {
    List<RoadState> roadStates = trafficStatistics.getRoadStatistics();
    HeatMap heatMap = new HeatMap(roadStates);
    List<KeyValuePair<Timeline, List<RoadCongestion>>> roadTimelineToCongestion  = new LinkedList<>();
    long timeBorder = timeline.getEnd();
    long timeLeft = timeline.getBegin();
    long timeRight = timeLeft + windowSize;
    for (; timeRight <= timeBorder; timeLeft += windowSize, timeRight += windowSize) {
      Timeline timeSplit = new Timeline(timeLeft, timeRight);
      roadTimelineToCongestion.add(new KeyValuePair<>(timeSplit, heatMap.calculateCongestion(timeSplit)));
    }
    if (timeLeft < timeBorder) {
      Timeline timeSplit = new Timeline(timeLeft, timeBorder);
      roadTimelineToCongestion.add(new KeyValuePair<>(timeSplit, heatMap.calculateCongestion(timeSplit)));
    }
    List<RoadCongestion> roadCongestion = heatMap.calculateCongestion(timeline);
    roadTimelineToCongestion.add(new KeyValuePair<>(timeline, roadCongestion));
    heatmap = roadTimelineToCongestion;
  }

  public TrafficStatistics getTrafficStatistics() {
    return trafficStatistics;
  }

  public void setTrafficStatistics(TrafficStatistics trafficStatistics) {
    this.trafficStatistics = trafficStatistics;
  }

  public List<KeyValuePair<Timeline, List<RoadCongestion>>> getHeatmap() {
    return heatmap;
  }

  public void setHeatmap(List<KeyValuePair<Timeline, List<RoadCongestion>>> heatmap) {
    this.heatmap = heatmap;
  }
}
