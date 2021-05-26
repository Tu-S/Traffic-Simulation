package ru.nsu.team.entity.report;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeatmapBuilder {
    private static Logger log = Logger.getRootLogger();

    static class Statistic {
        private double avgTime;
        private double count;
    }

    int frameStart, timeInterval, endTime;
    Map<TrafficParticipant, EnteringState> enterStates;
    Map<Lane, Statistic> statisticMap;
    RoadMap map;
    List<Road> observedRoads;
    int totalExits = 0;

    List<HeatmapFrame> frames;

    /**
     * Time interval must be divisible by simulation interval
     */
    public HeatmapBuilder(RoadMap map, int timeInterval) {
        this.enterStates = new HashMap<>();
        this.statisticMap = new HashMap<>();
        this.frameStart = (int) map.getCurrentTime();
        this.timeInterval = timeInterval;
        this.observedRoads = map.getRoads().stream().filter(r -> r.getFrom() != null).collect(Collectors.toList());
        this.frames = new ArrayList<>();
        observedRoads.stream().flatMap(r -> r.getLanes().stream()).forEach(l -> {
            statisticMap.put(l, new Statistic());
        });
        this.endTime = (int) map.getEndTime();
        this.map = map;
    }

    synchronized private void stashFrame() {
        HeatmapFrame frame = new HeatmapFrame(frameStart, Math.min(frameStart + timeInterval, endTime));
        for (Road road : observedRoads) {
            List<Double> speedRatios =
                    road.getLanes().stream().filter(lane -> statisticMap.get(lane).count > 0).map(l -> {
                        var stat = statisticMap.get(l);
                        double length = l.getParentRoad().getLength();
                        double avgTime = stat.avgTime;
                        double avgSpeed = length / avgTime;
                        return avgSpeed / l.getMaxSpeed();
                    }).collect(Collectors.toList());

            double score = speedRatios.stream().mapToDouble(ratio -> ratio * 100).average().orElse(100);
            frame.addHeatmapRoadState(road.getId(), (int) score, speedRatios);
        }
        frames.add(frame);
        statisticMap.forEach((k, v) -> {
            v.avgTime = 0;
            v.count = 0;
        });
        frameStart += timeInterval;
    }

    synchronized public void markEnter(TrafficParticipant participant, int time) {
        if (time > frameStart + timeInterval) {
            stashFrame();
        }
        enterStates.put(participant, new EnteringState(participant.getPosition().getPosition(), time));
    }

    synchronized public void markExit(TrafficParticipant participant, int time) {
        if (time > frameStart + timeInterval) {
            stashFrame();
        }
        Road currentRoad = participant.getPosition().getCurrentRoad();
        if (currentRoad.isEphemeral()) {
            return;
        }

        if (!enterStates.containsKey(participant)) {
            throw new RuntimeException("No enter record found for car " + participant);
        }
        var enterState = enterStates.get(participant);
        int spentTime = time - enterState.getTimestamp();
        Statistic stat = statisticMap.get(participant.getPosition().getCurrentLane());
        double lengthRatio =
                (enterState.getPosition() - participant.getPosition().getPosition())
                        / participant.getPosition().getCurrentRoad().getLength();
        stat.avgTime = (stat.avgTime * stat.count + spentTime) / (stat.count + lengthRatio);
        stat.count += lengthRatio;
        totalExits++;
    }

    synchronized public List<HeatmapFrame> build() {
        while (frameStart < endTime) {
            stashFrame();
        }
        return frames;
    }

    synchronized public double getScore() {
        double[] scores = frames.stream()
                .flatMap(frame -> frame.congestionList.stream())
                .mapToDouble(stat -> stat.speedRatio.stream().mapToDouble(Double::doubleValue).average().orElse(0)).toArray();
        log.debug("Scores: " + Arrays.stream(scores).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
        return Arrays.stream(scores).average().orElse(0);
    }
}
