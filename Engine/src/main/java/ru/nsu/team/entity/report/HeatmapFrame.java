package ru.nsu.team.entity.report;

import java.util.ArrayList;
import java.util.List;

public class HeatmapFrame {
    public static class HeatmapRoadState {
        public int roadId;
        public int congestion;
        public double speedRatio;

        public HeatmapRoadState(int roadId, int congestion, double speedRatio) {
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

    public void addHeatmapRoadState(int roadId, int congestion, double speedRatio) {
        congestionList.add(new HeatmapRoadState(roadId, congestion, speedRatio));
    }
}
