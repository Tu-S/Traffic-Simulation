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
    int border = 10;
    for (int i = 0; i < border; i++) {
      RoadMap roadMap = new RoadMap();
      roadMap.setScore(border - i);
      roadMaps.add(roadMap);
    }
    GenomeUtils.setSurvivorRate((byte)50);
    roadMaps = GenomeUtils.selection(roadMaps);
    double[] scores = new double[5];
    int i = 0;
    for (RoadMap roadMap : roadMaps) {
      scores[i++] = roadMap.getScore();
    }
    Assert.assertArrayEquals(new double[]{6.0, 7.0, 8.0, 9.0, 10.0}, scores, 0.1);
  }
}
