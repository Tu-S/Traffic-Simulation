package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;

import ru.nsu.team.entity.trafficparticipant.*;


public class Road {

    private int maxSpeed;
    private ArrayList<Lane> lanes;
    private ArrayList<TrafficParticipant> trafficParticipants;
    private int length;
    private Node from;
    private Node to;

    public Road(Node from, Node to, int maxSpeed, int length, int numberOfLines) {
        this.from = from;
        this.to = to;
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.lanes = new ArrayList<>(numberOfLines);
    }


    public void deleteCar(Car car) {

    }

    public void addCar(Car car) {
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getLength() {
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

}
