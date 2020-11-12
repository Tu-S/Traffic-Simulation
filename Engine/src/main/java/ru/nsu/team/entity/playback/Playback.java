package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.RoadState;

import java.util.ArrayList;
import java.util.List;

public class Playback {
  private List<RoadState> roadStates;

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
