package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.roadmap.configuration.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.entity.trafficparticipant.*;

import java.sql.Time;
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
            Node fromN = nodes.get(from);
            Node toN = nodes.get(to);
            Road road = new Road(i, fromN, toN, 0, lanesNumber);
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
            PlaceOfInterest place = new PlaceOfInterest(i, config.getCapacity(), config.getWeight());
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
                Car car = new Car(t, carConfig.getMaxSpeed(), path);
                PositionOnRoad pos = trafficParticipantConfig.getPositionOnRoad();
                TrafficParticipant trafficParticipant = new TrafficParticipant(car,new PositionOnRoad());
                Road road = pos.getCurrentRoad();
                PlaceOfInterest place = pos.getCurrentPlaceOfInterest();
                place.addTrafficParticipant(trafficParticipant);
                road.addTrafficParticipant(trafficParticipant);
            }
        }

        for (int i = 0; i < nN; i++) {
            Node node = nodes.get(i);
            NodeConfiguration nodeConfig = configNodes.get(i);
            node.setPosition(new Position(nodeConfig.getX(), nodeConfig.getY()));
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
            List<Integer> outRoadId = nodeConfig.getRoadsOut();
            List<Integer> inRoadId = nodeConfig.getRoadsIn();
            int lenIds = outRoadId.size();
            for (int heh = 0; heh < lenIds; heh++) {
                int in = inRoadId.get(heh);
                int out = outRoadId.get(heh);
                //TODO create courses and intersections according to signs and trajectories
                Course course = new Course(roads.get(in).getLaneN(0), roads.get(out).getLaneN(0));
                node.addCourse(course);
            }
            for (Integer to : outRoadId) {
                for (Integer from : inRoadId) {
                    Course course = new Course(roads.get(from), roads.get(to));
                    node.addCourse(course);
                }
            }
            if (nodeConfig.getPeriodsOfSpawn() != null) {
                List<SpawnConfiguration> configs = nodeConfig.getPeriodsOfSpawn();
                Spawner spawner = new Spawner(node);
                for (SpawnConfiguration config : configs) {
                    long start = getValue(config.getStart());
                    long end = getValue(config.getEnd());
                    spawner.addConfiguration(new Configuration(start, end, config.getSpawnRate()));

                }
                Road fakeRoad = new Road(-1, node, new Node(-2), 666, 1);
                fakeRoad.setLength(Integer.MAX_VALUE);
                roads.add(fakeRoad);
                map.addSpawner(spawner);
            }

        }


        for (Road road : roads) {
            if (road.getLength() == 0) {
                double length = calculateLength(road.getFrom().getPosition(), road.getTo().getPosition());
                road.setLength(length);
            }
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

        map.setStart(getValue(roadMapConfig.getStart()));
        return map;
    }

    private long getValue(String time) {
        Time t = Time.valueOf(time);
        return t.getTime() / 1000;
    }

    private double calculateLength(Position start, Position end) {
        double first = Math.pow(end.getX() - start.getX(), 2);
        double second = Math.pow(end.getY() - start.getY(), 2);
        double third = first + second;
        return Math.sqrt(third);
    }


}
