package ru.nsu.team.entity.roadmap;


import ru.nsu.team.entity.spawner.Spawner;

import java.util.ArrayList;

public class RoadMap {

    private ArrayList<Road> activeRoads;
    private ArrayList<Road> calculatedRoads;
    private ArrayList<Spawner> spawners;
    private ArrayList<PlaceOfInterest> placesOfInterest;
    private ArrayList<Road> roads;

    public RoadMap() {
        this.roads = new ArrayList<>();
        this.activeRoads = new ArrayList<>();
        this.calculatedRoads = new ArrayList<>();
        this.spawners = new ArrayList<>();
        this.placesOfInterest = new ArrayList<>();
    }

    public int getRoadsNumber() {
        return roads.size();
    }

    /*public ArrayList<Road> getRoads() {
        return roads;
    }*/
    public Road getRoadN(int n) {
        return roads.get(n);
    }

    public ArrayList<Spawner> getSpawners() {
        return spawners;
    }

    public int getActiveRoadsNumber() {
        return activeRoads.size();
    }

    public int getCalculatedRoadsNumber() {
        return calculatedRoads.size();
    }

    public int getSpawnersNumber() {
        return spawners.size();
    }

    public int getPlacesOfInterestNumber() {

        return placesOfInterest.size();
    }


    public Road getActiveRoadN(int n) {

        return activeRoads.get(n);
    }

    public Road getCalculatedRoadN(int n) {

        return calculatedRoads.get(n);
    }

    public Spawner getSpawnerN(int n) {
        return spawners.get(n);
    }

    public PlaceOfInterest getPlaceOfInterestN(int n) {
        return placesOfInterest.get(n);
    }

    public void addActiveRoad(Road activeRoad) {
        this.activeRoads.add(activeRoad);
    }

    public void addCalculatedRoad(Road calculatedRoad) {

        this.calculatedRoads.add(calculatedRoad);
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    public void addSpawner(Spawner spawner) {
        this.spawners.add(spawner);
    }

    public void addPlaceOfInterest(PlaceOfInterest placeOfInterest) {
        this.placesOfInterest.add(placeOfInterest);
    }


    public void serialize() {
    }

    public void deserialize() {
    }


}
