package ru.nsu.team.genome;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.*;
import ru.nsu.team.entity.spawner.Configuration;
import ru.nsu.team.entity.spawner.Spawner;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;
import ru.nsu.team.simulator.Simulator;

import java.util.*;

public class AlgorithmVersion1 {

    private static final int MAX_POPULATION_SIZE = 5;
    private static final int MAX_GENERATION_NUMBER = 2;
    private static RoadMap stdMap;

    public static void runAlgorithm() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/test_map.tsp");
        assert mapConfig != null;
        double requiredScore = 100;
        int curGeneration = 0;

        List<RoadMap> generation = CopierUtils.makeMaps(mapConfig, MAX_POPULATION_SIZE);
        stdMap = CopierUtils.copy(generation.get(0));
        assert stdMap != null;
        stdMap.setScore(0);
        mutationBlock(generation);
        simulationBlock(generation);
//        for (RoadMap map : generation) {
//            GenomeUtils.setDefaultState(map, stdMap);
//        }
        RoadMap bestMap;
        generation = GenomeUtils.selection(generation);
        for (RoadMap map : generation) {
            System.out.println(map.getScore());
        }
        bestMap = generation.get(generation.size() - 1);
        if (bestMap.getScore() >= requiredScore) {
            System.out.println("bestMapScore = " + bestMap.getScore());
            return;
        }
        while (bestMap.getScore() < requiredScore && curGeneration < MAX_GENERATION_NUMBER) {
            System.out.println("GENERATION #" + curGeneration);
            //generation = breedingBlock(generation);
            //mutationBlock(generation);
            for (RoadMap map : generation) {
                GenomeUtils.setDefaultState(map, stdMap);
            }
            simulationBlock(generation);
            generation = GenomeUtils.selection(generation);
//            if (bestMap.getScore() < generation.get(generation.size() - 1).getScore()) {
//                bestMap = generation.get(generation.size() - 1);
//            } else {
//                //mutationBlock(generation);
//            }
            curGeneration++;
        }
        System.out.println("size = " + generation.size());


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
            System.out.println("Map score " + m.getScore());
        }
    }

    private static void mutationBlock(List<RoadMap> maps) {
        for (var m : maps) {
            GenomeUtils.mutateMap(m);
            GenomeUtils.setDefaultState(m, stdMap);
        }
    }

    private static List<RoadMap> breedingBlock(List<RoadMap> maps) {
        int max = maps.size();
        List<RoadMap> children = new ArrayList<>(max);
        Map<RoadMap, RoadMap> parents = new HashMap<>(max);
        int p1Id = 0;
        int p2Id = 0;
        RoadMap p1 = null;
        RoadMap p2 = null;
        int c;
        for (int i = 0; i < MAX_POPULATION_SIZE - 1; i++) {
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
