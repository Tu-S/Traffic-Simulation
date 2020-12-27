package ru.nsu.team.roadmodelcreator;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.roadmap.configuration.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.entity.trafficparticipant.*;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class RoadModelCreator {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Road> roads = new ArrayList<>();
    private int rId = 0;

    public RoadMap createRoadMap(RoadMapConfiguration roadMapConfig) {
        RoadMap map = new RoadMap();
        createNodes(roadMapConfig.getNodes());
        createRoads(roadMapConfig.getRoads());
        createLights(roadMapConfig.getNodes());
        calculateAngles(roadMapConfig.getNodes(), roadMapConfig.getRoads());
        createCourses(roadMapConfig.getRoads(), roadMapConfig.getNodes(), map);
        createSpawners(roadMapConfig.getNodes(), map);
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
        map.getSpawners().forEach(s -> s.addPossibleDestination(map.getPlacesOfInterest()));

        return map;
    }

    private long getValue(String time) {
        time += ":00";
        Time t = Time.valueOf(time);
        return t.getTime() / 1000;
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
            //n.setRoadsIn(config.getRoadsIn());
            //n.setRoadsOut(config.getRoadsOut());
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
                Set<Lane> allLanes = node.getCourses().stream().map(Course::getToLane).map(Lane::getParentRoad).flatMap(r -> r.getLanes().stream()).collect(Collectors.toSet());
                allLanes.forEach(ln -> node.addCourse(new Course(spawnedQueue.getLaneN(0), ln)));
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
                TrafficLight trafficLight = new TrafficLight();
                for (TrafficLightConfiguration config : trafficLightConfigs) {
                    Iterable<Integer> pair = config.getRoads();
                    TrafficLightConfig c = new TrafficLightConfig(config.getDelay());
                    for (Integer integer : pair) {
                        c.addRoad(roads.get(integer));
                    }
                    trafficLight.addConfig(c);
                }
                node.addTrafficLight(trafficLight);
            }
        }
    }

    private void createCourses(List<RoadConfiguration> roadsConfig, List<NodeConfiguration> nodesConfig, RoadMap map) {
        int nodeNumber;
        nodeNumber = nodesConfig.size();
        for (int i = 0; i < nodeNumber; i++) {
            NodeConfiguration nodeConfig = nodesConfig.get(i);
            List<Integer> outRoadsId = nodeConfig.getRoadsOut();
            List<Integer> inRoadsId = nodeConfig.getRoadsIn();
            Node node = this.nodes.get(i);

            Set<Course> createdCourses = new HashSet<>();
            int lenIds = nodeConfig.getCoursesNumber();
            for (int heh = 0; heh < lenIds; heh++) {
                int in = inRoadsId.get(heh);
                for (Lane inLane : roads.get(in).getLanes()) {
                    for (Integer roadId : outRoadsId) {
                        roads.get(roadId).getLanes().forEach(outLane -> createdCourses.add(new Course(inLane, outLane, Collections.singletonList(new Intersection(0)), 10)));
                    }
                }
            }
            createdCourses.forEach(node::addCourse);
            map.getCourseSet().addAll(createdCourses);
        }
    }

    private void calculateAngles(List<NodeConfiguration> nodeConfigs, List<RoadConfiguration> roadConfigs) {
        SortedMap<Double, Road> sm = new TreeMap<Double, Road>();
        NodeConfiguration config;
        Node node;
        for (int i = 0; i < nodeConfigs.size(); i++) {
            config = nodeConfigs.get(i);
            for (Integer numberIn : config.getRoadsIn()) {
                Road in = roads.get(numberIn);
                RoadConfiguration configIn = roadConfigs.get(numberIn);
                for (Integer numberOut : config.getRoadsOut()) {
                    Road out = roads.get(numberOut);
                    RoadConfiguration configOut = roadConfigs.get(numberOut);
                    double angle = calculateAngle(nodeConfigs.get(configIn.getFrom()), nodeConfigs.get(configIn.getTo()), nodeConfigs.get(configOut.getTo()), nodeConfigs.get(configOut.getFrom()));
                    sm.put(angle, out);

                }
                Road[] roads1 = sm.values().toArray(new Road[0]);
                node = this.nodes.get(i);
                node.addFromTo(in, Arrays.asList(roads1));
                sm.clear();
            }
        }


    }

    private double calculateAngle(NodeConfiguration n1, NodeConfiguration n2, NodeConfiguration n3, NodeConfiguration duplicate) {
        assert !n2.equals(duplicate);
        double yDif = n3.getY() - n2.getY();

        double n2n1X = n1.getX() - n2.getX();
        double n2n1Y = n1.getY() - n2.getY();

        double n2n3X = n3.getX() - n2.getX();
        double n2n3Y = n3.getY() - n2.getY();

        double ch = (n2n1X * n2n3X + n2n1Y * n2n3Y);
        double z = Math.sqrt(Math.pow(n2n1X, 2) + Math.pow(n2n1Y, 2)) * Math.sqrt(Math.pow(n2n3X, 2) + Math.pow(n2n3Y, 2));
        double cos = ch / z;

        double sin = Math.sqrt(1 - Math.pow(cos, 2));
        if (yDif > 0) {
            return Math.acos(cos);
        } else {
            return 2 * Math.PI - Math.acos(cos);
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
                for (SignConfiguration sing : laneConfig.getSigns()) {
                    analyzeSign(sing, lane);
                }
                road.addLane(lane);
            }
            this.roads.add(road);
        }
        for (Road road : this.roads) {
            if (road.getLength() == 0) {
                double length = calculateLength(road.getFrom().getPosition(), road.getTo().getPosition());
                road.setLength(length / 3); // pixel to meter
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


    private void analyzeSign(SignConfiguration sign, Lane lane) {
        switch (sign.getType()) {
            case (SPEED):
                lane.setMaxSpeed(sign.getLimit() / 3.6);

                break;
            case (MAIN_ROAD):
                lane.getParentRoad().setMainRoad(true);

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
