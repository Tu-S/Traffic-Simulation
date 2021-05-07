package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Lane;

public class LaneGenome {

    private double maxSpeed;

    public LaneGenome(Lane lane){
        this.maxSpeed = lane.getMaxSpeed();
    }


    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
