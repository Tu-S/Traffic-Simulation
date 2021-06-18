package ru.nsu.team.entity.report;

public class EnteringState {

    private double position;
    private int timestamp;

    public EnteringState() {
    }

    public EnteringState(double position, int timestamp) {
        this.position = position;
        this.timestamp = timestamp;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
