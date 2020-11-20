package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Road;

public class PositionOnRoad {
    private int currentLane;
    private int position;
    private Road currentRoad;

    public PositionOnRoad(Road road, int distanceFromRoadExit, int lane) {
        this.currentRoad = road;
        this.position = distanceFromRoadExit;
        this.currentLane = lane;
    }

    public int getCurrentLane() {
        return currentLane;
    }

    public int getPosition() {
        return position;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentLane(int currentLane) {
        this.currentLane = currentLane;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCurrentRoad(Road road) {
        this.currentRoad = road;
    }
}
