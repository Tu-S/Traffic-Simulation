package ru.nsu.team.controller;

import ru.nsu.team.entity.playback.Playback;
import ru.nsu.team.entity.playback.TrafficStatistics;
import ru.nsu.team.entity.roadmap.RoadMap;

public class Loader {
    public void load() {
    }

    public Playback getPlaybackBuilder() {


        return new Playback();
    }

    public TrafficStatistics getTrafficStatistics() {


        return new TrafficStatistics();
    }

    public RoadMap getRoadMap() {

        return new RoadMap();
    }


}
