package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.RoadState;
import ru.nsu.team.entity.statistics.TrafficStatistics;

import java.util.ArrayList;
import java.util.List;

public class Playback {
  private List<RoadState> roadStates;

  public Playback(TrafficStatistics trafficStatistics) {
    roadStates = new ArrayList<>(trafficStatistics.getRoadStatistics());
  }

  public List<RoadState> getRoadStates() {
    return roadStates;
  }

  public void setRoadStates(List<RoadState> roadStates) {
    this.roadStates = roadStates;
  }
}
