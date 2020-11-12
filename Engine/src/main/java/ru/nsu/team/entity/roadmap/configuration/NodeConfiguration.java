package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class NodeConfiguration {
    private List<Integer> roadsOut;
    private List<Integer> roadsIn;
    private TrafficLightConfiguration trafficLight;
    private List<SpawnConfiguration>  periodsOfSpawn;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<SpawnConfiguration> getPeriodsOfSpawn() {
        return periodsOfSpawn;
    }

    public List<Integer> getRoadsOut() {
        return roadsOut;
    }

    public List<Integer> getRoadsIn() {
        return roadsIn;
    }

    public TrafficLightConfiguration getTrafficLightConfiguration() {
        return trafficLight;
    }
}
