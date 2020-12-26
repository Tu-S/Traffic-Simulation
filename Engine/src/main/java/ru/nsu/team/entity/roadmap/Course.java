package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final Lane fromLane;
    private final Lane toLane;
    private int length;
    private final List<Intersection> intersections;

    public Course(Lane fromRoad, Lane toLane) {
        this.fromLane = fromRoad;
        this.toLane = toLane;
        intersections = new ArrayList<>();
    }

    public Course(Lane fromLane, Lane toLane, List<Intersection> intersections, int length) {
        this.fromLane = fromLane;
        this.toLane = toLane;
        this.intersections = intersections;
    }

    public Lane getFromLane() {
        return fromLane;
    }

    public Lane getToLane() {
        return toLane;
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

    public void resetTimeLeft(int timeLeft) {
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
