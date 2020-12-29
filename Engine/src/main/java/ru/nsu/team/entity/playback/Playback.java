package ru.nsu.team.entity.playback;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import ru.nsu.team.entity.statistics.CarState;

public class Playback {
  private List<CarState> carStates;

  public Playback() {
    carStates = Collections.synchronizedList(new LinkedList<>());
  }

  public void addCarState(CarState carState) {
    carStates.add(carState);
  }

  public List<CarState> getCarStates() {
    return carStates;
  }

  public void setCarStates(List<CarState> carStates) {
    this.carStates = carStates;
  }
}
