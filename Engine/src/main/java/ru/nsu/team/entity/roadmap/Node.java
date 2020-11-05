package ru.nsu.team.entity.roadmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {

    private ArrayList<Course> courses;
    private ArrayList<TrafficLight> trafficLights;

    public Node() {
        this.courses = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
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

    public TrafficLight getTrafficLightN(int n) {
        return trafficLights.get(n);
    }
}
