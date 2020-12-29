package ru.nsu.team;



public class Main {
    public static void main(String[] args) {
       /* if(args.length == 0){
            System.out.println("No arguments exception");
            return;
        }*/

        /*SimulationController simulationController = new SimulationController();
        simulationController.run("config/sample_2.json");
        simulationController.saveRoadMap("config/save1.json");
        simulationController.run("config/save1.json");
        simulationController.saveRoadMap("config/save2.json");
        simulationController.saveCarStates("config/carStates.json");
        simulationController.saveHeatMap("config/heatMap.json");*/
        Master master = new Master();
        master.execute(args);

    }
}
