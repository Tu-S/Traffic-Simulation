package ru.nsu.team.controller;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.ReporterBuilder;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.entity.statistics.CarState;
import ru.nsu.team.entity.statistics.RoadCongestion;
import ru.nsu.team.entity.statistics.Timeline;
import ru.nsu.team.other.KeyValuePair;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.RoadModelCreator;
import ru.nsu.team.savers.CarStateSaver;
import ru.nsu.team.savers.HeatMapSaver;
import ru.nsu.team.savers.RoadMapSaver;
import ru.nsu.team.simulator.Simulator;

import java.util.List;


public class SimulationController implements Runnable {

    private RoadMapConfiguration mapConfig;
    private RoadMap roadMap;
    private List<KeyValuePair<Timeline, List<RoadCongestion>>> heatMap;
    private List<CarState> carStates;
    private PlaybackBuilder playbackBuilder;
    private ReporterBuilder reporterBuilder;
    private String mapSavePath;
    private String heatMapSavePath;
    private String carStateSavePath;
    private String mapLoadPath;
    private Simulator sim;


    public SimulationController(String mapLoadPath, String heatMapSavePath, String carStateSavePath) {
        this.mapLoadPath = mapLoadPath;
        this.heatMapSavePath = heatMapSavePath;
        this.carStateSavePath = carStateSavePath;
    }

    public void pause() {
    }


    private void prepareMap(String fileName) {
        RoadMapReader roadMapReader = new RoadMapReader();
        this.mapConfig = roadMapReader.getMapConfig(fileName);
        RoadModelCreator mapCreator = new RoadModelCreator();
        assert mapConfig != null;
        this.roadMap = mapCreator.createRoadMap(mapConfig);
    }

    public void stop() {
        sim.stopSimulation();
        try {
            sim.join(5000);
            saveHeatMap(this.heatMapSavePath);
            saveCarStates(this.carStateSavePath);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void resume() {
        sim.unpause();
    }

    public void saveRoadMap(String fileName) {
        RoadMapSaver saver = new RoadMapSaver();
        saver.saveMap(roadMap, mapConfig, fileName);
    }

    public void saveCarStates(String fileName) {
        CarStateSaver saver = new CarStateSaver();
        saver.saveCarsState(playbackBuilder.getPlayback().getCarStates(), fileName);
    }

    public void saveHeatMap(String fileName) {
        HeatMapSaver saver = new HeatMapSaver();
        saver.saveHeatMap(reporterBuilder.getReporter().getHeatmap(), fileName);
    }

    @Override
    public void run() {
        prepareMap(this.mapLoadPath);
        playbackBuilder = new PlaybackBuilder();
        reporterBuilder = new ReporterBuilder();
        sim = new Simulator(30, roadMap, playbackBuilder, reporterBuilder);
        sim.start();
        try {
            sim.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Saving stuff");
        saveHeatMap(this.heatMapSavePath);
        saveCarStates(this.carStateSavePath);
        System.out.println("Done!");
        System.exit(0);
    }
}
