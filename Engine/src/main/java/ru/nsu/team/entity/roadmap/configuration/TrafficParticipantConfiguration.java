package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;

public class TrafficParticipantConfiguration {
    private PositionOnRoad position;
    private CarConfiguration car;

    public TrafficParticipantConfiguration(PositionOnRoad pos, CarConfiguration car) {
        this.car = car;
        this.position = pos;
    }

    public PositionOnRoad getPositionOnRoad() {
        return position;
    }

    public CarConfiguration getCar() {
        return car;
    }
}
