package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.genome.LaneGenome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Lane implements Serializable {
    private Road parentRoad;
    private List<TrafficParticipant> trafficParticipants;
    private LaneGenome genome;

    public Lane(Road parentRoad) {
        this.trafficParticipants = new ArrayList<>();
        this.parentRoad = parentRoad;
        this.genome = new LaneGenome(Car.DEFAULT_MAX_SPEED);
    }

    public Lane(double maxSpeed, Road parentRoad) {
        this.trafficParticipants = new ArrayList<>();
        this.parentRoad = parentRoad;
        this.genome = new LaneGenome(maxSpeed);
    }

    public void setParentRoad(Road parentRoad) {
        this.parentRoad = parentRoad;
    }

    public void setTrafficParticipants(List<TrafficParticipant> trafficParticipants) {
        this.trafficParticipants = trafficParticipants;
    }

    public LaneGenome getGenome() {
        return genome;
    }

    public List<TrafficParticipant> getParticipants() {
        return trafficParticipants;
    }

    public void addTrafficParticipant(TrafficParticipant car) {
        trafficParticipants.add(car);
    }

    public void removeTrafficParticipant(TrafficParticipant car) {
        trafficParticipants.remove(car);
    }

    public double getMaxSpeed() {
        return this.genome.getMaxSpeed();
    }

    public void setMaxSpeed(double maxSpeed) {
        this.genome.setMaxSpeed(maxSpeed);
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

    @Override
    public String toString() {
        return parentRoad.toString() + ":" + parentRoad.getLanes().indexOf(this);
    }
}
