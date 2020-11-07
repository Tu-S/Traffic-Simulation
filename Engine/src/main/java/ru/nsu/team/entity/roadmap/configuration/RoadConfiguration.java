package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class RoadConfiguration {
    private Integer from;
    private Integer to;
    private Integer length;
    private List<LaneConfiguration> lanes;

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public Integer getLength() {
        return length;
    }

    public List<LaneConfiguration> getLanes() {
        return lanes;
    }

}
