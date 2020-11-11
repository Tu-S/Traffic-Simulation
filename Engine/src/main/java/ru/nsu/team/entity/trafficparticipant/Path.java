package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;

import java.util.ArrayList;

public class Path {
    private ArrayList<Road> roads;
    private int currentRoadNumber;

    public Path() {
        this.currentRoadNumber = 0;
        this.roads = new ArrayList<>();
    }

    public int getPathLength() {
        return roads.size();
    }

    public int getRoadId(int n) {
        return roads.get(n).getId();
    }


    public void addRoadToPath(Road road) {
        roads.add(road);
    }

    public Road getNextRoad() {
        int tmp = currentRoadNumber;
        currentRoadNumber++;
        return roads.get(tmp);
    }


}
