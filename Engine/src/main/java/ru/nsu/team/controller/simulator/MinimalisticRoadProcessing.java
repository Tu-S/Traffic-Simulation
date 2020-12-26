package ru.nsu.team.controller.simulator;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MinimalisticRoadProcessing implements Runnable {
    private final Set<Node> activeNodes;
    private final Road targetRoad;
    private final CountDownLatch latch;
    private final int timeFrameStart;
    private final HashMap<Integer, TrafficParticipant> blockingVehicles;

    public MinimalisticRoadProcessing(int timeFrameStart, Road targetRoad, Set<Node> activeNodes, CountDownLatch latch) {
        this.activeNodes = activeNodes;
        this.targetRoad = targetRoad;
        this.latch = latch;
        this.timeFrameStart = timeFrameStart;
        this.blockingVehicles = new HashMap<>();
    }

    private TrafficParticipant getBlocking(int lane) {
        return blockingVehicles.get(lane);
    }

    private void setBlocking(TrafficParticipant tp) {
        blockingVehicles.put(tp.getPosition().getCurrentLane(), tp);
    }


    private boolean checkLaneChange(TrafficParticipant car) {
        if (car.getCar().getPath().getRoads().isEmpty()) {
            return false;
        }
        return !targetRoad.getLaneN(car.getPosition().getCurrentLane()).leadsTo(car.getCar().getPath().getNextRoad());
    }

    private int desiredLane(TrafficParticipant car) { // TODO: Change data structures for better performance
        for (int i = 0; i < targetRoad.getLanesNumber(); i++) {
            if (!targetRoad.getLaneN(i).leadsTo(car.getCar().getPath().getNextRoad())) {
                return i;
            }
        }
        throw new IllegalStateException("Car is lost, no lane leads to the target");
    }

    private void processCar(TrafficParticipant car) {
        System.out.println(car);
        int timePassed = 2;
        if (checkLaneChange(car)) {
            int targetLane = desiredLane(car);
            int fromLane = car.getPosition().getCurrentLane();
            while (checkLaneChange(car)) {
                //TODO check if space is empty
                //TODO keep moving forward
                int currentLane = car.getPosition().getCurrentLane();
                car.getPosition().setCurrentLane(currentLane + (targetLane - currentLane > 0 ? 1 : -1));
                saveCarState(car, timeFrameStart + timePassed);
            }
        }
        moveCarStraight(car);
        setBlocking(car);
        saveCarState(car, timeFrameStart + timePassed);
    }

    private void moveCarStraight(TrafficParticipant participant) {
        PositionOnRoad position = participant.getPosition();
        System.out.println("Moving from " + position);
        Car car = participant.getCar();
        double distance;
        double possibleDistance = calculateDistanceByTime(participant, car.getTimeLeft());
        TrafficParticipant block = getBlocking(position.getCurrentLane());
        if (block != null) {
            // Node is blocked by another car

            distance = participant.getPosition().getPosition() - block.getPosition().getPosition() - block.getCar().getInterCarDistance() - car.getInterCarDistance();

            if (distance > possibleDistance) {
                position.setPosition(position.getPosition() - possibleDistance);
                updateSpeedAfterDistance(car, possibleDistance);
            } else {
                position.setPosition(position.getPosition() - distance);
                updateSpeedAfterDistance(car, distance);
            }


        } else {
            // Node is not blocked by another car
            if (possibleDistance >= position.getPosition()) {
                // Car can reach the node
                activeNodes.add(targetRoad.getExitNode());
                targetRoad.getExitNode().addPendingCar(participant);
                position.setPosition(0);
                updateSpeedAfterDistance(car, position.getPosition());
                // TODO offset
                // TODO decrease speed
                // TODO check traffic light
            } else {
                // Car can't reach the node
                position.setPosition(position.getPosition() - possibleDistance);
                updateSpeedAfterDistance(car, possibleDistance);
                //TODO update speed
            }
        }

        System.out.println("To " + position);
    }

    private double calculateDistanceByTime(TrafficParticipant participant, int time) {
        Car car = participant.getCar();
        double acceleration = car.getAcceleration();
        int timeOfAcceleration = (int) ((car.getMaxSpeed() - car.getSpeed()) / Car.DEFAULT_ACCELERATION);
        double distanceOfAcceleration = acceleration * timeOfAcceleration * timeOfAcceleration / 2 + car.getSpeed() * timeOfAcceleration;
        if (timeOfAcceleration > time) {
            return acceleration * acceleration / 2 * time + car.getSpeed() * time;
        }
        return distanceOfAcceleration + car.getMaxSpeed() * (time - timeOfAcceleration);
    }

    private int calculateTimeByDistance(Car car, double distance) {
        double acceleration = car.getAcceleration();
        int timeOfAcceleration = (int) ((car.getMaxSpeed() - car.getSpeed()) / car.getAcceleration());
        double distanceOfAcceleration = acceleration * timeOfAcceleration * timeOfAcceleration / 2 + car.getSpeed() * timeOfAcceleration;
        if (distanceOfAcceleration > distance) {
            return solveTimeSqEq(acceleration, car.getSpeed(), distance);
        }
        return timeOfAcceleration + (int) ((distance - distanceOfAcceleration) / car.getMaxSpeed());
    }

    private int calculateTimeByDistance(TrafficParticipant participant, double distance) {
        return calculateTimeByDistance(participant.getCar(), distance);
    }

    private void updateSpeedAfterTime(Car car, int time) {
        double speed = car.getSpeed();
        double acceleration = car.getAcceleration();
        int timeOfAcceleration = (int) ((car.getMaxSpeed() - speed) / acceleration);
        time = Math.min(time, timeOfAcceleration);
        speed += time * acceleration;
        car.setSpeed(speed);
    }

    private void updateSpeedAfterDistance(Car car, double distance) {
        int time = calculateTimeByDistance(car, distance);
        updateSpeedAfterTime(car, time);
    }

    private int solveTimeSqEq(double acceleration, double speed, double distance) {
        // TODO check solution
        double d = Math.sqrt(speed * speed + 2 * acceleration * distance);
        return (int) ((-speed + d) / (acceleration));
    }


    private void saveCarState(TrafficParticipant car, int time) { // This is a placeholder for sending state to playback
    }


    @Override
    public void run() {
        try {
            System.out.println("Running RP");
            List<TrafficParticipant> queue = new ArrayList<>(targetRoad.getTrafficParticipants());
            queue.sort(TrafficParticipant::compareTo);
            for (TrafficParticipant car : queue) {
                processCar(car);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}

