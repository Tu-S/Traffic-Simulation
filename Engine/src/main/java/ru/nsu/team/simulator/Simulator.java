package ru.nsu.team.simulator;

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
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));
        Set<Road> activeRoads = Collections.synchronizedSet(map.getRoads().parallelStream().filter(r -> r.getTrafficParticipantsNumber() > 0).collect(Collectors.toCollection(HashSet::new)));
        Set<Node> activeNodes = Collections.synchronizedSet(new HashSet<Node>());
        while (!activeRoads.isEmpty()) {
            //TODO check for edge-cases
            latch = new CountDownLatch(activeNodes.size());
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
