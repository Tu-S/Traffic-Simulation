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

    public NodeGenome(Node node) {
        this.id = node.getId();
        List<TrafficLight> tr = new ArrayList<>(node.getTrafficLights().size());

        for (var t : node.getTrafficLights()) {
            tr.add(copyTrafficLight(t));
        }

        this.trafficLights = tr;

    }

    private TrafficLight copyTrafficLight(TrafficLight trafficLight) {
        var res = new TrafficLight();
        for (var c : trafficLight.getConfigs()) {
            res.addConfig(new TrafficLightConfig(c.getDelay()));
        }
        return res;
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
