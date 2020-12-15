package ru.nsu.team.controller;

import ru.nsu.team.entity.playback.PlayBackBuilder;
import ru.nsu.team.entity.playback.Playback;

import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;


public class SimulationController {

    private RoadMapConfiguration mapConfig;
    private RoadMap roadMap;

    public void pause() {
    }


    public void run(String fileName) {
        //test for load and save
        prepareMap(fileName);
        Path path = new Path();
        path.addRoadToPath(roadMap.getRoadN(0));
        path.addRoadToPath(roadMap.getRoadN(2));
        Car car = new Car(666, 70, path);

        TrafficParticipant tr = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(0), 0, 0));
        TrafficParticipant tr1 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(4), 0, 0));
        TrafficParticipant tr2 = new TrafficParticipant(car, new PositionOnRoad(roadMap.getRoadN(5), 0, 0));


        roadMap.getRoadN(0).addTrafficParticipant(tr);
        roadMap.getRoadN(4).addTrafficParticipant(tr1);
        roadMap.getRoadN(4).addTrafficParticipant(tr1);
        roadMap.getRoadN(5).addTrafficParticipant(tr2);
        roadMap.getRoadN(5).addTrafficParticipant(tr2);
        System.out.println(roadMap.getStart());
        System.out.println(roadMap.getSpawnerN(0).getConfigN(0).getStart());
        roadMap.increaseCurrentTime(300000);
    }

    private void prepareMap(String fileName) {
        ConfigReader configReader = new ConfigReader();
        this.mapConfig = configReader.getMapConfig(fileName);
        RoadModelCreator mapCreator = new RoadModelCreator();
        assert mapConfig != null;
        this.roadMap = mapCreator.createRoadMap(mapConfig);
    }

    public void stop() {
    }


    public void resume() {
    }

    public void save(String fileName) {
        Saver saver = new Saver();
        saver.saveMap(roadMap, mapConfig, fileName);
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
