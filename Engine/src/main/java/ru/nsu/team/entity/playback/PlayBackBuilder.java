package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.CarState;

public class PlayBackBuilder {

  private Playback playback;

  public PlayBackBuilder() {
    this.playback = new Playback();
  }

  public void addCarState(CarState carState) {

  }

  public void addTrafficLightState(TrafficLightState trafficLightState) {

  }

  public void stopBuild() {
  }

  public Playback getPlayback() {
    return playback;
  }


}
