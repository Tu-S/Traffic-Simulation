package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.roadmap.configuration.*;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.Position;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.List;

public class RoadModelCreator {


    public RoadMap createRoadMap(RoadMapConfiguration roadMapConfig) {
        RoadMap map = new RoadMap();


        List<RoadConfiguration> configRoads = roadMapConfig.getRoads();
        List<NodeConfiguration> configNodes = roadMapConfig.getNodes();

        int rN = configRoads.size();
        int nN = configNodes.size();
        ArrayList<Node> nodes = new ArrayList<>(nN);
        ArrayList<Road> roads = new ArrayList<>(rN);
        for (int i = 0; i < nN; i++) {

            nodes.add(new Node(i));
        }

        for (int i = 0; i < rN; i++) {
            RoadConfiguration roadConfig = configRoads.get(i);
            int from = roadConfig.getFrom();
            int to = roadConfig.getTo();
            List<LaneConfiguration> lanes = roadConfig.getLanes();
            int lanesNumber = lanes.size();
            Road road = new Road(i, nodes.get(from), nodes.get(to), 0, roadConfig.getLength(), lanesNumber);
            for (LaneConfiguration laneConfig : lanes) {
                Lane lane = new Lane();
                List<SignConfiguration> signsConfig = laneConfig.getSigns();
                for (SignConfiguration signConfig : signsConfig) {
                    Sign sign = new Sign(signConfig.getType(), signConfig.getLimit());
                    lane.addSign(sign);
                }
                road.addLane(lane);
            }

            roads.add(road);
        }
        List<PlaceOfInterestConfiguration> placesOfInterests = roadMapConfig.getPointsOfInterest();
        int len = placesOfInterests.size();
        for (int i = 0; i < len; i++) {
            PlaceOfInterestConfiguration config = placesOfInterests.get(i);
            PlaceOfInterest place = new PlaceOfInterest(i, config.getCapacity());
            List<Integer> connectedNodes = config.getNodes();
            for (Integer number : connectedNodes) {
                place.addNode(nodes.get(number));
            }
            map.addPlaceOfInterest(place);
        }
        List<TrafficParticipantConfiguration> trafficParticipantConfigs = roadMapConfig.getTrafficParticipants();
        if (trafficParticipantConfigs != null) {
            int length = trafficParticipantConfigs.size();
            for (int t = 0; t < length; t++) {
                TrafficParticipantConfiguration trafficParticipantConfig = trafficParticipantConfigs.get(t);
                CarConfiguration carConfig = trafficParticipantConfig.getCar();
                int nodeId = carConfig.getDestination();
                Path path = new Path();
                List<Integer> roadsInPath = carConfig.getPath();
                int pathLength = roadsInPath.size();
                for (int p = 0; p < pathLength; p++) {
                    path.addRoadToPath(roads.get(p));
                }
                Car car = new Car(t, carConfig.getMaxSpeed(), nodes.get(nodeId), path);
                Position pos = trafficParticipantConfig.getPosition();
                int laneId = trafficParticipantConfig.getLaneId();
                TrafficParticipant trafficParticipant = new TrafficParticipant(car, pos, laneId);
                Road road = roads.get(pos.getCurrentRoad());
                PlaceOfInterest place = map.getPlaceOfInterestN(pos.getCurrentPlaceOfInterest());
                place.addTrafficParticipant(trafficParticipant);
                road.addTrafficParticipant(trafficParticipant);
            }
        }

        for (int i = 0; i < nN; i++) {
            Node node = nodes.get(i);
            NodeConfiguration nodeConfig = configNodes.get(i);
            TrafficLightConfiguration trafficLightConfig = nodeConfig.getTrafficLightConfiguration();
            if (trafficLightConfig != null) {
                int greenDuration = trafficLightConfig.getDelayGreen();
                int redDuration = trafficLightConfig.getDelayGreen();
                TrafficLight trafficLight = new TrafficLight(greenDuration, redDuration);
                List<Integer> pair = trafficLightConfig.getPairsOfRoads();
                for (Integer integer : pair) {
                    trafficLight.addRoad(roads.get(integer));
                }
                node.addTrafficLight(trafficLight);
            }
            List<Integer> toRoadId = nodeConfig.getRoadsOut();
            List<Integer> fromRoadId = nodeConfig.getRoadsIn();
            for (Integer to : toRoadId) {
                for (Integer from : fromRoadId) {
                    Course course = new Course(roads.get(from), roads.get(to));
                    node.addCourse(course);
                }
            }
            if (nodeConfig.isSpawner()) {
                map.addSpawner(new Spawner(node, roadMapConfig.getSpawnPeriodMin(), roadMapConfig.getSpawnPeriodMax()));
            }

        }
        for (Road road : roads) {
            map.addRoad(road);
        }
        List<Integer> activeRoads = roadMapConfig.getActiveRoads();
        List<Integer> calculatedRoads = roadMapConfig.getCalculatedRoads();
        if (activeRoads != null) {
            int activeLen = activeRoads.size();
            for (int i = 0; i < activeLen; i++) {
                map.addActiveRoad(map.getRoadN(activeRoads.get(i)));
            }
        }
        if (calculatedRoads != null) {
            for (Integer calculatedRoad : calculatedRoads) {
                map.addCalculatedRoad(map.getRoadN(calculatedRoad));
            }
        }


        return map;
    }


}
