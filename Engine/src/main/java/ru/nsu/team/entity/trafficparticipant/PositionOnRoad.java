package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Road;

import java.io.Serializable;

public class PositionOnRoad implements Serializable {
    private int currentLane;
    private double position;
    private Road currentRoad;

    public PositionOnRoad(Road road, double distanceFromRoadExit, int lane) {
        this.currentRoad = road;
        this.position = distanceFromRoadExit;
        this.currentLane = lane;
    }

    public int getCurrentLaneId() {
        return currentLane;
    }

    public double getPosition() {
        return position;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentLane(int currentLane) {
        this.currentLane = currentLane;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void setCurrentRoad(Road road) {
        this.currentRoad = road;
    }

    public Lane getCurrentLane() {
        return currentRoad.getLaneN(currentLane);
    }

    @Override
    public String toString() {
        return currentRoad.toString() + ":" + currentLane + ":" + position;
    }
}
