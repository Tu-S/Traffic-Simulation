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

    static Logger log = Logger.getRootLogger();

    @BeforeClass
    public static void initLogger() {
        Logger.getRootLogger().addAppender(
                new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
    }

    private RoadMap createSampleLineRoadMap(int spawnFrequency) {
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
        spawner.addConfiguration(new Configuration(0, 150, spawnFrequency));
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
        RoadMap rm = createSampleLineRoadMap(15);
        HeatmapBuilder hmb = new HeatmapBuilder(rm, 100);
        Simulator sim = new Simulator(100, rm, new PlaybackBuilder(), hmb);
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
        log.debug("Score is " + hmb.getScore());
    }
}
