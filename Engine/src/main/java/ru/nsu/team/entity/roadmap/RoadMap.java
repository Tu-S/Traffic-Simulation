package ru.nsu.team.entity.roadmap;


import ru.nsu.team.entity.spawner.Spawner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoadMap implements Serializable {

    private ArrayList<Spawner> spawners;
    private ArrayList<PlaceOfInterest> placesOfInterest;
    private ArrayList<Road> roads;
    private Set<Course> courseSet;
    private long start;
    private long currentTime;
    private long endTime;

    public RoadMap() {
        this.roads = new ArrayList<>();
        this.spawners = new ArrayList<>();
        this.placesOfInterest = new ArrayList<>();
        this.courseSet = new HashSet<>();
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public ArrayList<PlaceOfInterest> getPlacesOfInterest() {
        return placesOfInterest;
    }

    public void increaseCurrentTime(long delta) {
        this.currentTime += delta;
    }

    public int getRoadsNumber() {
        return roads.size();
    }

    public List<Road> getRoads() {
        return roads;
    }

    public Road getRoadN(int n) {
        return roads.get(n);
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }

    public int getSpawnersNumber() {
        return spawners.size();
    }

    public int getPlacesOfInterestNumber() {

        return placesOfInterest.size();
    }


    public Spawner getSpawnerN(int n) {
        return spawners.get(n);
    }

    /*public PlaceOfInterest getPlaceOfInterestN(int n) {
        return placesOfInterest.get(n);
    }*/


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

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void addCourse(Course course) {
        courseSet.add(course);
    }

    public Set<Course> getCourseSet() {
        return courseSet;
    }
}
