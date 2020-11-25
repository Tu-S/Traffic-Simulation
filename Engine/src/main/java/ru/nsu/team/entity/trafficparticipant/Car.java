package ru.nsu.team.entity.trafficparticipant;

import ru.nsu.team.entity.roadmap.Node;

public class Car {

    static int nextId = 0;

    private int speed;
    private int timeLeft;
    private double maxSpeed;
    private Path path;
    private final int id;

    public static double DEFAULT_MAX_SPEED = 120;
    public static double DEFAULT_ACCELERATION = 1.5d;
    public static double DEFAULT_DISTANCE = 12;
    public static double DEFAULT_CAR_SIZE = 6;


    public Car(int id, double maxSpeed, Path path) {
        this.maxSpeed = maxSpeed;
        this.path = path;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getPathLength() {
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

    public int getSpeed() {
        return speed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public double getMaxSpeed() {
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

    public static synchronized int getNextId() {
        return nextId++;
    }

    public static synchronized void setNextId(int id) {
        nextId = id;
    }
}
