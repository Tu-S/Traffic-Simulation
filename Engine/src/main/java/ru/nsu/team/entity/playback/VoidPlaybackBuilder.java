package ru.nsu.team.entity.playback;

import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

public class VoidPlaybackBuilder extends PlaybackBuilder {
    @Override
    public void addCarState(TrafficParticipant trafficParticipant, long time, boolean draw) {
    }

    @Override
    public Playback getPlayback() {
        return new Playback();
    }
}
