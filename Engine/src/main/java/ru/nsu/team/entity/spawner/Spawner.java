package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;

import java.util.ArrayList;

public class Spawner {
    private Configuration configuration;
    private Node node;
    private ArrayList<Node> possibleDestinations;


    public Spawner(Configuration config, Node node) {
        this.configuration = config;
        this.node = node;
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
