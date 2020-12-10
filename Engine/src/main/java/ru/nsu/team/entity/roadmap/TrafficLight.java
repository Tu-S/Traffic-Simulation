package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;

public class TrafficLight {
    //private int greenDuration;
    //private int redDuration;
    //private Color currentColor;
    //private int currentState;
    private ArrayList<Road> roads;
    private double delay;

    public TrafficLight(double delay) {
        //this.greenDuration = greenDuration;
        //this.redDuration = redDuration;
        this.delay = delay;
        this.roads = new ArrayList<>(2);

    }

    /*public int getGreenDuration() {
        return greenDuration;
    }*/

    /*public int getRedDuration() {
        return redDuration;
    }*/

    /*public int getCurrentState() {
        return currentState;
    }*/

   /* public Color getCurrentColor() {
        return currentColor;
    }*/

    /*public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }*/

    /*public int nextGreen(int time) {
        time = 0;
        return time;
    }*/

    public void addRoad(Road road) {
        roads.add(road);
    }

    enum Color {GREEN, RED, YELLOW}
}
