package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;

import java.util.ArrayList;

public class Spawner {
    private int minSpawnPeriod;
    private int maxSpawnPeriod;
    private Node node;
    private ArrayList<Node> possibleDestinations;


    public Spawner(Node node,int minSpawnPeriod,int maxSpawnPeriod) {
        this.node = node;
        this.minSpawnPeriod = minSpawnPeriod;
        this.maxSpawnPeriod = maxSpawnPeriod;
        this.possibleDestinations = new ArrayList<>();
    }

    public void spawn() {
    }

    public int getPossibleDestinationsNumber() {
        return possibleDestinations.size();
    }

    public Node getDestinationN(int n) {
        return possibleDestinations.get(n);
    }

    public void addPossibleDestination(Node node) {
        possibleDestinations.add(node);
    }


}
