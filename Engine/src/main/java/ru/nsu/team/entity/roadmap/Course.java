package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;

public class Course {

    private Road fromRoad;
    private Road toRoad;
    private int timeLeft;
    private int length;

    public Course(Road fromRoad, Road toRoad){
        this.fromRoad = fromRoad;
        this.toRoad = toRoad;
    }

    public Road getFromRoads() {
        return fromRoad;
    }

    public Road getToRoad() {
        return toRoad;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getLength() {
        return length;
    }

    public void setFromRoads(Road fromRoads) {
        this.fromRoad = fromRoads;
    }

    public void setToRoad(Road toRoad) {
        this.toRoad = toRoad;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
