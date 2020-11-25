package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    double maxSpeed;
    private Road parentRoad;
    private ArrayList<TrafficParticipant> trafficParticipants;
    private ArrayList<Sign> signs;

    public Lane(Road parentRoad) {
        this.signs = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
        this.maxSpeed = Car.DEFAULT_MAX_SPEED;
        this.parentRoad = parentRoad;
    }

    public Lane(double maxSpeed, Road parentRoad) {
        this.signs = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
        this.maxSpeed = maxSpeed;
        this.parentRoad = parentRoad;
    }

    public List<TrafficParticipant> getParticipants() {
        return trafficParticipants;
    }

    public void addSign(Sign sign) {
        signs.add(sign);
    }

    public void addTrafficParticipant(TrafficParticipant car) {
        trafficParticipants.add(car);
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Road getParentRoad() {
        return parentRoad;
    }
}
