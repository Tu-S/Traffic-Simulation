package ru.nsu.team.entity.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrafficStatistics {

  private List<RoadState> roadStatistics;

  public TrafficStatistics() {
    roadStatistics = Collections.synchronizedList(new ArrayList<>());
  }

  public void addRoadState(RoadState roadState) {
    roadStatistics.add(roadState);
  }

  public int getStaticsNumber() {
    return roadStatistics.size();
  }

  public RoadState getRoadStaticsN(int n) {
    return roadStatistics.get(n);
  }

  public List<RoadState> getRoadStatistics() {
    return roadStatistics;
  }
}
