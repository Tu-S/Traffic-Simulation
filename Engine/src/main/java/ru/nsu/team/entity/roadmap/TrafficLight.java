package ru.nsu.team.entity.roadmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrafficLight implements Serializable {
    //private int greenDuration;
    //private int redDuration;
    //private Color currentColor;
    //private int currentState;
    private List<TrafficLightConfig> configs;
    private int period;

    public TrafficLight() {
        this.configs = new ArrayList<>();
    }

    public void setConfigs(List<TrafficLightConfig> configs) {
        this.configs = configs;
    }

    public void addConfig(TrafficLightConfig c) {
        configs.add(c);
        period += c.getDelay();
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<TrafficLightConfig> getConfigs() {
        return configs;
    }

    public TrafficLightConfig getCurrentConfig(int time) {
        time %= period;
        int start = 0;
        for (TrafficLightConfig tlc : configs) {
            if (start <= time && start + tlc.getDelay() >= time) {
                return tlc;
            }
            start += tlc.getDelay();
        }
        throw new IllegalStateException("Broken traffic light configuration");
    }


    public int timeBlocked(Road road, int time) {
        time %= period;
        int start = 0;
        int index = 0;
        for (index = 0; index < configs.size(); index++) {
            if (start <= time && start + configs.get(index).getDelay() >= time) {
                break;
            }
            start += configs.get(index).getDelay();
        }

        if(configs.size()==index){
            System.out.println("Error");
        }
        if (configs.get(index).getRoads().contains(road)) {
            return 0;
        }
        int waitTime = start + configs.get(index).getDelay() - time;
        start += configs.get(index).getDelay();

        int i;
        for (i = (index + 1) % configs.size(); i != index; i = (i + 1) % configs.size()) {
            if (configs.get(i).getRoads().contains(road)) {
                return waitTime;
            }
            waitTime += configs.get(i).getDelay();
        }
        throw new IllegalArgumentException("Road is not controlled by traffic light");
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
