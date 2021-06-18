package ru.nsu.team.entity.roadmap;

import java.io.Serializable;

public class Intersection implements Serializable {
    private int timeLeft;

    //TODO design class :-)
    public Intersection(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return this.timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
