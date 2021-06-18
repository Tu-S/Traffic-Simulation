package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.TrafficLight;
import ru.nsu.team.entity.roadmap.TrafficLightConfig;
import ru.nsu.team.genome.GenomeUtils;

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

        assert road.getId() == 1;
        assert road.getId() == 1;
        assert road.getFrom().getId() == 0;
        assert road.getTo().getId() == 1;
        assert road.getLanes().size() == 2;

        //GenomeUtils.mutateRoad(road);
        System.out.println();
        System.out.println(tl.getConfigs().get(0).getDelay());

        System.out.println(road.getFrom().getTrafficLights().get(0).getConfigs().get(0).getDelay());
        assert tl.equals(road.getFrom().getTrafficLights().get(0));

    }
}
