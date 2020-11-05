package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class TrafficLight {
    private int greenDuration;
    private int redDuration;
    private Color currentColor;
    private int currentState;

    public TrafficLight(int greenDuration, int redDuration) {
        this.greenDuration = greenDuration;
        this.redDuration = redDuration;

    }

    public int getGreenDuration() {
        return greenDuration;
    }

    public int getRedDuration() {
        return redDuration;
    }

    public int getCurrentState() {
        return currentState;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public int nextGreen(int time) {
        time = 0;
        return time;
    }

    enum Color {GREEN, RED, YELLOW}
}
