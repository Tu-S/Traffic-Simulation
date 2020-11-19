package ru.nsu.team.entity.spawner;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.PlaceOfInterest;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.pathfinder.DestinationUnreachable;
import ru.nsu.team.pathfinder.DijkstraPathfinder;
import ru.nsu.team.pathfinder.Pathfinder;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Spawner {
    private ArrayList<Configuration> configs;
    private Node node;
    private List<PlaceOfInterest> possibleDestinations;
    private Pathfinder pathfinder;
    private Road spawningQueue;


    public Spawner(Node node) {
        this.node = node;
        this.possibleDestinations = new ArrayList<>();
        this.pathfinder = new DijkstraPathfinder();
        this.configs = new ArrayList<>();
        this.spawningQueue = new Road(-1, null, node, Integer.MAX_VALUE, 1);
    }

    public Spawner(Node node, List<PlaceOfInterest> destinations) {
        this.node = node;
        this.possibleDestinations = destinations;
        this.pathfinder = new DijkstraPathfinder(destinations.stream().flatMap(poi -> poi.getNodes().stream()).collect(Collectors.toList()));
        this.configs = new ArrayList<>();
        this.spawningQueue = new Road(-1, null, node, Integer.MAX_VALUE, 1);
    }

    private PlaceOfInterest selectDestination() {
        double weightSum = possibleDestinations.stream().map(PlaceOfInterest::getWeight).reduce(Double::sum).orElseThrow(() -> new IllegalStateException("No places of interest were provided for spawner"));
        PlaceOfInterest destination;
        Random rnd = new Random();
        double selected = rnd.nextDouble() * weightSum;
        double accumulated = 0;
        for (PlaceOfInterest poi : possibleDestinations) {
            if (accumulated + poi.getWeight() >= selected) {
                return poi;
            }
        }
        return possibleDestinations.get(0);
    }

    public void spawn() {


        //TODO actually spawn cars
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

    public PlaceOfInterest getDestinationN(int n) {
        return possibleDestinations.get(n);
    }

    public void addPossibleDestination(PlaceOfInterest node) throws DestinationUnreachable {
        possibleDestinations.add(node);

    }


}
