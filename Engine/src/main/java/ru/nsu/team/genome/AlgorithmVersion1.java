package ru.nsu.team.genome;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.other.KeyValuePair;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;
import ru.nsu.team.simulator.Simulator;

import java.util.*;

public class AlgorithmVersion1 {

    private static final int MAX_POPULATION_SIZE = 10;
    private static final int MAX_GENERATION_NUMBER = 2;
    private static RoadMap stdMap;
    private static double requiredScore = 0.9d;
    private static double scoreDelta = 0.1d;
    private static double okScore = requiredScore - scoreDelta;
    private static RoadMap bestMap;

    public static void runAlgorithm() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/test_map.tsp");
        assert mapConfig != null;
        int curGeneration = 0;

        List<RoadMap> generation = CopierUtils.makeMaps(mapConfig, MAX_POPULATION_SIZE);
        stdMap = CopierUtils.copy(generation.get(0));
        assert stdMap != null;
        stdMap.setScore(0);


        mutationBlock(generation);
        simulationBlock(generation);

        //generation = GenomeUtils.selection(generation);
        showScore(generation);

        bestMap = generation.get(generation.size() - 1);
        if (bestMap.getScore() >= okScore) {
            System.out.println("bestMapScore = " + bestMap.getScore());
            return;
        }
        while (bestMap.getScore() < okScore && curGeneration < MAX_GENERATION_NUMBER) {
            System.out.println("GENERATION #" + curGeneration);
            generation = breedingBlock(generation);
            //System.out.println("Generation size after breeding " + generation.size());
            setDefaultStats(generation);
            simulationBlock(generation);
            generation = GenomeUtils.selection(generation);
            showScore(generation);
            if (generation.get(generation.size() - 1).getScore() < bestMap.getScore()) {
                mutationBlock(generation);
            } else {
                System.out.println("old best score = " + bestMap.getScore() + " new score = " + generation.get(generation.size() - 1).getScore());
                bestMap = generation.get(generation.size() - 1);
            }
            System.out.println("Generation size before breeding " + generation.size());
            curGeneration++;
        }
        System.out.println("size = " + generation.size());


    }

    private static void showScore(List<RoadMap> maps) {
        for (RoadMap map : maps) {
            System.out.println("Map score " + map.getScore());
        }
    }

    private static void setDefaultStats(List<RoadMap> maps) {
        for (RoadMap map : maps) {
            GenomeUtils.setDefaultState(map, stdMap);
        }
    }

    private static void simulationBlock(List<RoadMap> maps) {
        List<KeyValuePair<RoadMap, KeyValuePair<HeatmapBuilder, Simulator>>> mapAndHeat = new ArrayList<>(maps.size());
        for (RoadMap m : maps) {
            HeatmapBuilder hmb = new HeatmapBuilder(m, 100);
            var s = new Simulator(100, m, new PlaybackBuilder(), hmb);
            mapAndHeat.add(new KeyValuePair<>(m, new KeyValuePair<>(hmb, s)));
        }
        for (var s : mapAndHeat) {
            s.getValue().getValue().start();
        }
        for (var s : mapAndHeat) {
            try {
                s.getValue().getValue().join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            s.getKey().setScore(s.getValue().getKey().getScore());
        }


    }

    private static void mutationBlock(List<RoadMap> maps) {
        System.out.println("Start mutation");
        for (var m : maps) {
            GenomeUtils.mutateMap(m);
            GenomeUtils.setDefaultState(m, stdMap);
        }
    }

    private static List<RoadMap> breedingBlock(List<RoadMap> maps) {
        System.out.println("Start breeding");
        int max = maps.size();
        for (var m : maps) {
            GenomeUtils.clearMapFromCars(m);
        }
        List<RoadMap> children = new ArrayList<>(MAX_POPULATION_SIZE);
        Map<RoadMap, RoadMap> parents = new HashMap<>(MAX_POPULATION_SIZE);
        int p1Id = 0;
        int p2Id = 0;
        RoadMap p1 = null;
        RoadMap p2 = null;
        int c;
        for (int i = 0; i < MAX_POPULATION_SIZE; i++) {
            c = 0;
            while (p1Id == p2Id || (parents.containsKey(p1) && parents.containsValue(p2)) || (parents.containsKey(p2) && parents.containsValue(p1))) {
                p1Id = (int) (Math.random() * max);
                p2Id = (int) (Math.random() * max);
                p1 = maps.get(p1Id);
                p2 = maps.get(p2Id);
                if (++c > 10) {
                    break;
                }
            }
            assert p1 != null && p2 != null;
            parents.put(p1, p2);
            var ch = GenomeUtils.crossbreedMaps(p1, p2, stdMap);
            children.add(ch);
        }
        return children;
    }
}
