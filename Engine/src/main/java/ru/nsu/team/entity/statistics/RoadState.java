package ru.nsu.team.entity.statistics;

import ru.nsu.team.entity.roadmap.Road;

public class RoadState {
  private Road road;
  private int time;

  public RoadState(Road road, int time) {
    this.road = road;
    this.time = time;
  }

  public int getId() {
    return road.getId();
  }

  public int getTime() {
    return time;
  }
}
