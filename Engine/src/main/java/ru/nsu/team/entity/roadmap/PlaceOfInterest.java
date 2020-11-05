package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Position;

import java.util.ArrayList;

public class PlaceOfInterest {
    private int parkingCapacity;
    private ArrayList<Car> cars;
    private Node position;

    public PlaceOfInterest(Node position, int parkingCapacity) {
        this.position = position;
        this.parkingCapacity = parkingCapacity;
        this.cars = new ArrayList<>();
    }


    public void addCar(Car car) {
    }

    public void deleteCar(Car car) {
    }

    public Node getPosition() {
        return position;
    }

    public int getCarsNumber() {
        return cars.size();
    }

    public Car getCarN(int n) {
        return cars.get(n);
    }
}
