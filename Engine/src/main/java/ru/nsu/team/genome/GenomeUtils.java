package ru.nsu.team.genome;

import java.util.List;
import ru.nsu.team.simulator.Simulator;

public class GenomeUtils {
  public List<Simulator> selection(List<Simulator> simulators) {
    int threshold = 1;
    if (simulators.size() > 3) {
      threshold = simulators.size() / 2;
    }
    simulators.sort((o1, o2) -> {
      Double score1 = o1.getReporterBuilder().getScore();
      Double score2 = o2.getReporterBuilder().getScore();
      return score1.compareTo(score2);
    });
    return simulators.subList(0, threshold);
  }
}
