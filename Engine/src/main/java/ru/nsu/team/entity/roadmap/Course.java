package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final Road fromRoad;
    private final Road toRoad;
    private int timeLeft;
    private int length;
    private final List<Intersection> intersections;

    public Course(Road fromRoad, Road toRoad) {
        this.fromRoad = fromRoad;
        this.toRoad = toRoad;
        intersections = new ArrayList<>();
    }

    public Course(Road fromRoad, Road toRoad, List<Intersection> intersections) {
        this.fromRoad = fromRoad;
        this.toRoad = toRoad;
        this.intersections = intersections;
    }

    public Road getFromRoad() {
        return fromRoad;
    }

    public Road getToRoad() {
        return toRoad;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTimeLeft() {
        return intersections.stream().map(Intersection::getTimeLeft).min(Integer::compareTo).orElse(0);
    }

    public void setTimeLeft(int timeLeft) {
        for (Intersection i : intersections) {
            i.setTimeLeft(timeLeft);
        }
    }

    public void decreaseTime(int timeUsed) {
        for (Intersection i : intersections) {
            i.setTimeLeft(i.getTimeLeft() - timeUsed);
        }
    }
}
