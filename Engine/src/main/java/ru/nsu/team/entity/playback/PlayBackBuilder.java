package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class PlayBackBuilder {
  private Playback playback;

  public PlayBackBuilder() {
    this.playback = new Playback();
  }

  public void addCarState(TrafficParticipant trafficParticipant, int time) {
    playback.addCarState(new CarState(trafficParticipant, time));
  }

  public Playback getPlayback() {
    return playback;
  }
}
