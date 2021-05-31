package ru.nsu.team.simulator;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.team.entity.roadmap.RoadMap;
import ru.nsu.team.genome.GenomeUtils;

import java.util.LinkedList;
import java.util.List;

public class SelectionTest {
  @Test
  public void selectionTest() {
    List<RoadMap> roadMaps = new LinkedList<>();
    var values = new double[]{0.0383, 0.0430, 0.0469, 0.0288, 0.0309, 0.0488, 0.0235, 0.0272, 0.0202, 0.0413, 0.0488, 0.0488};
    for (double value : values) {
      RoadMap roadMap = new RoadMap();
      roadMap.setScore(value);
      roadMaps.add(roadMap);
    }
    GenomeUtils.setSurvivorRate((byte)50);
    roadMaps = GenomeUtils.selection(roadMaps);
    double[] scores = new double[5];
    int i = 0;
    for (RoadMap roadMap : roadMaps) {
      System.out.println(roadMap.getScore());
      scores[i++] = roadMap.getScore();
    }
    Assert.assertArrayEquals(new double[]{0.0383, 0.0413, 0.043, 0.0469, 0.0488}, scores, 0.00001);
  }
}
