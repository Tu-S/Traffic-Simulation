package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.controller.SimulationController;

public class RoadMapTest {
    @Test
    public void testMapCreation(){
        SimulationController simulationController = new SimulationController();
        simulationController.testRun("config/test.tsp");




    }


}
