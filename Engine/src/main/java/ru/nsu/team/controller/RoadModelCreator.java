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
    private final List<Node> nodes = new ArrayList<>();
    private final List<Road> roads = new ArrayList<>();
    private int rId = 0;

    public RoadMap createRoadMap(RoadMapConfiguration roadMapConfig) {
        RoadMap map = new RoadMap();
        createNodes(roadMapConfig.getNodes());
        createRoads(roadMapConfig.getRoads());
        createLights(roadMapConfig.getNodes());
        createSpawners(roadMapConfig.getNodes(), map);
        createCourses(roadMapConfig.getRoads(), roadMapConfig.getNodes());
        createPlacesOfInterest(roadMapConfig.getPointsOfInterest(), map);
        createTrafficParticipants(roadMapConfig.getTrafficParticipants());
        for (Road road : roads) {
            map.addRoad(road);
        }
        map.setStart(getValue(roadMapConfig.getStart()));
        long t;
        if ((t = getValue(roadMapConfig.getCurrentTime())) < 0)
            map.setCurrentTime(map.getStart());
        else {
            map.setCurrentTime(t);
        }
        map.setEndTime(getValue(roadMapConfig.getEnd()));
        return map;
    }

    private long getValue(String time) {
        time += ":00";
        Time t = Time.valueOf(time);
        return t.getTime();
    }

    private double calculateLength(Position start, Position end) {
        double first = Math.pow(end.getX() - start.getX(), 2);
        double second = Math.pow(end.getY() - start.getY(), 2);
        double third = first + second;
        return Math.sqrt(third);
    }

    private void createNodes(List<NodeConfiguration> nodesConfig) {
        int nodeNumber = nodesConfig.size();
        for (int i = 0; i < nodeNumber; i++) {
            NodeConfiguration config = nodesConfig.get(i);
            Position pos = new Position(config.getX(), config.getY());
            Node n = new Node(i, pos);
            nodes.add(n);
        }
    }

    private void createSpawners(List<NodeConfiguration> nodesConfig, RoadMap map) {

        for (int i = 0; i < nodesConfig.size(); i++) {

            NodeConfiguration nodeConfig = nodesConfig.get(i);
            if (nodeConfig.getPeriodsOfSpawn() != null) {
                Node node = this.nodes.get(i);
                List<SpawnConfiguration> configs = nodeConfig.getPeriodsOfSpawn();
                Road spawnedQueue = new Road(rId++, null, node);
                Lane lane = new Lane(spawnedQueue);
                spawnedQueue.addLane(lane);
                Spawner spawner = new Spawner(node, spawnedQueue);
                for (SpawnConfiguration config : configs) {
                    long start = getValue(config.getStart());
                    long end = getValue(config.getEnd());
                    spawner.addConfiguration(new Configuration(start, end, config.getSpawnRate()));
                }
                map.addSpawner(spawner);
                roads.add(spawnedQueue);
            }
        }
    }

    private void createLights(List<NodeConfiguration> nodesConfig) {
        int number = 0;
        for (NodeConfiguration nodeConfig : nodesConfig) {
            Node node = this.nodes.get(number++);
            List<TrafficLightConfiguration> trafficLightConfigs = nodeConfig.getTrafficLightConfigurations();
            if (trafficLightConfigs != null) {
                for (TrafficLightConfiguration config : trafficLightConfigs) {
                    TrafficLight trafficLight = new TrafficLight(config.getDelay());
                    Iterable<Integer> pair = config.getRoads();
                    for (Integer integer : pair) {
                        trafficLight.addRoad(roads.get(integer));
                    }
                    node.addTrafficLight(trafficLight);
                }
            }
        }
    }

    private void createCourses(List<RoadConfiguration> roadsConfig, List<NodeConfiguration> nodesConfig) {
        int nodeNumber;
        nodeNumber = nodesConfig.size();
        for (int i = 0; i < nodeNumber; i++) {
            NodeConfiguration nodeConfig = nodesConfig.get(i);
            List<Integer> outRoadsId = nodeConfig.getRoadsOut();
            List<Integer> inRoadsId = nodeConfig.getRoadsIn();
            Node node = this.nodes.get(i);

            int lenIds = nodeConfig.getCoursesNumber();
            for (int heh = 0; heh < lenIds; heh++) {
                int in = inRoadsId.get(heh);
                int out = outRoadsId.get(heh);

                RoadConfiguration roadIn = roadsConfig.get(in);
                RoadConfiguration roadOut = roadsConfig.get(out);
                for (LaneConfiguration lane : roadIn.getLanes()) {
                    for (SignConfiguration sing : lane.getSigns()) {


                    }

                }
                for (LaneConfiguration lane : roadOut.getLanes()) {
                    for (SignConfiguration sing : lane.getSigns()) {


                    }
                }
                //TODO create courses and intersections according to signs and trajectories
                Course course = new Course(roads.get(in).getLaneN(0), roads.get(out).getLaneN(0));
                node.addCourse(course);
            }
        }
    }

    private void createRoads(Iterable<RoadConfiguration> roads) {
        for (RoadConfiguration roadConfig : roads) {
            int from = roadConfig.getFrom();
            int to = roadConfig.getTo();
            Iterable<LaneConfiguration> lanes = roadConfig.getLanes();
            Node fromN = nodes.get(from);
            Node toN = nodes.get(to);
            Road road = new Road(rId++, fromN, toN);
            for (LaneConfiguration laneConfig : lanes) {
                Lane lane = new Lane(road);
                road.addLane(lane);
            }
            this.roads.add(road);
        }
        for (Road road : this.roads) {
            if (road.getLength() == 0) {
                double length = calculateLength(road.getFrom().getPosition(), road.getTo().getPosition());
                road.setLength(length);
            }
        }
    }


    private void createPlacesOfInterest(Iterable<PlaceOfInterestConfiguration> placesOfInterests, RoadMap map) {
        int plId = 0;
        for (PlaceOfInterestConfiguration config : placesOfInterests) {
            PlaceOfInterest place = new PlaceOfInterest(plId++, config.getCapacity(), config.getWeight());
            Iterable<Integer> connectedNodes = config.getNodes();
            for (Integer number : connectedNodes) {
                place.addNode(nodes.get(number));
            }
            map.addPlaceOfInterest(place);
        }
    }

    private void createTrafficParticipants(Iterable<TrafficParticipantConfiguration> trafficParticipantConfigs) {
        if (trafficParticipantConfigs != null) {
            int pId = 0;
            for (TrafficParticipantConfiguration config : trafficParticipantConfigs) {
                CarConfiguration carConfig = config.getCar();
                Path path = new Path();
                for (Integer rNumber : carConfig.getPath()) {
                    path.addRoadToPath(roads.get(rNumber));
                }
                Car car = new Car(pId++, Car.DEFAULT_MAX_SPEED, path);
                PositionOnRoadConfiguration pos = config.getPositionOnRoad();
                Road road = this.roads.get(pos.getCurrentRoad());
                double position = pos.getPosition();
                int lane = pos.getCurrentLane();
                PositionOnRoad positionOnRoad = new PositionOnRoad(road, position, lane);
                TrafficParticipant trafficParticipant = new TrafficParticipant(car, positionOnRoad);
                road.addTrafficParticipant(trafficParticipant);
            }
        }
    }

    private final String SPEED = "speed";
    private final String MAIN_ROAD = "mainRoad";
    private final String LEFT = "left";
    private final String RIGHT = "right";
    private final String FORWARD = "forward";
    private final String FORWARD_OR_RIGHT = "forwardOrRight";
    private final String FORWARD_OR_LEFT = "forwardOrLeft";
    private final String LEFT_OR_RIGHT = "leftOrRight";


    private void analyzeSign(Sign sign) {
        switch (sign.getType()) {
            case (SPEED):


                break;
            case (MAIN_ROAD):


                break;
            case (LEFT):

                break;
            case (RIGHT):

                break;
            case (FORWARD):

                break;
            case (FORWARD_OR_LEFT):

                break;
            case (FORWARD_OR_RIGHT):

                break;
            case (LEFT_OR_RIGHT):

                break;

        }


    }


}
