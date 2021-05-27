package ru.nsu.team.simulator;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Node;
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

    private void show(List<Road> roads) {
        for (Road r : roads) {
            Node to = r.getTo();
            checkPeriod(to);
            Node from = r.getFrom();
            checkPeriod(from);
            System.out.println();
        }
    }

    private void checkPeriod(Node node) {
        int s = 0;
        for (var tr : node.getTrafficLights()) {
            for (var conf : tr.getConfigs()) {
                s += conf.getDelay();
            }
            Assert.assertEquals(tr.getPeriod(), s);
            System.out.println("p = " + tr.getPeriod() + " s = " + s);
            s = 0;
        }
    }

}
