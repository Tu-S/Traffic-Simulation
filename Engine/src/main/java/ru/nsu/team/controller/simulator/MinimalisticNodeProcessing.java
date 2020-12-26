package ru.nsu.team.controller.simulator;

import ru.nsu.team.entity.roadmap.Course;
import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
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

    private void processCar(TrafficParticipant participant) {
        //TODO add traffic lights
        System.out.println("Node "+targetNode.getId());
        System.out.println(participant);
        Car car = participant.getCar();
        PositionOnRoad position = participant.getPosition();
        Road road = participant.getPosition().getCurrentRoad();
        Path path = participant.getCar().getPath();
        if (path.getPathLength() == 0) {
            processDestination(participant);
            road.deleteTrafficParticipant(participant);
            return;
        }
        Road nextRoad = path.getNextRoad();
        Course course = selectCourse(position.getCurrentRoad().getLaneN(position.getCurrentLane()), nextRoad);
        int timeLeft = car.getTimeLeft();
        double dist = course.getLength();
        if (timeLeft >= dist / car.getSpeed() && course.getTimeLeft() >= dist / car.getSpeed() && !targetBlocked(participant, course.getToLane())) {
            car.setTimeLeft(car.getTimeLeft() - (int) (dist / car.getSpeed()));
            course.decreaseTime((int) (dist / car.getSpeed()));
            position.setCurrentRoad(course.getToLane().getParentRoad());
            position.setPosition(course.getToLane().getParentRoad().getLength());
            position.setCurrentLane(findLaneNumber(course.getToLane()));
            activeRoads.add(course.getToLane().getParentRoad());
            return;
        }
        // TODO deceleration
        car.setSpeed(0);
    }

    private int findLaneNumber(Lane lane) {
        return lane.getParentRoad().getLanes().indexOf(lane);
    }

    private boolean targetBlocked(TrafficParticipant participant, Lane target) {
        List<TrafficParticipant> laneParticipants = target.getParticipants();
        if (laneParticipants.isEmpty()) {
            return false;
        }
        TrafficParticipant last = laneParticipants.get(laneParticipants.size() - 1);
        return last.getPosition().getPosition() - target.getParentRoad().getLength() > participant.getCar().getInterCarDistance() + last.getCar().getInterCarDistance();
    }

    private Course selectCourse(Lane from, Road to) {
        for (Course course : targetNode.getCourses()) {
            if (course.getFromLane() == from && course.getToLane().getParentRoad() == to) {
                return course;
            }
        }
        throw new IllegalStateException("Car has no route");
    }

    private void processDestination(TrafficParticipant car) {
        //TODO process point of interest
    }

    @Override
    public void run() {
        List<TrafficParticipant> queue = new ArrayList<>();
        for (TrafficParticipant participant : targetNode.getPendingCars()) {
            if (participant.getPosition().getCurrentRoad().isMainRoad()) {
                targetNode.removePendingCar(participant);
                queue.add(participant);
            }
        }
        // TODO add priorities
        queue.addAll(targetNode.getPendingCars());
        targetNode.clearPendingCars();
        for (TrafficParticipant car : queue) {
            processCar(car);
        }
        latch.countDown();
    }
}
