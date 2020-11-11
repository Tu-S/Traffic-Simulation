package ru.nsu.team.entity.roadmap.configuration;

import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.List;

public class RoadMapConfiguration {
    private List<NodeConfiguration> nodes;
    private List<RoadConfiguration> roads;
    private List<PlaceOfInterestConfiguration> pointsOfInterest;
    private Integer spawnPeriodMin;
    private Integer spawnPeriodMax;
    private List<TrafficParticipantConfiguration> trafficParticipants;
    private List<Integer> activeRoads;
    private List<Integer> calculatedRoads;

    public void setTrafficParticipants(List<TrafficParticipantConfiguration> trafficParticipants) {
        this.trafficParticipants = trafficParticipants;
    }

    public void setActiveRoads(List<Integer> activeRoads) {
        this.activeRoads = activeRoads;
    }

    public void setCalculatedRoads(List<Integer> calculatedRoads) {
        this.calculatedRoads = calculatedRoads;
    }

    public List<PlaceOfInterestConfiguration> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public List<TrafficParticipantConfiguration> getTrafficParticipants() {
        return trafficParticipants;
    }

    public Integer getSpawnPeriodMin() {
        return spawnPeriodMin;
    }

    public Integer getSpawnPeriodMax() {
        return spawnPeriodMax;
    }

    public List<NodeConfiguration> getNodes() {
        return nodes;
    }

    public List<RoadConfiguration> getRoads() {
        return roads;
    }

    public List<Integer> getActiveRoads() {
        return activeRoads;
    }

    public List<Integer> getCalculatedRoads() {
        return calculatedRoads;
    }
}
