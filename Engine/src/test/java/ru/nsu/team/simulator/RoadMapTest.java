package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.controller.SimulationController;

public class RoadMapTest {
    @Test
    public void testMapCreation() {
        SimulationController simulationController = new SimulationController();
        simulationController.testRun("config/1.tsp");
        var map = simulationController.getRoadMap();
        for (var r : map.getRoads()) {
            for (var c : r.getFrom().getCourses()) {
                assert c.getIntersections().size() != 0;
            }
            for (var c : r.getTo().getCourses()) {
                assert c.getIntersections().size() != 0;
            }
        }
    }


}
