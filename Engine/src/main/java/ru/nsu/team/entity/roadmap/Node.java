package ru.nsu.team.entity.roadmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private ArrayList<Course> courses;
    private ArrayList<TrafficLight> trafficLights;

    public Node(int id) {
        this.courses = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
        this.id = id;
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

    public Course getCourseN(int n) {
        return courses.get(n);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public TrafficLight getTrafficLightN(int n) {
        return trafficLights.get(n);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id).hashCode();
    }
}
