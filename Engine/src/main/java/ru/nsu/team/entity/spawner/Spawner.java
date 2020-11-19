package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.pathfinder.DestinationUnreachable;
import ru.nsu.team.pathfinder.DijkstraPathfinder;
import ru.nsu.team.pathfinder.Pathfinder;

import java.util.ArrayList;
import java.util.List;

public class Spawner {
    private int minSpawnPeriod;
    private int maxSpawnPeriod;
    private Node node;
    private List<Node> possibleDestinations;
    private Pathfinder pathfinder;


    public Spawner(Node node, int minSpawnPeriod, int maxSpawnPeriod) {
        this.node = node;
        this.minSpawnPeriod = minSpawnPeriod;
        this.maxSpawnPeriod = maxSpawnPeriod;
        this.possibleDestinations = new ArrayList<>();
        this.pathfinder = new DijkstraPathfinder();
    }

    public Spawner(Node node, int minSpawnPeriod, int maxSpawnPeriod, List<Node> destinations) {
        this.node = node;
        this.minSpawnPeriod = minSpawnPeriod;
        this.maxSpawnPeriod = maxSpawnPeriod;
        this.possibleDestinations = destinations;
        this.pathfinder = new DijkstraPathfinder(destinations);
    }

    public void spawn() {
    }

    public int getPossibleDestinationsNumber() {
        return possibleDestinations.size();
    }

    public Node getDestinationN(int n) {
        return possibleDestinations.get(n);
    }

    public void addPossibleDestination(Node node) throws DestinationUnreachable {
        possibleDestinations.add(node);

    }


}
