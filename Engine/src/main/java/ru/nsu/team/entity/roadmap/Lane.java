package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;

import java.util.ArrayList;

public class Lane {
    private ArrayList<Car> cars;
    private ArrayList<Sign> signs;

    public Lane() {
        this.signs = new ArrayList<>();
        this.cars = new ArrayList<>();
    }

    public int getCarsNumber() {
        return cars.size();
    }

    public Car getCarN(int n) {
        return cars.get(n);
    }

    public void addSign(Sign sign){
        signs.add(sign);
    }


}
