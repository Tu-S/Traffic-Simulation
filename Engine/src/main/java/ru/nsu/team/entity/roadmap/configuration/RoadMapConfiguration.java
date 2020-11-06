package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class RoadMapConfiguration {
    private List<NodeConfiguration> nodes;
    private List<RoadConfiguration> roads;

    public List<NodeConfiguration> getNodes(){
        return nodes;
    }

    public List<RoadConfiguration> getRoads() {
        return roads;
    }
}
