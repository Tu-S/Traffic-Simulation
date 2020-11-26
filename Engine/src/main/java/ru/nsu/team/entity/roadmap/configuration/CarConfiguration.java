package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.trafficparticipant.Car;

import java.util.ArrayList;
import java.util.List;

public class CarConfiguration {
    private double speed;
    private double timeLeft;
    private double maxSpeed;
    private ArrayList<Integer> path;


    public CarConfiguration(Car car) {
        this.speed = car.getSpeed();
        this.timeLeft = car.getTimeLeft();
        this.maxSpeed = Car.DEFAULT_MAX_SPEED;
        int pathLen = car.getPath().getRoads().size();
        this.path = new ArrayList<>(pathLen);
        for (int i = 0; i < pathLen; i++) {
            path.add(car.getRoadId(i));
        }
    }

    /*public int getSpeed() {
        return speed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }*/

    public List<Integer> getPath() {
        return path;
    }
}
