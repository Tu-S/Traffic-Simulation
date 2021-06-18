package ru.nsu.team.entity.statistics;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.roadmap.Position;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.io.Serializable;

public class CarState implements Serializable {
    private double speed;
    private final int id;
    private double position;
    private long time;
    private int currentLane;
    private int currentRoad;
    private Position from;
    private Position to;
    private boolean draw;

    public CarState(TrafficParticipant trafficParticipant, long time, boolean draw) {
        Car car = trafficParticipant.getCar();
        PositionOnRoad positionOnRoad = trafficParticipant.getPosition();
        speed = car.getSpeed();
        id = car.getId();
        position = positionOnRoad.getCurrentRoad().getLength()-positionOnRoad.getPosition();
        this.time = time;
        currentLane = positionOnRoad.getCurrentLaneId();
        Road road = positionOnRoad.getCurrentRoad();
        currentRoad = road.getId();
        from = road.getFrom().getPosition();
        to = road.getTo().getPosition();
        this.draw = draw;
    }

    public double getSpeed() {
        return speed;
    }

    public int getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }

    public long getTime() {
        return time;
    }

    public int getCurrentLane() {
        return currentLane;
    }

    public int getCurrentRoad() {
        return currentRoad;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}
