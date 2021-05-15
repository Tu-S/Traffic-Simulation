package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.TrafficLight;
import ru.nsu.team.entity.roadmap.TrafficLightConfig;
import ru.nsu.team.genome.GenomeUtils;
import ru.nsu.team.genome.NodeGenome;
import ru.nsu.team.genome.RoadGenome;

import java.util.ArrayList;
import java.util.List;

public class GenomeTest {

    @Test
    public void createGenome() {
        Node from = new Node(0);
        Node to = new Node(1);
        var tl = new TrafficLight();
        tl.addConfig(new TrafficLightConfig(5));
        from.addTrafficLight(tl);
        to.addTrafficLight(tl);
        Road road = new Road(1, from, to, 2, 70);

        NodeGenome nodeGenome = new NodeGenome(from);
        RoadGenome roadGenome = new RoadGenome(road);
        assert roadGenome.getId() == 1;
        assert roadGenome.getId() == 1;
        assert roadGenome.getFrom().getId() == 0;
        assert roadGenome.getTo().getId() == 1;
        assert roadGenome.getLanes().size() == 2;

        GenomeUtils.mutateRoad(roadGenome);
        System.out.println();
        System.out.println(tl.getConfigs().get(0).getDelay());

        System.out.println(roadGenome.getFrom().getTrafficLights().get(0).getConfigs().get(0).getDelay());
        assert !tl.equals(roadGenome.getFrom().getTrafficLights().get(0));

    }
}
