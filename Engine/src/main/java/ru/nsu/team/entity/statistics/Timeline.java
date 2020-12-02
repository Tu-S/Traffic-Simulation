package ru.nsu.team.entity.statistics;

public class Timeline {
  private int begin;
  private int end;

  public Timeline(int begin, int end) {
    this.begin = begin;
    this.end = end;
  }

  public int getBegin() {
    return begin;
  }

  public int getEnd() {
    return end;
  }
}
