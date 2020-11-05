package ru.nsu.team.entity.roadmap;

public class Course {

    private Road fromRoad;
    private Road toRoad;
    private int timeLeft;
    private int length;

    public Road getFromRoad() {
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

    public void setFromRoad(Road fromRoad) {
        this.fromRoad = fromRoad;
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
