package ru.nsu.team.entity.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HeatmapFrame implements Serializable {
    public static class HeatmapRoadState implements Serializable {
        public int roadId;
        public int congestion;
        public List<Double> speedRatio;

        public HeatmapRoadState(int roadId, int congestion, List<Double> speedRatio) {
            this.roadId = roadId;
            this.congestion = congestion;
            this.speedRatio = speedRatio;
        }
    }

    int start, end;
    List<HeatmapRoadState> congestionList;

    public HeatmapFrame(int start, int end) {
        this.start = start;
        this.end = end;
        this.congestionList = new ArrayList<>();
    }

    public void addHeatmapRoadState(int roadId, int congestion, List<Double> speedRatio) {
        congestionList.add(new HeatmapRoadState(roadId, congestion, speedRatio));
    }
}
