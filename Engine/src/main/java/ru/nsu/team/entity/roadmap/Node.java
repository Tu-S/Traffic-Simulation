package ru.nsu.team.entity.roadmap;

import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.genome.GenomeUtils;
import ru.nsu.team.genome.NodeGenome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class Node implements Serializable {

    private int id;
    private ArrayList<Course> courses;
    private Position position;
    private NodeGenome genome;
    private Set<TrafficParticipant> pendingCars;
    private Map<Road, List<Road>> fromRoadToRoads = new HashMap<>();


    public Node(int id, Position position) {
        this.courses = new ArrayList<>();
        this.id = id;
        this.position = position;
        this.pendingCars = new HashSet<>();
        this.genome = new NodeGenome(this.id);
    }

    public NodeGenome getGenome() {
        return genome;
    }

    public void addFromTo(Road from, List<Road> to) {
        fromRoadToRoads.put(from, to);
    }

    public Node(int id) {
        this.courses = new ArrayList<>();
        //this.trafficLights = new ArrayList<>();
        this.id = id;
        this.pendingCars = new HashSet<>();
        this.genome = new NodeGenome(this.id);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getId() {
        return genome.getId();
    }

    public void setId(int id) {
        this.genome.setId(id);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addTrafficLight(TrafficLight trafficLight) {
        genome.getTrafficLights().add(trafficLight);
    }

    public int getCoursesNumber() {
        return courses.size();
    }

    public int getTrafficLightsNumber() {

        return this.genome.getTrafficLights().size();
    }

    public List<TrafficLight> getTrafficLights() {
        return genome.getTrafficLights();
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

    synchronized public void removePendingCar(TrafficParticipant car) {
        pendingCars.remove(car);
    }

    synchronized public Set<TrafficParticipant> getPendingCars() {
        return new HashSet<>(pendingCars);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id).hashCode();
    }

    @Override
    public String toString() {
        return "Node " + id;
    }


}
