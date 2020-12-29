package ru.nsu.team.entity.statistics;

import ru.nsu.team.entity.roadmap.Road;

public class RoadState {
  private Road road;
  private long time;

  public RoadState(Road road, long time) {
    this.road = road;
    this.time = time;
  }

  public Road getRoad() {
    return road;
  }

  public long getTime() {
    return time;
  }
}
