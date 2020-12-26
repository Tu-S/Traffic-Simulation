package ru.nsu.team.controller.simulator;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Simulator extends Thread {

    private final int timeInterval;
    private final RoadMap map;
    private final Semaphore runPermission;
    private CountDownLatch latch;
    private boolean paused;


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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Set<Road> activeRoads = Collections.synchronizedSet(map.getRoads().parallelStream().filter(r -> r.getTrafficParticipantsNumber() > 0).collect(Collectors.toCollection(HashSet::new)));
        Set<Node> activeNodes = Collections.synchronizedSet(new HashSet<Node>());
        while (!activeRoads.isEmpty()) {
            //TODO check for edge-cases
            latch = new CountDownLatch(activeRoads.size());
            for (Road road : activeRoads) {
                executor.submit(new MinimalisticRoadProcessing((int) map.getCurrentTime(), road, activeNodes, latch));
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
                executor.submit(new MinimalisticNodeProcessing(node, activeRoads, latch));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            activeNodes.clear();
        }
    }

    private void resetTime(RoadMap rm) {
        rm.getRoads().forEach(r -> r.getTrafficParticipants().forEach(p -> p.getCar().setTimeLeft(timeInterval)));
        rm.getCourseSet().forEach(c -> c.resetTimeLeft(timeInterval));
        //TODO traffic lights
    }


    private void spawnCars(RoadMap rm) {
        rm.getSpawners().forEach(s -> s.spawn((int) rm.getCurrentTime(), timeInterval));
    }

    @Override
    public void run() {
        try {
            while (map.getCurrentTime() < map.getEndTime()) {
                System.out.println(map.getCurrentTime());
                waitIfPaused();
                resetTime(map);
                spawnCars(map);
                runCycle();
                map.setCurrentTime(map.getCurrentTime() + timeInterval);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
