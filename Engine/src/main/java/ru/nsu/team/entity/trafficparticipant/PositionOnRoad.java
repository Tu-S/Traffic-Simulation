package ru.nsu.team.entity.trafficparticipant;

public class PositionOnRoad {
    private int currentLane;
    private int position;
    private int currentRoad;
    private int currentPlaceOfInterest;

    public int getCurrentLane() {
        return currentLane;
    }

    public int getCurrentPlaceOfInterest() {
        return currentPlaceOfInterest;
    }

    public int getPosition() {
        return position;
    }
    public int getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentLane(int currentLane) {
        this.currentLane = currentLane;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCurrentRoad(int currentRoad) {
        this.currentRoad = currentRoad;
    }

    public void setCurrentPlaceOfInterest(int currentPlaceOfInterest) {
        this.currentPlaceOfInterest = currentPlaceOfInterest;
    }
}
