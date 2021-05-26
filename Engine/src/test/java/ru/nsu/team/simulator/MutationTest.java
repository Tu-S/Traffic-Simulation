package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.genome.GenomeUtils;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;

import java.util.List;

public class MutationTest {

    @Test
    public void mutationTest() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;
        List<RoadMap> maps = CopierUtils.makeMaps(mapConfig, 5);
        for (var m : maps) {
            System.out.println("Old");
            show(m.getRoads());
            GenomeUtils.mutateMap(m);
            System.out.println("New");
            show(m.getRoads());
        }
        System.out.println();

    }
    private void show(List<Road> roads){
        for(Road r : roads){
            System.out.println("R id = "  + r.getId());
            for(Lane l: r.getLanes()){
                System.out.print("lane speed = " + l.getMaxSpeed()+", ");
            }
            System.out.println();
        }
    }


}
