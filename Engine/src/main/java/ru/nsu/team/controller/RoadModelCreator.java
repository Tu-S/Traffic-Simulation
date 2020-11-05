package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.RawMapData;
import ru.nsu.team.entity.roadmap.RoadMap;

public class RoadModelCreator {

    public RoadMap createRoadMap(RawMapData map) {
        return new RoadMap();
    }


}
