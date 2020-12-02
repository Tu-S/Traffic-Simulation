package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.statistics.TrafficStatistics;

public class PlayBackBuilder {

  private Playback playback;

  public PlayBackBuilder(TrafficStatistics trafficStatistics) {
    this.playback = new Playback(trafficStatistics);
  }

  public Playback getPlayback() {
    playback.getRoadStates().sort((o1, o2) -> {
      Integer i1 = o1.getTime();
      Integer i2 = o2.getTime();
      return i1.compareTo(i2);
    });
    return playback;
  }
}
