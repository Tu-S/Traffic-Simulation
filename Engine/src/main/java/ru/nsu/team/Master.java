package ru.nsu.team;

import ru.nsu.team.controller.SimulationController;

import java.util.Scanner;

public class Master {

    public void execute(String[] args) {


        SimulationController simulationController = new SimulationController(args[0], args[1],args[2]);
        Thread simulatorThread = new Thread(simulationController);
        simulatorThread.start();
        System.out.println("Simulation stated");
        //simulationController.execute(args);
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("Input command: ");
            String command = in.nextLine();
            switch (command){
                case "STOP":
                    System.out.println("STOP");
                    simulatorThread.interrupt();
                    try {
                        simulatorThread.join();
                        System.out.println("Simulation ended");
                        return;
                    } catch (InterruptedException ex){
                        System.out.println(ex.getMessage());

                    }
                    break;
                case "PAUSE":
                    System.out.println("pause");
                    break;
                case "CONTINUE":
                    System.out.println("continue");
            }

        }

    }
}
