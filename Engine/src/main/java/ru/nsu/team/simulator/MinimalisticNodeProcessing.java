package ru.nsu.team.simulator;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.ReporterBuilder;
import ru.nsu.team.entity.roadmap.*;
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
    private final PlaybackBuilder playbackBuilder;
    private final ReporterBuilder reporterBuilder;

    public MinimalisticNodeProcessing(Node targetNode, Set<Road> activeRoads, CountDownLatch latch, PlaybackBuilder playbackBuilder, ReporterBuilder reporterBuilder) {
        this.activeRoads = activeRoads;
        this.targetNode = targetNode;
        this.latch = latch;
        this.playbackBuilder = playbackBuilder;
        this.reporterBuilder = reporterBuilder;
    }

    private void processCar(TrafficParticipant participant) {
        //TODO add traffic lights
        System.out.println("Node " + targetNode.getId());
        System.out.println(participant);
        Car car = participant.getCar();
        PositionOnRoad position = participant.getPosition();
        Road road = participant.getPosition().getCurrentRoad();
        Path path = participant.getCar().getPath();
        if (path.getRoads().isEmpty()) {
            processDestination(participant);
            road.deleteTrafficParticipant(participant);
            return;
        }
        Road nextRoad = path.getNextRoad();
        Course course = selectCourse(position.getCurrentRoad().getLaneN(position.getCurrentLane()), nextRoad);
        int timeLeft = car.getTimeLeft();
        double dist = course.getLength();
        System.out.println("" + (timeLeft >= dist / (car.getSpeed() + 1)) + " " + (course.getTimeLeft() >= dist / (car.getSpeed() + 1)) + " " + (!targetBlocked(participant, course.getToLane())));
        if (timeLeft >= dist / (car.getSpeed() + 1) && course.getTimeLeft() >= dist / (car.getSpeed() + 1) && !targetBlocked(participant, course.getToLane())) {
            car.setTimeLeft(car.getTimeLeft() - (int) (dist / (car.getSpeed() + 1) + 1));
            position.getCurrentRoad().deleteTrafficParticipant(participant);
            course.decreaseTime((int) (dist / (car.getSpeed() + 1)));
            position.setCurrentRoad(course.getToLane().getParentRoad());
            position.setPosition(course.getToLane().getParentRoad().getLength());
            position.setCurrentLane(findLaneNumber(course.getToLane()));
            activeRoads.add(course.getToLane().getParentRoad());
            position.getCurrentRoad().addTrafficParticipant(participant);
            System.out.println("Moved " + car + " to road:" + course.getToLane().getParentRoad());
            car.getPath().popRoad();
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
        System.out.println("Car \"" + car.getCar() + "\" has reached destination!)");
        //TODO process point of interest
    }

    @Override
    public void run() {
        try {
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
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
