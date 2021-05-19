package ru.nsu.team.roadmodelcreator;

import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Copier {
    public static List<RoadMap> makeMapCopy(RoadMapConfiguration roadMapConfig, int count) {
        List<RoadMap> maps = new ArrayList<>(count);
        var creator = new RoadModelCreator();
        for (int i = 0; i < count; i++) {
            maps.add(creator.createRoadMap(roadMapConfig));
        }
        return maps;
    }


}
