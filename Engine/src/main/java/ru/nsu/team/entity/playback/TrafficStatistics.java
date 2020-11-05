package ru.nsu.team.entity.playback;

import java.util.ArrayList;

public class TrafficStatistics {

    private ArrayList<RoadState> roadStatistics;

    public void addRoadState(RoadState roadState) {

    }

    public int getStaticsNumber() {
        return roadStatistics.size();
    }

    public RoadState getRoadStaticsN(int n) {
        return roadStatistics.get(n);
    }
}
