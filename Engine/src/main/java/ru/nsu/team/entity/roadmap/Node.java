package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.Position;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class Node {

    private int id;
    private ArrayList<Course> courses;
    private ArrayList<TrafficLight> trafficLights;
    private Position position;
    private Set<TrafficParticipant> pendingCars;
    //private List<Road> roads;
    private Map<Road,List<Road>> fromRoadToRoads = new HashMap<Road,List<Road>>();

    public Node(int id, Position position) {
        this.courses = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
        this.id = id;
        this.position = position;
        this.pendingCars = new HashSet<>();
        //roads = new ArrayList<>();
    }


    public void addFromTo(Road from,List<Road> to){
        fromRoadToRoads.put(from,to);
    }

    public Node(int id) {
        this.courses = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
        this.id = id;
        this.pendingCars = new HashSet<>();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addTrafficLight(TrafficLight trafficLight) {
        trafficLights.add(trafficLight);
    }

    public int getCoursesNumber() {
        return courses.size();
    }

    public int getTrafficLightsNumber() {
        return trafficLights.size();
    }

    /*public Course getCourseN(int n) {
        return courses.get(n);
    }*/

    public List<Course> getCourses() {
        return courses;
    }

    /*public TrafficLight getTrafficLightN(int n) {
        return trafficLights.get(n);
    }*/
    /*public void addRoad(Road r){
        this.roads.add(r);
    }*/

    synchronized public void addPendingCar(TrafficParticipant car) {
        pendingCars.add(car);
    }

    synchronized public void clearPendingCars() {
        pendingCars.clear();
    }

    synchronized public Set<TrafficParticipant> getPendingCars() {
        return new HashSet<>(pendingCars);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id).hashCode();
    }
}
