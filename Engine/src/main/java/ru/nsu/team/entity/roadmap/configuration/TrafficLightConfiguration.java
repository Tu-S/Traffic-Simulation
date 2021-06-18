package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class TrafficLightConfiguration {
    private Integer delay;
    private List<Integer> roads;

    public Integer getDelay() {
        return delay;
    }

    public List<Integer> getRoads() {
        return roads;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
