package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class NodeConfiguration {
    private List<Integer> roadsOut;
    private List<Integer> roadsIn;
    private TrafficLightConfiguration trafficLight;
    private boolean isSpawner;

    public List<Integer> getRoadsOut() {
        return roadsOut;
    }

    public List<Integer> getRoadsIn() {
        return roadsIn;
    }

    public boolean isSpawner() {
        return isSpawner;
    }

    public TrafficLightConfiguration getTrafficLightConfiguration() {
        return trafficLight;
    }
}
