package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimGenTest {


    @Test
    public void test(){

        List<RoadMap> generation = new ArrayList<>(5);
        for (int i = 0; i < 1; i++) {
            RoadMap copy = createSampleLineRoadMap(15);
            generation.add(copy);
        }
        simulationBlock(generation);
    }
    private static void simulationBlock(List<RoadMap> maps) {
        for (RoadMap m : maps) {
            HeatmapBuilder hmb = new HeatmapBuilder(m, 100);
            Simulator sim = new Simulator(100, m, new PlaybackBuilder(), hmb);
            sim.start();
            try {
                sim.join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            m.setScore(hmb.getScore());
            System.out.println(m.getScore());
        }
    }

    private static RoadMap createSampleLineRoadMap(int spawnFrequency) {
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
}
