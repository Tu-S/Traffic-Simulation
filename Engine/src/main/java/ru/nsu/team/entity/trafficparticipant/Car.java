package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Node;

public class Car {
    private int speed;
    private int timeLeft;
    private int maxSpeed;
    private Node destination;
    private Path path;
    private int id;


    public Car(int id, int maxSpeed, Node destination, Path path) {
        this.destination = destination;
        this.maxSpeed = maxSpeed;
        this.path = path;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDestinationId() {
        return destination.getId();
    }

    public int getPathLength() {
        return path.getPathLength();
    }

    public int getRoadId(int n) {
        return path.getRoadId(n);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isDestination(Node node) {
        return node.equals(destination);
    }
}