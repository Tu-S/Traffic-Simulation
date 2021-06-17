package ru.nsu.team.entity.trafficparticipant;

import java.io.Serializable;

public class TrafficParticipant implements Comparable<TrafficParticipant>, Serializable {

    private PositionOnRoad position;
    private Car car;

    public TrafficParticipant(Car car, PositionOnRoad position) {
        this.car = car;
        this.position = position;
    }

    public PositionOnRoad getPosition() {
        return position;
    }

    public void setPositionOnRoad(PositionOnRoad position) {
        this.position = position;
    }

    public Car getCar() {
        return car;
    }


    @Override
    public int compareTo(TrafficParticipant o) {
        return (int) position.getPosition() - (int) o.position.getPosition();
    }

    @Override
    public String toString() {
        return "{" + car.toString() + ":" + position.toString() + "}";
    }
}
