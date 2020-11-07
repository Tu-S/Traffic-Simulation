package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class NodeConfiguration {
    private List<Integer> roadsTo;
    private List<Integer> roadsFrom;
    private TrafficLightConfiguration trafficLight;
    private boolean isSpawner;

    public List<Integer> getRoadsTo() {
        return roadsTo;
    }

    public List<Integer> getRoadsFrom() {
        return roadsFrom;
    }

    public boolean isSpawner() {
        return isSpawner;
    }

    public TrafficLightConfiguration getTrafficLightConfiguration() {
        return trafficLight;
    }
}
