package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class RoadMapConfiguration {
    private List<NodeConfiguration> nodes;
    private List<RoadConfiguration> roads;
    private List<PlaceOfInterestConfiguration> pointsOfInterest;
    private List<TrafficParticipantConfiguration> trafficParticipants;
    private String start;


    public int getNodeNumber() {
        return nodes.size();

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
