package ru.nsu.team.entity.statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class HeatMap {
  private Map<Integer, CongestionAndIteration> map;
  private List<RoadCongestion> trafficCongestion;
  private List<RoadState> roadStates;

  public HeatMap(List<RoadState> roadStates) {
    map = new HashMap<>();
    trafficCongestion = new LinkedList<>();
    this.roadStates = roadStates;
  }

  /**
   * Calculate roads congestion by given time intervals.
   *
   * @param timeline timeline
   * @return list of elements consisting of the road identifier and its congestion.
   */
  public List<RoadCongestion> calculateCongestion(Timeline timeline) {
    map.clear();
    trafficCongestion.clear();
    int intervalBegin = timeline.getBegin();
    int intervalEnd = timeline.getEnd();
    for (RoadState roadState : roadStates) {
      int roadTime = roadState.getTime();
      int roadId = roadState.getRoad().getId();
      if (roadTime >= intervalBegin && roadTime <= intervalEnd) {
        if (map.containsKey(roadId)) {
          CongestionAndIteration congestionAndIteration = map.get(roadId);
          congestionAndIteration.setCongestion(congestionAndIteration.getCongestion()
              + averageRoadCongestion(roadState.getRoad()));
          congestionAndIteration.setIteration(congestionAndIteration.getIteration() + 1);
          map.put(roadId, congestionAndIteration);
        } else {
          CongestionAndIteration congestionAndIteration = new CongestionAndIteration();
          congestionAndIteration.setCongestion(congestionAndIteration.getCongestion() +
              averageRoadCongestion(roadState.getRoad()));
          congestionAndIteration.setIteration(congestionAndIteration.getIteration() + 1);
          map.put(roadId, congestionAndIteration);
        }
      }
    }
    map.forEach((k, v) -> trafficCongestion.add(
        new RoadCongestion(k, v.getCongestion() / v.getIteration())));
    return trafficCongestion;
  }

  private int averageCarsSpeed(List<TrafficParticipant> trafficParticipants) {
    int averageSpeed = 0;
    int iteration = 0;
    for (TrafficParticipant trafficParticipant : trafficParticipants) {
      iteration++;
      averageSpeed += trafficParticipant.getCar().getSpeed();
    }
    return averageSpeed / iteration;
  }

  private int averageRoadCongestion(Road road) {
    int fullCongestion = 0;
    int iteration = 0;
    for (Lane lane : road.getLanes()) {
      int percentage = (int)((averageCarsSpeed(lane.getParticipants()) * 100) / lane.getMaxSpeed());
      iteration++;
      fullCongestion += percentage;
    }
    return fullCongestion / iteration;
  }
}
