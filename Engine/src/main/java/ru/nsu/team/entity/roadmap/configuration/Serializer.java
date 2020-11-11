package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;


public class Serializer {

    public CarConfiguration toCarConfiguration(Car car) {
        return new CarConfiguration(car);
    }

    public TrafficParticipantConfiguration toTrafficParticipantConfiguration(TrafficParticipant trafficParticipant) {
        CarConfiguration car = toCarConfiguration(trafficParticipant.getCar());
        return new TrafficParticipantConfiguration(trafficParticipant.getPosition(), trafficParticipant.getLaneId(), car);
    }

    public int roadToInt(Road road) {
        return road.getId();
    }
}
