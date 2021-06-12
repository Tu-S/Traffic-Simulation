package ru.nsu.team.simulator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.nsu.team.genome.AlgorithmVersion1;
import ru.nsu.team.genome.AlgorithmVersion2;
import ru.nsu.team.readers.RoadMapReader;

public class AlgoTest {
    @Test
    public void testAlgo() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/test_map.tsp");
        assert mapConfig != null;
        AlgorithmVersion2.runAlgorithm(mapConfig);
    }


}
