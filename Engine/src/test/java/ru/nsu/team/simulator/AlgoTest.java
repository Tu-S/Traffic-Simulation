package ru.nsu.team.simulator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.nsu.team.genome.AlgorithmVersion1;

public class AlgoTest {
    @Test
    public void testAlgo() {
        AlgorithmVersion1.runAlgorithm();
    }


}
