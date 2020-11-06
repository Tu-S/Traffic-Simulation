package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class TrafficLightConfiguration {
    private Integer delayGreen;
    private Integer delayRed;
    private List<Integer> pairsOfRoads;


    public Integer getDelayGreen() {
        return delayGreen;
    }

    public Integer getDelayRed() {
        return delayRed;
    }

    public List<Integer> getPairsOfRoads() {
        return pairsOfRoads;
    }
}
