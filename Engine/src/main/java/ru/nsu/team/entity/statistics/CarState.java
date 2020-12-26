package ru.nsu.team.entity.statistics;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Position;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class CarState {
  private double speed;
  private final int id;
  private double position;
  private int time;
  private int currentLane;
  private int currentRoad;
  private Position from;
  private Position to;

  public CarState(TrafficParticipant trafficParticipant, int time) {
    Car car = trafficParticipant.getCar();
    PositionOnRoad positionOnRoad = trafficParticipant.getPosition();
    speed = car.getSpeed();
    id = car.getId();
    position = positionOnRoad.getPosition();
    this.time = time;
    currentLane = positionOnRoad.getCurrentLane();
    Road road = positionOnRoad.getCurrentRoad();
    currentRoad = road.getId();
    from = road.getFrom().getPosition();
    to = road.getTo().getPosition();
  }

  public double getSpeed() {
    return speed;
  }

  public int getId() {
    return id;
  }

  public double getPosition() {
    return position;
  }

  public int getTime() {
    return time;
  }

  public int getCurrentLane() {
    return currentLane;
  }

  public int getCurrentRoad() {
    return currentRoad;
  }

  public Position getFrom() {
    return from;
  }

  public Position getTo() {
    return to;
  }
}
