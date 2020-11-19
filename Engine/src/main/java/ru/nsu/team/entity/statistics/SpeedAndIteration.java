package ru.nsu.team.entity.statistics;

public class SpeedAndIteration {
  private int speed;
  private int iteration;

  public SpeedAndIteration() {
    speed = 0;
    iteration = 0;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getIteration() {
    return iteration;
  }

  public void setIteration(int iteration) {
    this.iteration = iteration;
  }
}
