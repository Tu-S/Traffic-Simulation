package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.TrafficLight;
import ru.nsu.team.entity.roadmap.TrafficLightConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeGenome {
    private List<TrafficLight> trafficLights;
    private int id;

    public NodeGenome(int id) {
        this.id = id;
        this.trafficLights = new ArrayList<>();
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public int getId() {
        return id;
    }

    public void setTrafficLights(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }

    public void setId(int id) {
        this.id = id;
    }

}
