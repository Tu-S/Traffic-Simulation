package ru.nsu.team.genome;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.roadmap.configuration.RoadMapConfiguration;
import ru.nsu.team.other.KeyValuePair;
import ru.nsu.team.roadmodelcreator.CopierUtils;
import ru.nsu.team.simulator.Simulator;

import java.util.*;

public class AlgorithmVersion2 {

    private static final int MAX_POPULATION_SIZE = 20;
    private static final int MAX_GENERATION_NUMBER = 30;
    private static final double requiredScore = 0.9d;
    private static final double scoreDelta = 0.1d;
    private static final double mutationRate = 0.30d;
    private static final double okScore = requiredScore - scoreDelta;
    private static RoadMap stdMap;
    private static RoadMap bestMap;

    public static RoadMap runAlgorithm(RoadMapConfiguration mapConfig) {
        List<RoadMap> bestMaps = new ArrayList<>(100);
        int curGeneration = 0;

        List<RoadMap> generation = CopierUtils.makeMaps(mapConfig, MAX_POPULATION_SIZE);
        stdMap = CopierUtils.copy(generation.get(0));
        assert stdMap != null;
        stdMap.setScore(0);


        mutationBlock(generation);
        System.out.println("sim 1");
        System.out.println("GENERATION #" + curGeneration);
        simulationBlock(generation);
        //generation = GenomeUtils.selection(generation);
        curGeneration++;
        showScore(generation);
        setDefaultStats(generation);

        bestMap = generation.get(generation.size() - 1);
        if (bestMap.getScore() >= okScore) {
            System.out.println("bestMapScore = " + bestMap.getScore());
            return bestMap;
        }
        while (bestMap.getScore() < okScore && curGeneration < MAX_GENERATION_NUMBER) {
            System.out.println("GENERATION #" + curGeneration);
            List<RoadMap> oldGeneration = CopierUtils.copy(generation);
            setDefaultStats(generation);
            generation = breedingBlock(generation);
            simulationBlock(generation);
            generation = GenomeUtils.selection(generation, oldGeneration);
            showScore(generation);
            if (generation.get(generation.size() - 1).getScore() < bestMap.getScore()) {
                mutationBlock(generation);
            } else if (generation.get(generation.size() - 1).getScore() > bestMap.getScore()) {
                var m = generation.get(generation.size() - 1);
                System.out.println("old best score = " + bestMap.getScore() + " new score = " + m.getScore());
                RoadMap c = CopierUtils.copy(m);
                bestMap = c;
                bestMaps.add(c);
            }

            curGeneration++;
        }
        System.out.println("Best score = " + bestMap.getScore());
        System.out.println("size = " + generation.size());
        for (var m : bestMaps) {
            System.out.println("Map " + m.getMapId() + " score = " + m.getScore());

        }
        return bestMap;
    }

    private static void showScore(List<RoadMap> maps) {
        maps.sort(Comparator.comparing(RoadMap::getScore));
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
        for (var map: maps) {
            if (Math.random() < mutationRate) {
                GenomeUtils.mutateMap(map);
            }
        }
    }



    private static int selectParentId(int totalWeight) {
        int target = rnd.nextInt(totalWeight);
        int cap = 1;
        int id = 1;
        while (target >= cap) {
            id++;
            cap += id;
        }
        return id - 1;
    }

    private static List<RoadMap> breedingBlock(List<RoadMap> maps) {
        int totalWeight = ((1 + maps.size()) * maps.size()) / 2;
        List<RoadMap> children = new ArrayList<>(MAX_POPULATION_SIZE);
        for (int i = 0; i < MAX_POPULATION_SIZE; i++) {
            int parent1Id = selectParentId(totalWeight);
            int parent2Id = parent1Id;
            while (parent1Id == parent2Id) {
                parent2Id = selectParentId(totalWeight);
            }
            RoadMap child = GenomeUtils.crossbreedMaps(CopierUtils.copy(maps.get(parent1Id)), maps.get(parent2Id));
            children.add(child);
        }

        return children;
    }
}
