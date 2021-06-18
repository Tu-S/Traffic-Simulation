package ru.nsu.team;

import ru.nsu.team.controller.SimulationController;
import ru.nsu.team.genome.AlgorithmVersion2;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.savers.SaverUtils;

import java.util.Scanner;

public class Master {

    public void execute(String[] args) {
        switch(args[0]){
            case "sim":
                simulate(args[1],args[2],args[3]);
                break;
            case "opt":
                optimise(args[1],args[2]);
                System.out.println("opt done");
                break;
        }
    }

    private void optimise(String... args) {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig(args[0]);
        assert mapConfig != null;
        var optimisedMap = AlgorithmVersion2.runAlgorithm(mapConfig);
        SaverUtils.saveOptimisedMap(optimisedMap,mapConfig,args[1]);
    }

    private void simulate(String... args){
        SimulationController simulationController = new SimulationController(args[0], args[1], args[2]);
        Thread simulatorThread = new Thread(simulationController);
        simulatorThread.start();
        System.out.println("Simulation stated");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            System.out.print("Input command: ");
            String command = in.nextLine();
            switch (command) {
                case "STOP":
                    simulationController.stop();
                    try {
                        simulatorThread.join();
                        System.out.println("Simulation ended");
                        return;
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());

                    }
                    break;
                case "PAUSE":
                    simulationController.pause();
                    break;
                case "CONTINUE":
                    simulationController.resume();
            }

        }


    }
}
