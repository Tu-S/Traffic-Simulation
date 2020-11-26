package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;

public class TrafficParticipantConfiguration {
    private PositionOnRoadConfiguration position;
    private CarConfiguration car;

    public TrafficParticipantConfiguration(PositionOnRoadConfiguration pos, CarConfiguration car) {
        this.car = car;
        this.position = pos;
    }

    public PositionOnRoadConfiguration getPositionOnRoad() {
        return position;
    }

    public CarConfiguration getCar() {
        return car;
    }
}
