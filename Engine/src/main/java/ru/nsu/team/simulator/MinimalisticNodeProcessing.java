package ru.nsu.team.simulator;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class MinimalisticNodeProcessing implements Runnable, Serializable {

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
        LOG.debug("Processing car " + participant);
        Car car = participant.getCar();
        PositionOnRoad position = participant.getPosition();
        Road road = participant.getPosition().getCurrentRoad();
        TrafficLight trafficLight = null;
        Path path = participant.getCar().getPath();
        if (path.getRoads().isEmpty()) {
            reporterBuilder.markExit(participant, time + timeInterval - car.getTimeLeft());
            processDestination(participant);
            road.deleteTrafficParticipant(participant);
            return;
        }
        if (road.getTo().getTrafficLightsNumber() > 0) {
            trafficLight = road.getTo().getTrafficLights().get(0);
        }
        Road nextRoad = path.getNextRoad();
        Course course = selectCourse(position.getCurrentRoad().getLaneN(position.getCurrentLaneId()), nextRoad);
        int timeLeft = car.getTimeLeft();
        double dist = course.getLength();
        car.setSpeed(car.getSpeed() * 0.7);
        playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
        if (trafficLight == null || trafficLight.timeBlocked(road, time + timeInterval - car.getTimeLeft()) == 0) {
            if(tryPassIntersection(participant)){
                return;
            }
        } else {
            LOG.debug("Car " + participant + " is blocked by traffic light for " + trafficLight.timeBlocked(road,
                    time + timeInterval - car.getTimeLeft()));
            car.setTimeLeft(Math.max(car.getTimeLeft() - trafficLight.timeBlocked(road,
                    time + timeInterval - car.getTimeLeft()), 0));
            car.setSpeed(0);
            playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
            if(trafficLight.timeBlocked(road, time + timeInterval - car.getTimeLeft()) == 0){
                if(tryPassIntersection(participant)){
                    return;
                }
            }
        }
        // TODO deceleration
        car.setSpeed(0);
        playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
        LOG.debug("Finished processing car " + participant);
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
        LOG.debug("["+(time + timeInterval - car.getCar().getTimeLeft())+"] Car has reached destination "+car);
        //TODO process point of interest
    }

    @Override
    public void run() {
        LOG.debug("Processing node " + targetNode.getId() + " with " + targetNode.getPendingCars().size() + " cars");
        try {
            List<TrafficParticipant> queue = new ArrayList<>(targetNode.getPendingCars());
            queue.sort((tp1, tp2) -> getCarPriority(tp2) - getCarPriority(tp1));
            targetNode.clearPendingCars();
            for (TrafficParticipant car : queue) {
                processCar(car);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            LOG.debug("Finished processing node " + targetNode.getId());
            latch.countDown();
        }
    }

    private boolean tryPassIntersection(TrafficParticipant participant) {
        Car car = participant.getCar();
        var position = participant.getPosition();
        Road nextRoad = car.getPath().getNextRoad();
        Course course = selectCourse(position.getCurrentRoad().getLaneN(position.getCurrentLaneId()), nextRoad);
        int timeLeft = car.getTimeLeft();
        double dist = course.getLength();
        if (timeLeft >= dist / (car.getSpeed() + 8) && course.getTimeLeft() >= dist / (car.getSpeed() + 5) && !targetBlocked(participant, course.getToLane())) {
            reporterBuilder.markExit(participant, time + timeInterval - car.getTimeLeft());
            car.setTimeLeft(car.getTimeLeft() - (int) (dist / (car.getSpeed() + 5) + 1));
            position.getCurrentRoad().deleteTrafficParticipant(participant);
            course.decreaseTime((int) (dist / (car.getSpeed() + 5)) + 1);
            position.setCurrentRoad(course.getToLane().getParentRoad());
            position.setPosition(course.getToLane().getParentRoad().getLength());
            position.setCurrentLane(findLaneNumber(course.getToLane()));
            activeRoads.add(course.getToLane().getParentRoad());
            position.getCurrentRoad().addTrafficParticipant(participant);
            LOG.debug("Car has passed intersection " + car);
            car.getPath().popRoad();
            playbackBuilder.addCarState(participant, time + timeInterval - car.getTimeLeft(), true);
            reporterBuilder.markEnter(participant, time + timeInterval - car.getTimeLeft());
            return true;
        }
        return false;
    }

    private int getCarPriority(TrafficParticipant tp) {
        return tp.getPosition().getCurrentRoad().isMainRoad() ? 1 : 0;
    }
}
