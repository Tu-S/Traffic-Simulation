package ru.nsu.team.controller;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Simulator extends Thread {

    private int timeInterval;
    private RoadMap map;
    private Semaphore runPermission;
    private CountDownLatch latch;
    private boolean paused;

    private class RoadProcessing implements Runnable {
        Set<Node> activeNodes;
        Road targetRoad;

        public RoadProcessing(Road targetRoad, Set<Node> activeNodes) {
            this.activeNodes = activeNodes;
            this.targetRoad = targetRoad;
        }

        private boolean checkLaneChange(TrafficParticipant car) {
            return !targetRoad.getLaneN(car.getPosition().getCurrentLane()).leadsTo(car.getCar().getPath().getNextRoad());
        }

        private int desiredLane(TrafficParticipant car) {
            for (int i = 0; i < targetRoad.getLanesNumber(); i++) {
                if (!targetRoad.getLaneN(i).leadsTo(car.getCar().getPath().getNextRoad())) {
                    return i;
                }
            }
            throw new IllegalStateException("Car is lost, no lane leads to the target");
        }

        private void processCar(TrafficParticipant car) {
            if (checkLaneChange(car)) {
                while (checkLaneChange(car)) {
                    desiredLane(car);
                }
            }
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

    private class NodeProcessing implements Runnable {
        Set<Road> activeRoads;
        Node targetNode;

        public NodeProcessing(Node targetNode, Set<Road> activeRoads) {
            this.activeRoads = activeRoads;
            this.targetNode = targetNode;
        }

        private void processCar(TrafficParticipant car) {

        }

        @Override
        public void run() {
            List<TrafficParticipant> queue = new ArrayList<>();

            for (TrafficParticipant car : queue) {
                processCar(car);
            }
            latch.countDown();
        }
    }

    public Simulator(int timeInterval, RoadMap map) {
        super();
        this.timeInterval = timeInterval;
        this.map = map;
        this.runPermission = new Semaphore(1);
        this.paused = false;
    }

    public void pause() {
        if (!paused) {
            paused = true;
            if (runPermission.availablePermits() == 1) {
                try {
                    runPermission.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unpause() {
        if (paused) {
            paused = false;
            runPermission.release();
        }
    }

    private void waitIfPaused() {
        try {
            runPermission.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runPermission.release();
    }

    private void runCycle() {
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));
        Set<Road> activeRoads = Collections.synchronizedSet(new HashSet<Road>(map.getRoads()));
        Set<Node> activeNodes = Collections.synchronizedSet(new HashSet<Node>());
        while (!activeRoads.isEmpty()) {
            //TODO check for edge-cases
            latch = new CountDownLatch(activeNodes.size());
            for (Road road : activeRoads) {
                executor.submit(new RoadProcessing(road, activeNodes));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            latch = new CountDownLatch(activeNodes.size());
            activeRoads.clear();
            for (Node node : activeNodes) {
                executor.submit(new NodeProcessing(node, activeRoads));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    @Override
    public void run() {
        while (map.getCurrentTime() < map.getEndTime()) {
            waitIfPaused();
            runCycle();
            map.setCurrentTime(map.getCurrentTime() + timeInterval);
        }
    }
}
