package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.roadmap.configuration.*;
import ru.nsu.team.jsonparser.RoadMapConfigurationParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RoadModelCreator {

    private RoadMap map;


    public RoadMap createRoadMap(String fileName) {
        map = new RoadMap();

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
            Road road = new Road(nodes.get(from), nodes.get(to), 0, 0, lanesNumber);
            for (LaneConfiguration laneConfig : lanes) {
                Lane lane = new Lane();
                List<SignConfiguration> signsConfig = laneConfig.getSigns();
                int signNumber = signsConfig.size();
                for (SignConfiguration signConfig : signsConfig) {
                    Sign sign = new Sign(signConfig.getType(), signConfig.getLimit());
                    lane.addSign(sign);
                }
                road.addLane(lane);
            }
            roads.add(road);
        }
        for (int i = 0; i < nN; i++) {
            NodeConfiguration nodeConfig = configNodes.get(i);

            TrafficLightConfiguration trafficLightConfig = nodeConfig.getTrafficLightConfiguration();
            int greenDuration = trafficLightConfig.getDelayGreen();
            int redDuration = trafficLightConfig.getDelayGreen();
            TrafficLight trafficLight = new TrafficLight(greenDuration, redDuration);
            List<Integer> pair = nodeConfig.getRoads();
            for (Integer integer : pair) {
                trafficLight.addRoad(roads.get(integer));
            }
            Node node = nodes.get(i);
            node.addTrafficLight(trafficLight);
            List<Integer> toRoadId = nodeConfig.getRoads();
            int toRoadNumber = toRoadId.size();
            for (Integer integer : toRoadId) {
                Course course = new Course(null, roads.get(integer));
                node.addCourse(course);
            }
        }
        for (Road road : roads) {
            map.addRoad(road);
        }


        return map;
    }


}
