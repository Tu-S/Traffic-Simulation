package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class RoadMapConfiguration {
    private List<NodeConfiguration> nodes;
    private List<RoadConfiguration> roads;
    private List<PlaceOfInterestConfiguration> pointsOfInterest;
    private Integer spawnPeriodMin;
    private Integer spawnPeriodMax;

    public List<PlaceOfInterestConfiguration> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public Integer getSpawnPeriodMin() {
        return spawnPeriodMin;
    }

    public Integer getSpawnPeriodMax() {
        return spawnPeriodMax;
    }

    public List<NodeConfiguration> getNodes(){
        return nodes;
    }

    public List<RoadConfiguration> getRoads() {
        return roads;
    }
}
