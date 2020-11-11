package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Position;

public class TrafficParticipantConfiguration {
    private Position position;
    private int laneId;
    private CarConfiguration car;

    public TrafficParticipantConfiguration(Position pos, int laneId, CarConfiguration car) {
        this.car = car;
        this.laneId = laneId;
        this.position = pos;
    }

    public Position getPosition() {
        return position;
    }

    public int getLaneId() {
        return laneId;
    }

    public CarConfiguration getCar() {
        return car;
    }
}
