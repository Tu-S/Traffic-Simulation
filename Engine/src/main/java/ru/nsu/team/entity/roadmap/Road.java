package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.nsu.team.entity.trafficparticipant.*;


public class Road {

    private List<Lane> lanes;
    private List<TrafficParticipant> trafficParticipants;
    private double length;
    private Node from;
    private Node to;
    private int id;
    private boolean isMainRoad;

    public Road(int id, Node from, Node to) {
        this.from = from;
        this.to = to;
        this.lanes = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
        this.id = id;
    }

    public void setMainRoad(boolean isMainRoad) {
        this.isMainRoad = isMainRoad;
    }

    public int getId() {
        return id;
    }

    public void deleteTrafficParticipant(TrafficParticipant car) {

    }

    public List<Lane> getLanes() {
        return lanes;
    }

    public void addTrafficParticipant(TrafficParticipant car) {
        Lane lane = lanes.get(car.getPosition().getCurrentLane());
        lane.addTrafficParticipant(car);
        trafficParticipants.add(car);
    }

    public double getLength() {
        return length;
    }

    public Node getExitNode() {
        return to;
    }

    public int getLanesNumber() {
        return lanes.size();
    }

    public Lane getLaneN(int n) {
        return lanes.get(n);

    }

    public List<Lane> getIterator() {
        return lanes;
    }

    public int getTrafficParticipantsNumber() {
        return trafficParticipants.size();
    }

    public TrafficParticipant getTrafficParticipantN(int n) {
        return trafficParticipants.get(n);
    }

    public List<TrafficParticipant> getTrafficParticipants() {
        return trafficParticipants;
    }

    public void addLane(Lane lane) {
        lanes.add(lane);
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
