package ru.nsu.team.simulator;

import org.junit.Test;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.genome.NodeGenome;
import ru.nsu.team.genome.RoadGenome;

public class GenomeTest {

    @Test
    public void createGenome() {
        Node from = new Node(0);
        Node to = new Node(1);

        Road road = new Road(1, from, to, 2, 70);

        NodeGenome nodeGenome = new NodeGenome(from);
        RoadGenome roadGenome = new RoadGenome(road);
        assert roadGenome.getId() == 1;
        assert roadGenome.getId() == 1;
        assert roadGenome.getFrom().getId() == 0;
        assert roadGenome.getTo().getId() == 1;
        assert roadGenome.getLanes().size() == 2;

    }
}
