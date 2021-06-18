package ru.nsu.team.simulator;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.team.entity.roadmap.Lane;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.readers.RoadMapReader;
import ru.nsu.team.roadmodelcreator.CopierUtils;

import java.util.List;

public class CopierUtilsTest {

    @Test
    public void copyMap() {
        RoadMapReader roadMapReader = new RoadMapReader();
        var mapConfig = roadMapReader.getMapConfig("config/1.tsp");
        assert mapConfig != null;
        List<RoadMap> maps = CopierUtils.makeMaps(mapConfig, 5);
        assert !maps.get(0).equals(maps.get(1));
        for (var r1 : maps.get(0).getRoads()) {
            for (var r2 : maps.get(1).getRoads()) {
                assert !r1.equals(r2);
            }
        }
        RoadMap mapToCopy = maps.get(0);
        RoadMap copiedMap = CopierUtils.copy(mapToCopy);
        Assert.assertNotEquals(mapToCopy, copiedMap);
        for (Road r : copiedMap.getRoads()) {
            for (Road r2 : mapToCopy.getRoads()) {
                Assert.assertNotEquals(r, r2);
            }
        }
        Road roadToCopy = maps.get(1).getRoads().get(1);
        Road copiedRoad = (Road) CopierUtils.copy(roadToCopy);
        //Assert.assertNotNull(copiedRoad);
        Assert.assertNotEquals(copiedRoad, roadToCopy);
        for (Lane cL : copiedRoad.getLanes()) {
            for (Lane oL : roadToCopy.getLanes()) {
                Assert.assertNotEquals(cL, oL);
            }
        }

        RoadMap test = new RoadMap();
        for(Road r : mapToCopy.getRoads()){
            test.addRoad(r);

        }
        for(Road r : copiedMap.getRoads()){
            test.addRoad(r);
        }
        mapToCopy.getRoads().get(0).setLength(666);
        Assert.assertEquals(mapToCopy.getRoads().get(0).getLength(),test.getRoads().get(0).getLength(),0);
        RoadMap copiedTest = CopierUtils.copy(test);
        mapToCopy.getRoads().get(0).setLength(777);

        Assert.assertNotEquals(mapToCopy.getRoads().get(0).getLength(), copiedTest.getRoads().get(0).getLength(),0);
        Assert.assertEquals(copiedTest.getRoads().get(0).getLength(),666,0);


    }


}
