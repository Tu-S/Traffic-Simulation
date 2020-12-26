package ru.nsu.team.entity.statistics;

import java.util.List;

public class HeatMapConfiguration {
    private  long start;
    private long end;
    private List<RoadCongestion> congestionList;


    public HeatMapConfiguration(long start, long end, List<RoadCongestion> congestionList) {
        this.start = start;
        this.end = end;
        this.congestionList = congestionList;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public List<RoadCongestion> getCongestionList() {
        return congestionList;
    }
}
