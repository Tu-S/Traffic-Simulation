package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Road;

public class PositionOnRoad {
    private int currentLane;
    private double position;
    private Road currentRoad;

    public PositionOnRoad(Road road, double distanceFromRoadExit, int lane) {
        this.currentRoad = road;
        this.position = distanceFromRoadExit;
        this.currentLane = lane;
    }

    public int getCurrentLane() {
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

    @Override
    public String toString() {
        return "{" + currentRoad.toString() + ":" + currentLane + ":" + position + "}";
    }
}
