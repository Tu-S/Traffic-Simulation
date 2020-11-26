package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;

public class PositionOnRoadConfiguration {
    private int currentLane;
    private double position;
    private int currentRoad;

    public int getCurrentLane() {
        return currentLane;
    }

    public double getPosition() {
        return position;
    }

    public int getCurrentRoad() {
        return currentRoad;
    }

    public PositionOnRoadConfiguration(int currentRoad, int currentLane, double position) {
        this.currentRoad = currentRoad;
        this.currentLane = currentLane;
        this.position = position;


    }
}
