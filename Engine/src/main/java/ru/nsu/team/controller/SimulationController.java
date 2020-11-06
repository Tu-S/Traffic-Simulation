package ru.nsu.team.controller;

import ru.nsu.team.entity.playback.PlayBackBuilder;
import ru.nsu.team.entity.playback.Playback;
import ru.nsu.team.entity.report.Report;
import ru.nsu.team.entity.report.ReportBuilder;
import ru.nsu.team.jsonparser.RoadMapConfigurationParser;

public class SimulationController {


    public void pause() {
    }


    public void run(String fileName) {




    }

    public void stop() {
    }


    public void resume() {
    }

    public void save() {
    }

    public void load() {
    }

    public Report getReport() {
        ReportBuilder builder = new ReportBuilder();

        return builder.getReport();
    }

    public Playback getPlayback() {
        PlayBackBuilder builder = new PlayBackBuilder();
        return builder.getPlayback();
    }


}
