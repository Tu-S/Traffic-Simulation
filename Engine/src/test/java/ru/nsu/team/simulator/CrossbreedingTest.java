package ru.nsu.team.simulator;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.genome.GenomeUtils;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;

import java.util.List;

public class CrossbreedingTest {

    @Test
    public void testCrossbreeding() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;

        List<RoadMap> maps = CopierUtils.makeMaps(mapConfig, 2);
        RoadMap stdMap = CopierUtils.copy(maps.get(0));


        maps.get(0).getRoadN(1).addTrafficParticipant(new TrafficParticipant(new Car(777, 78, null), new PositionOnRoad(maps.get(0).getRoadN(1), 999, 0)));

        var childMap = GenomeUtils.crossbreedMaps(maps.get(0), maps.get(1), stdMap);

        Assert.assertEquals(0, childMap.getRoads().get(1).getTrafficParticipants().size());
        Assert.assertEquals(0, childMap.getRoads().get(1).getLanes().get(0).getParticipants().size());


        var childMap2 = GenomeUtils.crossbreedMaps(maps.get(0), maps.get(1), maps.get(0));
        Assert.assertEquals(1, childMap2.getRoads().get(1).getTrafficParticipants().size());
        Assert.assertEquals(1, childMap2.getRoads().get(1).getLanes().get(0).getParticipants().size());

        Assert.assertNotEquals(childMap, maps.get(0));
        Assert.assertNotEquals(childMap, maps.get(1));
        Assert.assertEquals(childMap.getRoads().size(), maps.get(0).getRoads().size());
        Assert.assertEquals(childMap.getRoads().size(), maps.get(1).getRoads().size());
        int rN = childMap.getRoadsNumber();
        var childRoads = childMap.getRoads();
        var parent0Roads = maps.get(0).getRoads();
        var parent1Roads = maps.get(1).getRoads();

        for (int i = 0; i < rN; i++) {
            var cR = childRoads.get(i);
            var p0R = parent0Roads.get(i);
            var p1R = parent1Roads.get(i);

            Assert.assertNotEquals(cR, p0R);
            Assert.assertNotEquals(cR, p1R);
            Assert.assertNotEquals(cR.getFrom(), p0R.getFrom());
            Assert.assertNotEquals(cR.getFrom(), p1R.getFrom());
            Assert.assertNotEquals(cR.getTo(), p0R.getTo());
            Assert.assertNotEquals(cR.getTo(), p1R.getTo());

            var cFrom = cR.getFrom();
            var p0From = p0R.getFrom();
            var p1From = p1R.getFrom();
            Assert.assertEquals(cFrom.getTrafficLights().size(), p0From.getTrafficLights().size());
            Assert.assertEquals(cFrom.getTrafficLights().size(), p1From.getTrafficLights().size());
            for (int t = 0; t < cFrom.getTrafficLights().size(); t++) {

                Assert.assertNotEquals(cFrom.getTrafficLights().get(t), p0From.getTrafficLights().get(t));
                Assert.assertNotEquals(cFrom.getTrafficLights().get(t), p1From.getTrafficLights().get(t));
            }
            var cL = cR.getLanes();
            var p0L = p0R.getLanes();
            var p1L = p1R.getLanes();
            for (int j = 0; j < cL.size(); j++) {
                Assert.assertNotEquals(cL.get(j), p0L.get(j));
                Assert.assertNotEquals(cL.get(j), p1L.get(j));
            }

        }

    }

}
