package ru.nsu.team.entity.statistics;

public class Timeline {
  private long begin;
  private long end;

  public Timeline(long begin, long end) {
    this.begin = begin;
    this.end = end;
  }

  public long getBegin() {
    return begin;
  }

  public long getEnd() {
    return end;
  }
}
