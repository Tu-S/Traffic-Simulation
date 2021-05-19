package ru.nsu.team.entity.report;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatmapBuilder {
    private static Logger log = Logger.getRootLogger();

    static class Statistic {
        private double avgTime;
        private int count;
    }

    int frameStart, timeInterval, endTime;
    Map<Road, Map<TrafficParticipant, Integer>> enterTimes;
    Map<Road, Statistic> statisticMap;
    int totalExits = 0;

    List<HeatmapFrame> frames;

    /**
     * Time interval must be divisible by simulation interval
     */
    public HeatmapBuilder(RoadMap map, int timeInterval) {
        this.enterTimes = new HashMap<>();
        this.statisticMap = new HashMap<>();
        this.frameStart = (int) map.getCurrentTime();
        this.timeInterval = timeInterval;
        this.frames = new ArrayList<>();
        map.getRoads().stream().filter(r -> r.getFrom() != null).forEach(r -> {
            enterTimes.put(r, new HashMap<>());
            statisticMap.put(r, new Statistic());
        });
        this.endTime = (int) map.getEndTime();
    }

    synchronized private void stashFrame() {
        HeatmapFrame frame = new HeatmapFrame(frameStart, Math.min(frameStart + timeInterval, endTime));
        for (Map.Entry<Road, Statistic> entry : statisticMap.entrySet()) {
            double length = entry.getKey().getLength();
            double avgTime = entry.getValue().avgTime;
            double avgSpeed = length / avgTime;
            int score;

            score = entry.getValue().count == 0 ? 0 : (int) (100 * avgSpeed / Car.DEFAULT_MAX_SPEED);
            frame.addHeatmapRoadState(entry.getKey().getId(), score,
                    avgSpeed / entry.getKey().getLaneN(0).getMaxSpeed());
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
        enterTimes.get(participant.getPosition().getCurrentRoad()).put(participant, time);
    }

    synchronized public void markExit(TrafficParticipant participant, int time) {
        if (time > frameStart + timeInterval) {
            stashFrame();
        }
        Road currentRoad = participant.getPosition().getCurrentRoad();
        if (currentRoad.isEphemeral()) {
            return;
        }

        if (!enterTimes.containsKey(participant.getPosition().getCurrentRoad())) {
            throw new RuntimeException("No enter record found for car " + participant);
        }
        int enterTime = enterTimes.get(participant.getPosition().getCurrentRoad()).get(participant);
        int spentTime = time - enterTime;
        Statistic stat = statisticMap.get(participant.getPosition().getCurrentRoad());
        stat.avgTime = (stat.avgTime * stat.count + spentTime) / (stat.count + 1);
        stat.count++;
        totalExits++;
    }

    public List<HeatmapFrame> build() {
        while (frameStart < endTime) {
            stashFrame();
        }
        return frames;
    }

    public double getScore() {
        int roadCount = statisticMap.size();
        double[] scores = frames.stream()
                .flatMap(frame -> frame.congestionList.stream())
                .mapToDouble(stat -> stat.speedRatio).filter(Double::isFinite).toArray();
        double score = scores.length == 0 ? 0 : Arrays.stream(scores).sum() / scores.length;
        return score;
    }
}
