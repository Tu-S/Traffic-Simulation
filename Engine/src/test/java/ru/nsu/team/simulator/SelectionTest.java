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
    List<RoadMap> roadMapsN = new LinkedList<>();
    var values = new double[]{0.0083, 0.0120, 0.0139, 0.0288, 0.0309, 0.0330};
    var valuesN = new double[]{0.0383, 0.0430, 0.0469, 0.0588, 0.0609, 0.0701};
    for (double value : values) {
      RoadMap roadMap = new RoadMap();
      roadMap.setScore(value);
      roadMaps.add(roadMap);
    }
    for (double value : valuesN) {
      RoadMap roadMap = new RoadMap();
      roadMap.setScore(value);
      roadMapsN.add(roadMap);
    }
    GenomeUtils.setSurvivorRate(50);
    roadMaps = GenomeUtils.selection(roadMapsN, roadMaps);
    double[] scores = new double[6];
    int i = 0;
    for (RoadMap roadMap : roadMaps) {
      System.out.println(roadMap.getScore());
      scores[i++] = roadMap.getScore();
    }
    Assert.assertArrayEquals(new double[]{0.0701, 0.0609, 0.0588, 0.0288, 0.0309, 0.0330}, scores, 0.00001);
  }
}
