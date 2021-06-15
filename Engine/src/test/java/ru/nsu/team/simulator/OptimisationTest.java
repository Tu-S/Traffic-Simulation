package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.genome.AlgorithmVersion2;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.savers.SaverUtils;

public class OptimisationTest {
    @Test
    public void test() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/test_map.tsp");
        assert mapConfig != null;
        var optimisedMap = AlgorithmVersion2.runAlgorithm(mapConfig);
        SaverUtils.saveOptimisedMap(optimisedMap, mapConfig, "config/optMap.json");
    }


}
