package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Road;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Path implements Serializable {
    private ArrayList<Road> roads;
    private int currentRoadNumber;
    private double length;

    public Path() {
        this.currentRoadNumber = 0;
        this.roads = new ArrayList<>();
        this.length = 0;
    }

    public Path(ArrayList<Road> roads) {
        this.currentRoadNumber = 0;
        this.roads = roads;
        this.length = roads.stream().map(Road::getLength).reduce(Double::sum).orElse(0d);
    }

    public double getPathLength() {
        return length;
    }

    public int getRoadId(int n) {
        return roads.get(n).getId();
    }


    public void addRoadToPath(Road road) {
        roads.add(road);
        length += road.getLength();
    }

    public List<Road> getRoads() {
        return roads;
    }

    public Road getNextRoad() {
        return roads.get(0);
    }

    public Road popRoad() {
        length -= getNextRoad().getLength();
        return roads.remove(0);
    }

    public Path copy() {
        return new Path(new ArrayList<>(roads));
    }
}
