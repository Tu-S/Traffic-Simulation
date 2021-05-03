package ru.nsu.team.simulator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class SimulatorTest {

    @BeforeClass
    public static void initLogger() {
        Logger.getRootLogger().addAppender(
                new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    }

    private RoadMap createSampleLineRoadMap() {
        List<Node> nodes = Arrays.asList(new Node(0), new Node(1));
        RoadMap rm = new RoadMap();
        rm.setStart(0);
        rm.setEndTime(1000);
        Road road = new Road(0, nodes.get(0), nodes.get(1), 1, 25);
        road.setLength(1000);
        rm.addRoad(road);

        PlaceOfInterest poi = new PlaceOfInterest(0, 100500, 100);
        poi.addNode(nodes.get(1));

        Road queue = new Road(1, null, nodes.get(0), 1, 100);
        rm.addRoad(queue);
        Course course = new Course(queue.getLaneN(0), road.getLaneN(0),
                Collections.singletonList(new Intersection(0)), 10);
        rm.addCourse(course);
        nodes.get(0).addCourse(course);
        Spawner spawner = new Spawner(nodes.get(0), queue);
        spawner.addPossibleDestination(poi);
        spawner.addConfiguration(new Configuration(0, 150, 10));
        rm.addSpawner(spawner);

        return rm;
    }

    private RoadMap createSampleLineRoadMapTL() {
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Node node3 = new Node(2);
        Node node4 = new Node(3);
        List<Node> nodes = Arrays.asList(node1, node2, node3, node4);
        RoadMap rm = new RoadMap();
        rm.setStart(0);
        rm.setEndTime(1000);
        Road road2 = new Road(3, nodes.get(2), nodes.get(3), 1, 25);
        road2.setLength(3000);
        rm.addRoad(road2);
        Road road1 = new Road(2, nodes.get(1), nodes.get(2), 1, 25);
        road1.setLength(5000);
        rm.addRoad(road1);
        Road road = new Road(1, nodes.get(0), nodes.get(1), 1, 25);
        road.setLength(2000);
        rm.addRoad(road);

        PlaceOfInterest poi = new PlaceOfInterest(0, 100500, 100);
        poi.addNode(node4);

        Road queue = new Road(0, null, nodes.get(0), 1, 100);
        rm.addRoad(queue);

        TrafficLightConfig config = new TrafficLightConfig(100);
        TrafficLightConfig config1 = new TrafficLightConfig(100);
        TrafficLight trafficLight = new TrafficLight();
        config.addRoad(road);
        config1.addRoad(road1);
        trafficLight.addConfig(config);
        trafficLight.addConfig(config1);
        node3.addTrafficLight(trafficLight);

        Course course = new Course(queue.getLaneN(0), road.getLaneN(0), Collections.singletonList(new Intersection(0)), 100);
        rm.addCourse(course);
        nodes.get(0).addCourse(course);
        Course course1 = new Course(road.getLaneN(0), road1.getLaneN(0), Collections.singletonList(new Intersection(0)), 100);
        rm.addCourse(course1);
        nodes.get(1).addCourse(course1);
        Course course2 = new Course(road1.getLaneN(0), road2.getLaneN(0), Collections.singletonList(new Intersection(0)), 100);
        rm.addCourse(course2);
        nodes.get(2).addCourse(course2);
        Spawner spawner = new Spawner(nodes.get(0), queue);
        spawner.addPossibleDestination(poi);
        spawner.addConfiguration(new Configuration(0, 150, 10));
        rm.addSpawner(spawner);

        return rm;
    }

    private RoadMap createCrossRoadMap() {
        List<Node> nodes = Arrays.asList(new Node(0), new Node(1));
        RoadMap rm = new RoadMap();
        rm.setStart(0);
        rm.setEndTime(1000);
        Road road = new Road(0, nodes.get(0), nodes.get(1), 1, 25);
        road.setLength(1000);
        rm.addRoad(road);

        PlaceOfInterest poi = new PlaceOfInterest(0, 100500, 100);
        poi.addNode(nodes.get(1));

        Road queue = new Road(1, null, nodes.get(0), 1, 100);
        rm.addRoad(queue);
        Course course = new Course(queue.getLaneN(0), road.getLaneN(0),
                Collections.singletonList(new Intersection(0)), 10);
        rm.addCourse(course);
        nodes.get(0).addCourse(course);
        Spawner spawner = new Spawner(nodes.get(0), queue);
        spawner.addPossibleDestination(poi);
        spawner.addConfiguration(new Configuration(0, 150, 20));
        rm.addSpawner(spawner);

        return rm;
    }

    private void failTest() {
        fail();
    }

    @Test
    public void testSimulator() {
        RoadMap rm = createSampleLineRoadMap();
        Simulator sim = new Simulator(30, rm, new PlaybackBuilder(), new HeatmapBuilder(rm, 100));
        sim.setUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();
            failTest();
        });
        sim.start();
        try {
            sim.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        if (sim.isAlive()) {
            sim.interrupt();
            fail();
        }
    }

    @Test
    public void testTrafficLights() {
        RoadMap rm = createSampleLineRoadMapTL();
        Simulator sim = new Simulator(30, rm, new PlaybackBuilder(), new HeatmapBuilder(rm, 100));
        sim.setUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();
            failTest();
        });
        sim.start();
        try {
            sim.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        if (sim.isAlive()) {
            sim.interrupt();
            fail();
        }
    }
}
