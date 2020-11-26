package ru.nsu.team.entity.statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class HeatMap {
    private Map<Integer, SpeedAndIteration> map;
    private List<RoadCongestion> trafficCongestion;
    private Map<Integer, Integer> roadIdMaxSpeed;
    private List<RoadState> roadStates;

    public HeatMap(List<RoadState> roadStates) {
        map = new HashMap<>();
        trafficCongestion = new LinkedList<>();
        roadIdMaxSpeed = new HashMap<>();
        this.roadStates = roadStates;
    }

    /**
     * Calculate roads congestion by given time intervals.
     *
     * @param intervalBegin beginning of time interval
     * @param intervalEnd   end of time interval
     * @return list of elements consisting of the road identifier and its congestion.
     */
    public List<RoadCongestion> calculateCongestion(int intervalBegin, int intervalEnd) {
        map.clear();
        trafficCongestion.clear();
        for (RoadState roadState : roadStates) {
            int roadTime = roadState.getTime();
            int roadId = roadState.getRoad().getId();
            if (roadTime >= intervalBegin && roadTime <= intervalEnd) {
                if (map.containsKey(roadId)) {
                    SpeedAndIteration speedAndIteration = map.get(roadId);
                    speedAndIteration.setSpeed(speedAndIteration.getSpeed()
                            + averageCarsSpeed(roadState.getRoad()));
                    speedAndIteration.setIteration(speedAndIteration.getIteration() + 1);
                    map.put(roadId, speedAndIteration);
                } else {
                    SpeedAndIteration speedAndIteration = new SpeedAndIteration();
                    speedAndIteration.setSpeed(speedAndIteration.getSpeed()
                            + averageCarsSpeed(roadState.getRoad()));
                    speedAndIteration.setIteration(speedAndIteration.getIteration() + 1);
                    map.put(roadId, speedAndIteration);
                    roadIdMaxSpeed.put(roadId, (int) roadState.getRoad().getLaneN(0).getMaxSpeed());
                }
            }
        }
        map.forEach((k, v) -> trafficCongestion.add(
                new RoadCongestion(k, ((v.getSpeed() / v.getIteration()) * 100) / roadIdMaxSpeed.get(k))));
        return trafficCongestion;
    }

    private int averageCarsSpeed(Road road) {
        int averageSpeed = 0;
        int iteration = 0;
        for (TrafficParticipant trafficParticipant : road.getTrafficParticipants()) {
            iteration++;
            averageSpeed += trafficParticipant.getCar().getSpeed();
        }
        return averageSpeed / iteration;
    }
}
