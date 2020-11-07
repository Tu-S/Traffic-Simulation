package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.roadmap.configuration.*;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.jsonparser.RoadMapConfigurationParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RoadModelCreator {


    public RoadMap createRoadMap(String fileName) {
        RoadMap map = new RoadMap();

        RoadMapConfigurationParser parser = new RoadMapConfigurationParser();
        RoadMapConfiguration roadMapConfig;
        try {
            roadMapConfig = parser.ParseJson(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        List<RoadConfiguration> configRoads = roadMapConfig.getRoads();
        List<NodeConfiguration> configNodes = roadMapConfig.getNodes();

        int rN = configRoads.size();
        int nN = configNodes.size();
        ArrayList<Node> nodes = new ArrayList<>(nN);
        ArrayList<Road> roads = new ArrayList<>(rN);
        for (int i = 0; i < nN; i++) {

            nodes.add(new Node());
        }

        for (RoadConfiguration roadConfig : configRoads) {
            int from = roadConfig.getFrom();
            int to = roadConfig.getTo();
            List<LaneConfiguration> lanes = roadConfig.getLanes();
            int lanesNumber = lanes.size();
            Road road = new Road(nodes.get(from), nodes.get(to), 0, roadConfig.getLength(), lanesNumber);
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
        for (int i = 0; i < nN; i++) {
            Node node = nodes.get(i);
            NodeConfiguration nodeConfig = configNodes.get(i);
            TrafficLightConfiguration trafficLightConfig = nodeConfig.getTrafficLightConfiguration();
            if(trafficLightConfig != null) {
                int greenDuration = trafficLightConfig.getDelayGreen();
                int redDuration = trafficLightConfig.getDelayGreen();
                TrafficLight trafficLight = new TrafficLight(greenDuration, redDuration);
                List<Integer> pair = trafficLightConfig.getPairsOfRoads();
                for (Integer integer : pair) {
                    trafficLight.addRoad(roads.get(integer));
                }

                node.addTrafficLight(trafficLight);
            }

            List<Integer> toRoadId = nodeConfig.getRoadsTo();
            List<Integer> fromRoadId = nodeConfig.getRoadsFrom();
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
        List<PlaceOfInterestConfiguration> placesOfInterests = roadMapConfig.getPointsOfInterest();
        for (PlaceOfInterestConfiguration config : placesOfInterests) {
            PlaceOfInterest place = new PlaceOfInterest(config.getCapacity());
            List<Integer> connectedNodes = config.getNodes();
            for (Integer number : connectedNodes) {
                place.addNode(nodes.get(number));
            }
            map.addPlaceOfInterest(place);
        }
        return map;
    }


}
