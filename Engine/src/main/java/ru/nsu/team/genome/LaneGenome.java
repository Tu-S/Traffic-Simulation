package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Lane;

import java.io.Serializable;

public class LaneGenome implements Serializable {

    private double maxSpeed;

    public LaneGenome(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }


    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
