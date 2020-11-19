package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.pathfinder.DestinationUnreachable;
import ru.nsu.team.pathfinder.DijkstraPathfinder;
import ru.nsu.team.pathfinder.Pathfinder;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;

public class Spawner {
    private ArrayList<Configuration> configs;
    private Node node;
    private List<Node> possibleDestinations;
    private Pathfinder pathfinder;


    public Spawner(Node node) {
        this.node = node;
        this.possibleDestinations = new ArrayList<>();
        this.pathfinder = new DijkstraPathfinder();
        this.configs = new ArrayList<>();
    }

    public Spawner(Node node, List<Node> destinations) {
        this.node = node;
        this.possibleDestinations = destinations;
        this.pathfinder = new DijkstraPathfinder(destinations);
        this.configs = new ArrayList<>();
    }

    public void spawn() {
    }

    public Configuration getConfigN(int n) {
        return configs.get(n);
    }

    public int getConfigsNumber() {
        return configs.size();
    }

    public Node getNode() {
        return node;
    }

    public void addConfiguration(Configuration config) {
        configs.add(config);
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
