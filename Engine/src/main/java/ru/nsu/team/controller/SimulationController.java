package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.other.KeyValuePair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class SimulationController {

    private RoadMapConfiguration mapConfig;
    private RoadMap roadMap;
    private List<KeyValuePair<Timeline, List<RoadCongestion>>> heatMap;
    private List<CarState> carStates;


    public void execute(String[] args){
        String command = args[0];
        switch(command){
            case "run":
                run(args[1]);
                break;
            case "pause":
                //TODO
                pause();
                break;
            case "stop":
                stop(args[1],args[2],args[3]);
                break;
            case "resume":
                resume();
                break;
            case "save":
                save(args[1],args[2],args[3]);
                break;
        }

    }


    public void pause() {
    }


    public void run(String fileName) {
        //test for load and save
        prepareMap(fileName);
        CarStateReader carStateReader;
        List<CarState> statesFromFile;
        try {

            ReportReader reportReader = new ReportReader();
            List<KeyValuePair<Timeline, List<RoadCongestion>>> report = reportReader.getReportFromJson("config/heatMap.json");
            carStateReader = new CarStateReader();
            statesFromFile = carStateReader.getCarStateList("config/carStates.json");
        } catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
            return;
        }
        Path path = new Path();
        path.addRoadToPath(roadMap.getRoadN(0));
        path.addRoadToPath(roadMap.getRoadN(2));
        Car car = new Car(666, 70, path);

        List<TrafficParticipant> trs = new ArrayList<>();
        TrafficParticipant tr0 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(0), 0, 0));
        trs.add(tr0);
        TrafficParticipant tr1 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(0), 0, 0));
        trs.add(tr1);
        TrafficParticipant tr2 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(1), 0, 0));
        trs.add(tr2);
        TrafficParticipant tr3 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(2), 0, 0));
        trs.add(tr3);
        TrafficParticipant trFake1 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(5), 0, 0));
        //trs.add(trFake1);
        TrafficParticipant trFake2 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(6), 0, 0));
        //trs.add(trFake2);

        for(TrafficParticipant p:trs){
            roadMap.getRoadN(p.getPosition().getCurrentRoad().getId()).addTrafficParticipant(p);
        }

        /*roadMap.getRoadN(0).addTrafficParticipant(tr0);
        roadMap.getRoadN(4).addTrafficParticipant(trFake1);
        roadMap.getRoadN(4).addTrafficParticipant(trFake1);
        roadMap.getRoadN(5).addTrafficParticipant(trFake2);
        roadMap.getRoadN(5).addTrafficParticipant(trFake2);*/
        System.out.println(roadMap.getStart());
        System.out.println(roadMap.getSpawnerN(0).getConfigN(0).getStart());
        roadMap.increaseCurrentTime(300000);
        List<CarState> states = new ArrayList<>();
        for(TrafficParticipant p:trs){
            states.add(new CarState(p, (int) roadMap.getCurrentTime()));
        }
        this.carStates = states;
        List<KeyValuePair<Timeline, List<RoadCongestion>>> heatMap = new ArrayList<>();
        for(int i = 0 ; i < 5; i++){
            List<RoadCongestion> congestions = new ArrayList<>();
            Timeline timeline = new Timeline((int)roadMap.getCurrentTime(),(int)roadMap.getEndTime());
            for(int j = 0; j < 3;j++){
                congestions.add(new RoadCongestion(j, (i+1)*10));
            }
            KeyValuePair<Timeline,List<RoadCongestion>> pair = new KeyValuePair<>(timeline,congestions);
            heatMap.add(pair);
        }
        this.heatMap = heatMap;
    }

    private void prepareMap(String fileName) {
        RoadMapReader roadMapReader = new RoadMapReader();
        this.mapConfig = roadMapReader.getMapConfig(fileName);
        RoadModelCreator mapCreator = new RoadModelCreator();
        assert mapConfig != null;
        this.roadMap = mapCreator.createRoadMap(mapConfig);
    }

    public void stop(String heatMapFilepath,String roadMapFilePath,String carStatesFilePath) {
        //TODO stop calculate traffic
        //TODO collect model data
        // TODO make report
        saveHeatMap(heatMapFilepath);
        saveRoadMap(roadMapFilePath);
        saveCarStates(carStatesFilePath);
    }


    public void resume() {
    }


    public void save(String roadMapFilePath,String heatMapFilePath, String playbackBuilderFilepath){
        saveRoadMap(roadMapFilePath);
        saveHeatMap(heatMapFilePath);
        saveCarStates(playbackBuilderFilepath);
    }

    public void saveRoadMap(String fileName) {
        Saver saver = new Saver();
        saver.saveMap(roadMap, mapConfig, fileName);
    }
    public void saveCarStates(String fileName){
        Saver saver = new Saver();
        saver.saveCarsState(this.carStates,fileName);
    }
    public void saveHeatMap(String fileName){
        Saver saver = new Saver();
        saver.saveHeatMap(this.heatMap,fileName);
    }




    /*public Report getReport() {
        ReportBuilder builder = new ReportBuilder();

        return new Report();//builder.getReport();
    }*/

    /*
    public Playback getPlayback() {
        PlayBackBuilder builder = new PlayBackBuilder();
        return builder.getPlayback();
    }*/


}
