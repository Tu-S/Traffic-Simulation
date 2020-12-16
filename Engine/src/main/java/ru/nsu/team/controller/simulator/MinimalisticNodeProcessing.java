package ru.nsu.team.controller.simulator;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MinimalisticNodeProcessing implements Runnable {
    private final Set<Road> activeRoads;
    private final Node targetNode;
    private final CountDownLatch latch;

    public MinimalisticNodeProcessing(Node targetNode, Set<Road> activeRoads, CountDownLatch latch) {
        this.activeRoads = activeRoads;
        this.targetNode = targetNode;
        this.latch = latch;
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
