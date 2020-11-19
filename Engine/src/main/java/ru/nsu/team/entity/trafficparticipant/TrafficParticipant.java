package ru.nsu.team.entity.trafficparticipant;

public class TrafficParticipant {

    private PositionOnRoad position;
    private Car car;

    public TrafficParticipant(Car car) {
        this.car = car;
        this.position = new PositionOnRoad();
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


}
