package ru.nsu.team.entity.statistics;

public class RoadState {
  private int id;
  private int numberOfCars;
  private long time;

  public RoadState(int id, int numberOfCars, long time) {
    this.id = id;
    this.numberOfCars = numberOfCars;
    this.time = time;
  }
}
