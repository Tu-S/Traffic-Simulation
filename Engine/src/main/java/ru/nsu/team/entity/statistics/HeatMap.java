package ru.nsu.team.entity.statistics;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.*;

public class HeatMap {
  private Map<RoadState, SpeedAndIteration> map;

  public HeatMap(List<RoadState> roadStates, int intervalBegin, int intervalEnd) {
    map = new HashMap<>();
    // for each road if time >= intervalBegin && time <= intervalEnd, calculate traffic congestion:
    // ...

    for (RoadState road : roadStates) {
      int roadTime = road.getTime();
      if (roadTime >= intervalBegin && roadTime <= intervalEnd) {
        if (map.containsKey(road)) {

        } else {
          SpeedAndIteration speedAndIteration = new SpeedAndIteration();
          // calculate average cars speed
          //speedAndIteration.setSpeed();
          //map.put(road, )
        }
//        Optional<RoadIdMaxSpeed> matchedEntry =
//            map.keySet().stream().
//                    filter(element -> element.getId() == road.getId()).findAny();
//
//        if (matchedEntry.isPresent()) {
//          RoadIdMaxSpeed roadIdMaxSpeed = matchedEntry.get();
//
//        }
      }
    }
  }

  private int averageCarsSpeed(Road road) {
    List<TrafficParticipant> trafficParticipants = road.getTrafficParticipants();
    int averageSpeed = 0;
    return averageSpeed;
  }
}
