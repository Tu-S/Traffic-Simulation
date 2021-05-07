package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Road;

import java.util.ArrayList;
import java.util.List;

public class RoadGenome {

    private List<LaneGenome> lanes;
    private int id;
    private boolean isMainRoad;

    public RoadGenome(Road road) {
        this.id = road.getId();
        this.isMainRoad = road.isMainRoad();
        int lNumber = road.getLanesNumber();
        var lanes = road.getLanes();
        this.lanes = new ArrayList<>(lNumber);
        for(int i = 0 ; i < lNumber;i++){
            this.lanes.add(new LaneGenome(lanes.get(i)));
        }
    }

    public List<LaneGenome> getLanes() {
        return lanes;
    }

    public int getId() {
        return id;
    }

    public boolean isMainRoad() {
        return isMainRoad;
    }

    public void setLanes(List<LaneGenome> lanes) {
        this.lanes = lanes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMainRoad(boolean mainRoad) {
        isMainRoad = mainRoad;
    }
}
