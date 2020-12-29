package ru.nsu.team.entity.roadmap.configuration;

import java.util.List;

public class PlaceOfInterestConfiguration {
    private Integer capacity;
    private List<Integer> nodes;
    private double x;
    private double y;
    private double width;
    private double height;
    private double weight;

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Integer> getNodes() {
        return nodes;
    }
}
