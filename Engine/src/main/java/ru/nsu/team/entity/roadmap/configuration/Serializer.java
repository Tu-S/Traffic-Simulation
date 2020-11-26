package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;


public class Serializer {

    public CarConfiguration toCarConfiguration(Car car) {
        return new CarConfiguration(car);
    }

    public TrafficParticipantConfiguration toTrafficParticipantConfiguration(TrafficParticipant trafficParticipant) {
        CarConfiguration car = toCarConfiguration(trafficParticipant.getCar());
        PositionOnRoad positionOnRoad = trafficParticipant.getPosition();
        int curRoad = positionOnRoad.getCurrentRoad().getId();
        int curLane = positionOnRoad.getCurrentLane();
        double distanceFromRoadExit = positionOnRoad.getPosition();
        PositionOnRoadConfiguration position = new PositionOnRoadConfiguration(curRoad, curLane, distanceFromRoadExit);
        return new TrafficParticipantConfiguration(position, car);
    }

    public int roadToInt(Road road) {
        return road.getId();
    }
}
