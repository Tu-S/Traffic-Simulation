package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class NodeConfiguration {
    private List<Integer> roads;
    private TrafficLightConfiguration trafficLight;

    public List<Integer> getRoads() {
        return roads;
    }

    public TrafficLightConfiguration getTrafficLightConfiguration() {
        return trafficLight;
    }
}
