package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;
import java.util.List;

public class TrafficLightConfig {
    private Integer delay;
    private List<Road> roads;

    public Integer getDelay() {
        return delay;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public TrafficLightConfig(Integer delay) {
        this.delay = delay;
        this.roads = new ArrayList<>();
    }

    public void addRoad(Road r){
        roads.add(r);
    }
}
