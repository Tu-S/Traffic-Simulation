package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.Copier;

import java.util.List;

public class CopierTest {

    @Test
    public void copyMap() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;
        List<RoadMap> maps = Copier.makeMapCopy(mapConfig, 5);
        assert !maps.get(0).equals(maps.get(1));
        for(var r1 : maps.get(0).getRoads()){
            for(var r2 : maps.get(1).getRoads()){
                assert !r1.equals(r2);
            }
        }

    }


}
