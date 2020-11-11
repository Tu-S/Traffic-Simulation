package ru.nsu.team.entity.trafficparticipant;

public class TrafficParticipant {

    private Position position;
    private int laneId;
    private Car car;

    public TrafficParticipant(Car car, Position pos, int laneId) {
        this.car = car;
        this.position = pos;
        this.laneId = laneId;
    }

    public Position getPosition() {
        return position;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public Car getCar() {
        return car;
    }


}
