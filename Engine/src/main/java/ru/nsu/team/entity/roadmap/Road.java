package ru.nsu.team.entity.roadmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.nsu.team.entity.trafficparticipant.*;


public class Road implements Serializable {

    private List<Lane> lanes;
    private List<TrafficParticipant> trafficParticipants;
    private double length;
    private Node from;
    private Node to;
    private int id;
    private boolean isMainRoad;
    private boolean isEphemeral;

    public Road(int id, Node from, Node to) {
        this.from = from;
        this.to = to;
        this.lanes = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
        this.id = id;
        this.isEphemeral=false;
    }

    public Road(int id, Node from, Node to, int lanes, double maxSpeed) {
        this.from = from;
        this.to = to;
        this.lanes = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
        this.id = id;
        for (int i = 0; i < lanes; i++) {
            addLane(maxSpeed);
        }
        this.isEphemeral=false;
    }


    public void setMainRoad(boolean isMainRoad) {
        this.isMainRoad = isMainRoad;
    }

    public boolean isMainRoad() {
        return isMainRoad;
    }

    public boolean isEphemeral() {
        return isEphemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        isEphemeral = ephemeral;
    }

    public int getId() {
        return id;
    }

    synchronized public void deleteTrafficParticipant(TrafficParticipant car) {
        lanes.get(car.getPosition().getCurrentLaneId()).removeTrafficParticipant(car);
        trafficParticipants.remove(car);
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    synchronized public void addTrafficParticipant(TrafficParticipant car) {
        Lane lane = lanes.get(car.getPosition().getCurrentLaneId());
        lane.addTrafficParticipant(car);
        trafficParticipants.add(car);
    }

    public void setTrafficParticipants(List<TrafficParticipant> trafficParticipants) {
        this.trafficParticipants = trafficParticipants;
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

    public void addLane(double maxSpeed) {
        lanes.add(new Lane(maxSpeed, this));
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

    @Override
    public String toString() {
        return "Road " + id;
    }
}
