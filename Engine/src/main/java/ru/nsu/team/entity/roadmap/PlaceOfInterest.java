package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;

public class PlaceOfInterest {
    private int parkingCapacity;
    private ArrayList<TrafficParticipant> trafficParticipants;
    private ArrayList<Node> nodes;

    public PlaceOfInterest(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        this.trafficParticipants = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {

        nodes.add(node);
    }

    public void addTrafficParticipant(TrafficParticipant car) {
        trafficParticipants.add(car);
    }

    public void deleteCar(Car car) {
    }

    public int getCarsNumber() {
        return trafficParticipants.size();
    }

    public TrafficParticipant getCarN(int n) {
        return trafficParticipants.get(n);
    }

    public int getNodesNumber() {
        return nodes.size();
    }

    public Node getNodeN(int n) {
        return nodes.get(n);
    }
}
