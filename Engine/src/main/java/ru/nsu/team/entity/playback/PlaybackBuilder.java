package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class PlaybackBuilder {
  private Playback playback;

  public PlaybackBuilder() {
    this.playback = new Playback();
  }

  public void addCarState(TrafficParticipant trafficParticipant, long time, boolean draw) {
    if (trafficParticipant.getPosition().getCurrentRoad().getFrom() != null) {
      playback.addCarState(new CarState(trafficParticipant, time, draw));
    }
  }

  public Playback getPlayback() {
    return playback;
  }
}
