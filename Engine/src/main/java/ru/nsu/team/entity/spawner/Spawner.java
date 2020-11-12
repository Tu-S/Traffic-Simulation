package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;

import java.io.ObjectInputFilter;
import java.util.ArrayList;

public class Spawner {
    private ArrayList<Configuration> configs;
    private Node node;
    private ArrayList<Node> possibleDestinations;


    public Spawner(Node node) {
        this.node = node;
        this.possibleDestinations = new ArrayList<>();
        this.configs = new ArrayList<>();
    }

    public void spawn() {
    }

    public Configuration getConfigN(int n) {
        return configs.get(n);
    }
    public int getConfigsNumber(){
        return configs.size();
    }

    public Node getNode() {
        return node;
    }
    public void addConfiguration(Configuration config){
        configs.add(config);
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
