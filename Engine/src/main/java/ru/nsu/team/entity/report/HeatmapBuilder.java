package ru.nsu.team.entity.report;

import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatmapBuilder {
    static class Statistic {
        private double avgTime;
        private int count;
    }

    int frameStart, timeInterval, endTime;
    Map<Road, Map<TrafficParticipant, Integer>> enterTimes;
    Map<Road, Statistic> statisticMap;

    List<HeatmapFrame> frames;

    // Time interval must be divisible by simulation interval
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

    private void stashFrame() {
        HeatmapFrame frame = new HeatmapFrame(frameStart, Math.min(frameStart + timeInterval,endTime));
        for (Map.Entry<Road, Statistic> entry : statisticMap.entrySet()) {
            double length = entry.getKey().getLength();
            double avgTime = entry.getValue().avgTime;
            double avgSpeed = length / avgTime;
            int score;

            score = entry.getValue().count == 0 ? 0 : (int) (100 * avgSpeed / Car.DEFAULT_MAX_SPEED);
            frame.addHeatmapRoadState(entry.getKey().getId(), score);
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
        if (!enterTimes.containsKey(participant.getPosition().getCurrentRoad())) {
            return;
        }
        int enterTime = enterTimes.get(participant.getPosition().getCurrentRoad()).get(participant);
        int spentTime = time - enterTime;
        Statistic stat = statisticMap.get(participant.getPosition().getCurrentRoad());
        stat.avgTime = (stat.avgTime * stat.count + spentTime) / (stat.count + 1);
        stat.count++;
    }

    public List<HeatmapFrame> build() {
        while (frameStart < endTime) {
            stashFrame();
        }
        return frames;
    }
}
