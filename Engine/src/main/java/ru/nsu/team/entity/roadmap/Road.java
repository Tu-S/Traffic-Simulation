package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;

import ru.nsu.team.entity.trafficparticipant.*;


public class Road {

    private int maxSpeed;
    private ArrayList<Lane> lanes;
    private ArrayList<TrafficParticipant> trafficParticipants;
    private double length;
    private Node from;
    private Node to;
    private int id;

    public Road(int id, Node from, Node to, int maxSpeed, int numberOfLines) {
        this.from = from;
        this.to = to;
        this.maxSpeed = maxSpeed;
        this.lanes = new ArrayList<>(numberOfLines);
        for(int i = 0 ; i < numberOfLines;i++){
            lanes.add(new Lane());
        }
        this.trafficParticipants = new ArrayList<>();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void deleteTrafficParticipant(TrafficParticipant car) {

    }

    public void addTrafficParticipant(TrafficParticipant car) {
        Lane lane = lanes.get(car.getPosition().getCurrentLane());
        lane.addTrafficParticipant(car);
        trafficParticipants.add(car);
    }

    public int getMaxSpeed() {
        return maxSpeed;
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

    public int getTrafficParticipantsNumber() {
        return trafficParticipants.size();
    }

    public TrafficParticipant getTrafficParticipantN(int n) {
        return trafficParticipants.get(n);
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
