package ru.nsu.team.entity.spawner;

import org.apache.log4j.Logger;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.PlaceOfInterest;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Car;
import ru.nsu.team.entity.trafficparticipant.Path;
import ru.nsu.team.entity.trafficparticipant.PositionOnRoad;
import ru.nsu.team.entity.trafficparticipant.TrafficParticipant;
import ru.nsu.team.pathfinder.DijkstraPathfinder;
import ru.nsu.team.pathfinder.Pathfinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Spawner {

    private static final Logger LOG = Logger.getRootLogger();

    private ArrayList<Configuration> configs;
    private Node node;
    private List<PlaceOfInterest> possibleDestinations;
    private Pathfinder pathfinder;
    private Road spawningQueue;
    private final Random rng;


    public Spawner(Node node, Road spawningQueue) {
        this.node = node;
        this.possibleDestinations = new ArrayList<>();
        this.pathfinder = new DijkstraPathfinder();
        this.configs = new ArrayList<>();
        this.spawningQueue = spawningQueue;
        this.rng = new Random();
        spawningQueue.setEphemeral(true);
    }

    public Spawner(Node node) {
        this.node = node;
        this.possibleDestinations = new ArrayList<>();
        this.pathfinder = new DijkstraPathfinder();
        this.configs = new ArrayList<>();
        this.spawningQueue = new Road(-1, null, node);
        this.rng = new Random();
    }

    public Spawner(Node node, List<PlaceOfInterest> destinations) {
        this.node = node;
        this.possibleDestinations = destinations;
        this.pathfinder = new DijkstraPathfinder(destinations.stream().flatMap(poi -> poi.getNodes().stream()).collect(Collectors.toList()));
        this.configs = new ArrayList<>();
        this.spawningQueue = new Road(-1, null, node);
        this.rng = new Random();
    }

    private PlaceOfInterest selectDestination() {
        double weightSum = possibleDestinations.stream().map(PlaceOfInterest::getWeight).reduce(Double::sum).orElseThrow(() -> new IllegalStateException("No places of interest were provided for spawner"));
        double selected = rng.nextDouble() * weightSum;
        double accumulated = 0;
        for (PlaceOfInterest poi : possibleDestinations) {
            accumulated += poi.getWeight();
            if (accumulated >= selected) {
                return poi;
            }
        }
        return possibleDestinations.get(0);
    }

    private Configuration getCurrentConfiguration(int time) {
        return configs.stream().filter(c -> c.getStart() <= time && c.getEnd() > time).findFirst().orElse(Configuration.NO_SPAWN);
    }

    public void spawn(int time, int duration) {
        Random rng = new Random();
        Configuration config = getCurrentConfiguration(time);
        int bound = 2 * (int) config.getSpawnRate();
        if (bound <= 0) {
            return;
        }
        int toSpawn = (rng.nextInt(bound) * duration) / 60;
        LOG.debug("Will spawn " + toSpawn + " cars");
        List<TrafficParticipant> queuedCars = spawningQueue.getLaneN(0).getParticipants();
        double spawnPosition;
        if (queuedCars.isEmpty()) {
            spawnPosition = Car.DEFAULT_DISTANCE * 5;
        } else {
            spawnPosition = queuedCars.get(queuedCars.size() - 1).getPosition().getPosition() + Car.DEFAULT_DISTANCE;
        }
        for (int i = 0; i < toSpawn; i++) {
            PlaceOfInterest destination = selectDestination();
            Path path = pathfinder.findPath(node, destination);
            while (path == null && !possibleDestinations.isEmpty()) {
                LOG.debug("Destination " + destination.getId() + " can't be reached from spawner");
                // TODO add id for spawners
                possibleDestinations.remove(destination);
                destination = selectDestination();
                path = pathfinder.findPath(node, destination);
            }
            if (path == null) {
                throw new IllegalStateException("Spawner has no reachable destinations");
            }
            TrafficParticipant spawnedCar = new TrafficParticipant(new Car(Car.getNextId(), Car.DEFAULT_MAX_SPEED, path), new PositionOnRoad(spawningQueue, spawnPosition, 0));
            spawningQueue.addTrafficParticipant(spawnedCar);
            spawnPosition += Car.DEFAULT_DISTANCE;
        }
    }

    public void spawnCar(){

    }

    public void spawnCar(double position){

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
        configs.sort((a, b) -> (int) (a.getStart() - b.getStart()));
    }

    public int getPossibleDestinationsNumber() {
        return possibleDestinations.size();
    }

    public PlaceOfInterest getDestinationN(int n) {
        return possibleDestinations.get(n);
    }

    public void addPossibleDestination(PlaceOfInterest node) {
        possibleDestinations.add(node);
    }

    public void addPossibleDestination(Collection<PlaceOfInterest> pois) {
        possibleDestinations.addAll(pois);
    }

}
