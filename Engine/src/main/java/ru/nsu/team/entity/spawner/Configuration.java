package ru.nsu.team.entity.spawner;

public class Configuration {
    private String distribution;
    private long start;
    private long end;
    private long spawnRate;
    public int countCarsAtTime(int time) {
        return 0;
    }

    public Configuration(long start,long end,long spawnRate){
        this.start = start;
        this.end = end;
        this.spawnRate = spawnRate;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getSpawnRate() {
        return spawnRate;
    }
}
