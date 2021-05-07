package ru.nsu.team.genome;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.TrafficLight;

import java.util.ArrayList;
import java.util.List;

public class NodeGenome {
    private List<TrafficLight> trafficLights;
    private int id;

    public NodeGenome(Node node){
        this.id = node.getId();
        this.trafficLights = node.getTrafficLights();

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
