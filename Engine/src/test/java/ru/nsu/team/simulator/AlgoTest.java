package ru.nsu.team.simulator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.nsu.team.genome.AlgorithmVersion1;

public class AlgoTest {
    /*@BeforeClass
    public static void initLogger() {
        Logger.getRootLogger().addAppender(
                new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    }*/
    @Test
    public void testAlgo() {
        AlgorithmVersion1.runAlgorithm();
    }


}
