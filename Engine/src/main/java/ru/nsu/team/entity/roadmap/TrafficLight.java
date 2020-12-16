package ru.nsu.team.entity.roadmap;

import java.util.ArrayList;
import java.util.List;

public class TrafficLight {
    //private int greenDuration;
    //private int redDuration;
    //private Color currentColor;
    //private int currentState;
    private List<TrafficLightConfig> configs;

    public TrafficLight() {
        this.configs = new ArrayList<>();
    }

    public void addConfig(TrafficLightConfig c){
        configs.add(c);
    }

    public List<TrafficLightConfig> getConfigs() {
        return configs;
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


    enum Color {GREEN, RED, YELLOW}
}
