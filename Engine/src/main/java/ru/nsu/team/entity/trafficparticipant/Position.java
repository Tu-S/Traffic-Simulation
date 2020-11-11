package ru.nsu.team.entity.trafficparticipant;

public class Position {
    private int currentPlaceOfInterest;
    private int currentRoad;

    public Position() {
    }

    public Position(int currentPlaceOfInterest, int currentRoad) {
        this.currentPlaceOfInterest = currentPlaceOfInterest;
        this.currentRoad = currentRoad;
    }

    public void setCurrentPlaceOfInterest(int x) {
        this.currentPlaceOfInterest = x;
    }

    public void setCurrentRoad(int currentRoad) {
        this.currentRoad = currentRoad;
    }

    public int getCurrentPlaceOfInterest() {
        return currentPlaceOfInterest;
    }

    public int getCurrentRoad() {
        return currentRoad;
    }
}
