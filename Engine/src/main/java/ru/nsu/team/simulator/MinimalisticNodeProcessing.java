package ru.nsu.team.simulator;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
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

    private static Logger LOG = Logger.getRootLogger();

    private final Set<Road> activeRoads;
    private final Node targetNode;
    private final CountDownLatch latch;
    private final PlaybackBuilder playbackBuilder;
    private final HeatmapBuilder reporterBuilder;
    private final int time;
    private final int timeInterval;

    public MinimalisticNodeProcessing(int time, int timeInterval, Node targetNode, Set<Road> activeRoads,
                                      CountDownLatch cyclicBarrier, PlaybackBuilder playbackBuilder,
                                      HeatmapBuilder reporterBuilder) {
        this.activeRoads = activeRoads;
        this.targetNode = targetNode;
        this.latch = cyclicBarrier;
        this.playbackBuilder = playbackBuilder;
        this.reporterBuilder = reporterBuilder;
        this.time = time;
        this.timeInterval = timeInterval;
    }

    private void processCar(TrafficParticipant participant) {
        //TODO add traffic lights
        //System.out.println("Node " + targetNode.getId());
        //System.out.println(participant);
        Car car = participant.getCar();
        PositionOnRoad position = participant.getPosition();
        Road road = participant.getPosition().getCurrentRoad();
        TrafficLight trafficLight = null;
        Path path = participant.getCar().getPath();
        if (path.getRoads().isEmpty()) {
            processDestination(participant);
            reporterBuilder.markExit(participant, time + timeInterval - car.getTimeLeft());
            road.deleteTrafficParticipant(participant);
            return;
        }
        if (road.getTo().getTrafficLightsNumber() > 0) {
            trafficLight = road.getTo().getTrafficLights().get(0);
        }
        Road nextRoad = path.getNextRoad();
        Course course = selectCourse(position.getCurrentRoad().getLaneN(position.getCurrentLane()), nextRoad);
        int timeLeft = car.getTimeLeft();
        double dist = course.getLength();
        car.setSpeed(car.getSpeed() * 0.7);
        playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
        //System.out.println("" + (timeLeft >= dist / (car.getSpeed() + 1)) + " " + (course.getTimeLeft() >= dist /
        // (car.getSpeed() + 1)) + " " + (!targetBlocked(participant, course.getToLane())));
        if (trafficLight == null || trafficLight.timeBlocked(road, time) == 0) {
            if (timeLeft >= dist / (car.getSpeed() + 5) && course.getTimeLeft() >= dist / (car.getSpeed() + 5) && !targetBlocked(participant, course.getToLane())) {
                reporterBuilder.markExit(participant, time + timeInterval - car.getTimeLeft());
                car.setTimeLeft(car.getTimeLeft() - (int) (dist / (car.getSpeed() + 5) + 1));
                position.getCurrentRoad().deleteTrafficParticipant(participant);
                course.decreaseTime((int) (dist / (car.getSpeed() + 5)) + 1);
                position.setCurrentRoad(course.getToLane().getParentRoad());
                position.setPosition(course.getToLane().getParentRoad().getLength());
                position.setCurrentLane(findLaneNumber(course.getToLane()));
                activeRoads.add(course.getToLane().getParentRoad());
                position.getCurrentRoad().addTrafficParticipant(participant);
                //System.out.println("Moved " + car + " to road:" + course.getToLane().getParentRoad());
                car.getPath().popRoad();
                playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
                reporterBuilder.markEnter(participant, time + timeInterval - car.getTimeLeft());
                return;
            }
        }
        // TODO deceleration
        car.setSpeed(0);
        playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);

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
        playbackBuilder.addCarState(car, time + timeInterval - car.getCar().getTimeLeft(), false);
        //System.out.println("Car \"" + car.getCar() + "\" has reached destination " + targetNode + "!)");
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
