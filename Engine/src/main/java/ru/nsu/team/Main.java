package ru.nsu.team;

import ru.nsu.team.controller.SimulationController;


public class Main {
    public static void main(String[] args) {

        SimulationController simulationController = new SimulationController();
        simulationController.run("config/sample.json");
        //System.out.println(map.getRoads().get(0).getTrafficParticipantsNumber());
        simulationController.save("config/save1.json");
        simulationController.run("config/save1.json");
        simulationController.save("config/save2.json");

    }
}
