package ru.nsu.team.entity.roadmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrafficLightConfig implements Serializable {
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

    public TrafficLightConfig(Integer delay, List<Road> roads) {
        this.delay = delay;
        this.roads = roads;
    }

    public void addRoad(Road r) {
        roads.add(r);
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

}
