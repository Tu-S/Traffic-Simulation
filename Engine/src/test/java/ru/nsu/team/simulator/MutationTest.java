package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.genome.GenomeUtils;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.Copier;

import java.util.List;

public class MutationTest {

    @Test
    public void mutationTest() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;
        List<RoadMap> maps = Copier.makeMapCopy(mapConfig, 5);
        for (var m : maps) {
            for (var r : m.getRoads()) {
                GenomeUtils.mutateRoad(r);
            }

        }
        System.out.println();

    }
}
