package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class RoadMapConfiguration {
    private List<NodeConfiguration> nodes;
    private List<RoadConfiguration> roads;
    private List<PlaceOfInterestConfiguration> pointsOfInterest;
    private List<TrafficParticipantConfiguration> trafficParticipants;
    private String start;
    private String currentTime = "00:00";
    private String end = "00:00";
    private double width = 1000.0;
    private double height = 1000.0;

    public int getNodeNumber() {
        return nodes.size();

    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getRoadNumber() {
        return roads.size();
    }

    public void setTrafficParticipants(List<TrafficParticipantConfiguration> trafficParticipants) {
        this.trafficParticipants = trafficParticipants;
    }

    public List<PlaceOfInterestConfiguration> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public List<TrafficParticipantConfiguration> getTrafficParticipants() {
        return trafficParticipants;
    }

    public List<NodeConfiguration> getNodes() {
        return nodes;
    }

    public List<RoadConfiguration> getRoads() {
        return roads;
    }

    public String getStart() {
        return start;
    }


}
