package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class LaneConfiguration {
    private List<SignConfiguration> signs;

    //private double maxSpeed;
    public List<SignConfiguration> getSigns() {
        return signs;
    }

    /*public LaneConfiguration(double maxSpeed){
            this.maxSpeed = maxSpeed;
    }*/

    public LaneConfiguration() {
    }

    /*public double getMaxSpeed() {
        return maxSpeed;
    }*/
}
