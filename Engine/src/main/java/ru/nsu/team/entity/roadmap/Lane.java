package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Lane {
    double maxSpeed;
    private Road parentRoad;
    private List<TrafficParticipant> trafficParticipants;

    public Lane(Road parentRoad) {
        this.trafficParticipants = new ArrayList<>();
        this.maxSpeed = Car.DEFAULT_MAX_SPEED;
        this.parentRoad = parentRoad;
    }

    public Lane(double maxSpeed, Road parentRoad) {
        this.trafficParticipants = new ArrayList<>();
        this.maxSpeed = maxSpeed;
        this.parentRoad = parentRoad;
    }

    public List<TrafficParticipant> getParticipants() {
        return trafficParticipants;
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

    public boolean leadsTo(Road road) {
        return parentRoad.getExitNode().getCourses().stream().anyMatch(c -> c.getFromLane() == this && c.getToLane().parentRoad == road);
    }

    public boolean leadsTo(Lane lane) {
        return parentRoad.getExitNode().getCourses().stream().anyMatch(c -> c.getFromLane() == this && c.getToLane() == lane);
    }
}
