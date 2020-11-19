package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.util.ArrayList;

public class Lane {
    private ArrayList<TrafficParticipant> trafficParticipants;
    private ArrayList<Sign> signs;

    public Lane() {
        this.signs = new ArrayList<>();
        this.trafficParticipants = new ArrayList<>();
    }

    public int getTrafficParticipantsNumber() {
        return trafficParticipants.size();
    }

    public TrafficParticipant getCarN(int n) {
        return trafficParticipants.get(n);
    }

    public void addSign(Sign sign) {
        signs.add(sign);
    }

    public void addTrafficParticipant(TrafficParticipant car){
        trafficParticipants.add(car);
    }

}
