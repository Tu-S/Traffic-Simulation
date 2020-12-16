package ru.nsu.team.controller.simulator;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MinimalisticRoadProcessing implements Runnable {
    private final Set<Node> activeNodes;
    private final Road targetRoad;
    private final CountDownLatch latch;
    private final int timeFrameStart;

    public MinimalisticRoadProcessing(int timeFrameStart, Road targetRoad, Set<Node> activeNodes, CountDownLatch latch) {
        this.activeNodes = activeNodes;
        this.targetRoad = targetRoad;
        this.latch = latch;
        this.timeFrameStart = timeFrameStart;
    }

    private boolean checkLaneChange(TrafficParticipant car) {
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
        int timePassed = 0;
        if (checkLaneChange(car)) {
            int targetLane = desiredLane(car);
            while (checkLaneChange(car)) {
                //TODO check if space is empty
                //TODO keep moving forward
                int currentLane = car.getPosition().getCurrentLane();
                car.getPosition().setCurrentLane(currentLane + (targetLane - currentLane > 0 ? 1 : -1));
                saveCarState(car, timeFrameStart + timePassed);
            }
        }
        moveCarStraight(car);
        saveCarState(car, timeFrameStart + timePassed);
    }

    private void moveCarStraight(TrafficParticipant car) {

    }


    private void saveCarState(TrafficParticipant car, int time) { // This is a placeholder for sending state to playback
    }


    @Override
    public void run() {
        List<TrafficParticipant> queue = new ArrayList<>(targetRoad.getTrafficParticipants());
        for (TrafficParticipant car : queue) {
            processCar(car);
        }
        latch.countDown();
    }
}

