package ru.nsu.team.simulator;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MinimalisticRoadProcessing implements Runnable, Serializable {

    private static final Logger LOG = Logger.getRootLogger();

    private final Set<Node> activeNodes;
    private final Road targetRoad;
    private final CountDownLatch latch;
    private final int timeFrameStart;
    private final HashMap<Integer, TrafficParticipant> blockingVehicles;
    private final PlaybackBuilder playbackBuilder;
    private final HeatmapBuilder reporterBuilder;
    private final int timeInterval;

    public MinimalisticRoadProcessing(int timeFrameStart, int timeInterval, Road targetRoad, Set<Node> activeNodes,
                                      CountDownLatch latch, PlaybackBuilder playbackBuilder,
                                      HeatmapBuilder reporterBuilder) {
        this.activeNodes = activeNodes;
        this.targetRoad = targetRoad;
        this.latch = latch;
        this.timeFrameStart = timeFrameStart;
        this.blockingVehicles = new HashMap<>();
        this.playbackBuilder = playbackBuilder;
        this.reporterBuilder = reporterBuilder;
        this.timeInterval = timeInterval;
    }

    private TrafficParticipant getBlocking(int lane) {
        return blockingVehicles.get(lane);
    }

    private void setBlocking(TrafficParticipant tp) {
        blockingVehicles.put(tp.getPosition().getCurrentLaneId(), tp);
    }


    private boolean checkLaneChange(TrafficParticipant car) {
        if (car.getCar().getPath().getRoads().isEmpty()) {
            return false;
        }
        return !targetRoad.getLaneN(car.getPosition().getCurrentLaneId()).leadsTo(car.getCar().getPath().getNextRoad());
    }

    private int desiredLane(TrafficParticipant car) { // TODO: Change data structures for better performance
        for (int i = 0; i < targetRoad.getLanesNumber(); i++) {
            if (targetRoad.getLaneN(i).leadsTo(car.getCar().getPath().getNextRoad())) {
                return i;
            }
        }
        throw new IllegalStateException("Car is lost, no lane leads to the target");
    }

    private void processCar(TrafficParticipant car) {
        LOG.debug("Processing car " + car);
        int timePassed = 2;
        if (checkLaneChange(car)) {
            int targetLane = desiredLane(car);
            car.getCar().setTimeLeft(car.getCar().getTimeLeft() - timePassed);
            int fromLane = car.getPosition().getCurrentLaneId();
            while (checkLaneChange(car)) {
                //TODO check if space is empty
                //TODO keep moving forward
                int currentLane = car.getPosition().getCurrentLaneId();
                car.getPosition().setCurrentLane(currentLane + (targetLane - currentLane > 0 ? 1 : -1));
                saveCarState(car, timeFrameStart + timeInterval - car.getCar().getTimeLeft() + timePassed);
            }
        }
        moveCarStraight(car);
        setBlocking(car);
        saveCarState(car, timeFrameStart + timePassed);
        LOG.debug(car);
    }

    private void moveCarStraight(TrafficParticipant participant) {
        //System.out.println(participant);
        PositionOnRoad position = participant.getPosition();
        Car car = participant.getCar();
        if(car.getSpeed()>participant.getPosition().getCurrentLane().getMaxSpeed()){
            car.setSpeed(participant.getPosition().getCurrentLane().getMaxSpeed());

        }
        double distance;
        double possibleDistance = calculateDistanceByTime(participant, car.getTimeLeft());
        TrafficParticipant block = getBlocking(position.getCurrentLaneId());
        if (block != null) {
            // Node is blocked by another car
            distance = participant.getPosition().getPosition() - block.getPosition().getPosition()
                    - block.getCar().getInterCarDistance() - car.getInterCarDistance();

            if (distance > possibleDistance) {
                position.setPosition(position.getPosition() - possibleDistance);
                updateAfterDistance(participant, possibleDistance);
            } else {
                LOG.debug("Node is blocked by another car: " + block);
                position.setPosition(position.getPosition() - distance);
                updateAfterDistance(participant, distance);
            }


        } else {
            // Node is not blocked by another car
            if (possibleDistance >= position.getPosition()) {
                LOG.debug("["+(timeFrameStart+timeInterval-car.getTimeLeft())+"] "+car + " reached node");
                // Car can reach the node
                activeNodes.add(targetRoad.getExitNode());
                targetRoad.getExitNode().addPendingCar(participant);
                updateAfterDistance(participant, position.getPosition());
                position.setPosition(0);

                // TODO offset
                // TODO decrease speed
                // TODO check traffic light
            } else {
                // Car can't reach the node
                position.setPosition(position.getPosition() - possibleDistance);
                updateAfterDistance(participant, possibleDistance);
                //TODO update speed
            }
        }
        if (participant.getCar().getTimeLeft() < 0) {
            participant.getCar().setTimeLeft(0);
        }
        saveCarState(participant, timeFrameStart + timeInterval - participant.getCar().getTimeLeft());
    }

    private double calculateDistanceByTime(TrafficParticipant participant, int time) {
        Car car = participant.getCar();
        double acceleration = car.getAcceleration();
        double maxSpeed = Math.min(car.getMaxSpeed(),
                targetRoad.getLaneN(participant.getPosition().getCurrentLaneId()).getMaxSpeed());
        int timeOfAcceleration = (int) ((maxSpeed - car.getSpeed()) / Car.DEFAULT_ACCELERATION);
        double distanceOfAcceleration =
                acceleration * timeOfAcceleration * timeOfAcceleration / 2 + car.getSpeed() * timeOfAcceleration;
        if (timeOfAcceleration > time) {
            return acceleration * acceleration / 2 * time + car.getSpeed() * time;
        }
        return distanceOfAcceleration + maxSpeed * (time - timeOfAcceleration);
    }

    private int calculateTimeByDistance(TrafficParticipant participant, double distance) {
        Car car = participant.getCar();
        double acceleration = car.getAcceleration();
        double maxSpeed = Math.min(car.getMaxSpeed(),
                targetRoad.getLaneN(participant.getPosition().getCurrentLaneId()).getMaxSpeed());
        int timeOfAcceleration = (int) ((maxSpeed - car.getSpeed()) / car.getAcceleration());
        double distanceOfAcceleration =
                acceleration * timeOfAcceleration * timeOfAcceleration / 2 + car.getSpeed() * timeOfAcceleration;
        if (distanceOfAcceleration > distance) {
            return solveTimeSqEq(acceleration, car.getSpeed(), distance);
        }
        return timeOfAcceleration + (int) ((distance - distanceOfAcceleration) / maxSpeed);
    }


    private void updateAfterTime(Car car, int time) {
        double speed = car.getSpeed();
        double acceleration = car.getAcceleration();
        int timeOfAcceleration = (int) ((car.getMaxSpeed() - speed) / acceleration);
        timeOfAcceleration = Math.min(time, timeOfAcceleration);
        speed += timeOfAcceleration * acceleration;
        car.setTimeLeft(car.getTimeLeft() - time);
        car.setSpeed(speed);
    }

    private void updateAfterDistance(TrafficParticipant participant, double distance) {
        int time = calculateTimeByDistance(participant, distance);
        if (distance > 0) {
            time++;
        }
        updateAfterTime(participant.getCar(), time);
    }

    private int solveTimeSqEq(double acceleration, double speed, double distance) {
        // TODO check solution
        double d = Math.sqrt(speed * speed + 2 * acceleration * distance);
        return (int) ((-speed + d) / (acceleration));
    }


    private void saveCarState(TrafficParticipant car, int time) {
        playbackBuilder.addCarState(car, time, true);
    }


    @Override
    public void run() {
        LOG.debug("Starting processing road " + targetRoad.getId());
        try {
            List<TrafficParticipant> queue = new ArrayList<>(targetRoad.getTrafficParticipants());
            queue.sort(TrafficParticipant::compareTo);
            for (TrafficParticipant car : queue) {
                processCar(car);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            LOG.debug("Finished processing road " + targetRoad.getId());
            latch.countDown();
        }
    }
}

