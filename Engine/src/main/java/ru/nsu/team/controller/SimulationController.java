package ru.nsu.team.controller;

import ru.nsu.team.entity.playback.PlayBackBuilder;
import ru.nsu.team.entity.playback.Playback;
import ru.nsu.team.entity.report.Report;
import ru.nsu.team.entity.report.ReportBuilder;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.Position;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;


public class SimulationController {

    private RoadMapConfiguration mapConfig;
    private RoadMap roadMap;

    public void pause() {
    }


    public void run(String fileName) {
        //test for load and save
        /*prepareMap(fileName);
        Car car = new Car(666, 70, new Node(1), new Path());
        TrafficParticipant tr = new TrafficParticipant(car, new Position(), 1);
        roadMap.getRoadN(0).addTrafficParticipant(tr);
        Road tmpA = roadMap.getRoadN(1);
        roadMap.addActiveRoad(tmpA);
        Road tmpC = roadMap.getRoadN(2);
        roadMap.addCalculatedRoad(tmpC);
        System.out.println(roadMap.getStart());
        System.out.println(roadMap.getSpawnerN(0).getConfigN(0).getStart());*/
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

    public Playback getPlayback() {
        PlayBackBuilder builder = new PlayBackBuilder();
        return builder.getPlayback();
    }


}
