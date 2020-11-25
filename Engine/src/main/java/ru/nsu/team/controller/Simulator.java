package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.RoadMap;

public class Simulator extends Thread {

    private int timeInterval;
    private RoadMap map;

    public Simulator(int timeInterval, RoadMap map) {
        super();
        this.timeInterval = timeInterval;
        this.map = map;
    }

    @Override
    public void run() {

    }
}
