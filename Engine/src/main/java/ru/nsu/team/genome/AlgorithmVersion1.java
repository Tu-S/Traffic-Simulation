package ru.nsu.team.genome;

import ru.nsu.team.entity.playback.PlaybackBuilder;
import ru.nsu.team.entity.report.HeatmapBuilder;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;
import ru.nsu.team.simulator.Simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmVersion1 {

    private static final int MAX_POPULATION_SIZE = 100;
    private static RoadMap stdMap;

    public static void runAlgorithm() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;
        int maxGeneration = 100;
        double requiredScore = 100;
        int curGeneration = 0;
        List<RoadMap> generation = CopierUtils.makeMaps(mapConfig, MAX_POPULATION_SIZE);
        stdMap = CopierUtils.copy(generation.get(0));
        stdMap.setScore(0);

        for (RoadMap m : generation) {
            GenomeUtils.mutateMap(m);
        }


        simulationBlock(generation);
        RoadMap bestMap;
        var selectedMaps = GenomeUtils.selection(generation);
        bestMap = selectedMaps.get(selectedMaps.size() - 1);
        if (bestMap.getScore() >= requiredScore) {
            System.out.println("bestMapScore = " + bestMap.getScore());
            return;
        }
        /*curGeneration < maxGeneration && bestMap.getScore() < requiredScore*/
        while (bestMap.getScore() < requiredScore && curGeneration <= maxGeneration) {
            generation = breedingBlock(generation);
            simulationBlock(generation);
            selectedMaps = GenomeUtils.selection(generation);
            if (bestMap.getScore() < selectedMaps.get(selectedMaps.size() - 1).getScore()) {
                bestMap = selectedMaps.get(selectedMaps.size() - 1);
            }
            curGeneration++;
        }
        System.out.println("size = " + generation.size());


    }

    private static void simulationBlock(List<RoadMap> maps) {
        for (RoadMap m : maps) {
            HeatmapBuilder hmb = new HeatmapBuilder(m, 100);
            Simulator sim = new Simulator(100, m, new PlaybackBuilder(), hmb);
            sim.start();
            m.setScore(hmb.getScore());
            System.out.println(m.getScore());
        }
    }

    private static void mutationBlock(List<RoadMap> maps) {
        for (var m : maps) {
            GenomeUtils.mutateMap(m);
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
        for (int i = 0; i < MAX_POPULATION_SIZE; i++) {
            while (p1Id == p2Id || (parents.containsKey(p1) && parents.containsValue(p2)) || (parents.containsKey(p2) && parents.containsValue(p1))) {
                p1Id = (int) (Math.random() * max);
                p2Id = (int) (Math.random() * max);
                p1 = maps.get(p1Id);
                p2 = maps.get(p2Id);
            }
            parents.put(p1, p2);
            children.add(GenomeUtils.crossbreedMaps(p1, p2, stdMap));
        }
        return children;
    }
}
