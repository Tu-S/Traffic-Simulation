package ru.nsu.team.entity.playback;

import java.util.ArrayList;

public class Playback {
    private ArrayList<RoadState> roadStates;

    public Playback() {
        roadStates = new ArrayList<>();
    }

    public int getRoadSatesNumber() {
        return roadStates.size();
    }

    public RoadState getRoadStateN(int n) {
        return roadStates.get(n);
    }

    public void addRoadState(RoadState state) {
        roadStates.add(state);
    }


}
