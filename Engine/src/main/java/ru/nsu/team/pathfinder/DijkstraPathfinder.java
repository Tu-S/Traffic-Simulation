package ru.nsu.team.pathfinder;

import ru.nsu.team.entity.roadmap.Course;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.PlaceOfInterest;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Path;

import java.io.Serializable;
import java.util.*;

public class DijkstraPathfinder implements Pathfinder, Serializable {
    private final Map<Map.Entry<Node, Node>, Path> cache;
    private final Set<Node> knownUnreachable;
    private final Set<Node> possibleDestinations;

    private static class PathNode implements Comparable<PathNode> {
        Node node;
        double distance;
        ArrayList<Road> path;

        public PathNode(Node node, double distance, ArrayList<Road> path) {
            this.node = node;
            this.distance = distance;
            this.path = path;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(PathNode o) {
            return (int) (distance - o.distance);
        }
    }

    public DijkstraPathfinder() {
        cache = new HashMap<>();
        possibleDestinations = new HashSet<>();
        knownUnreachable = new HashSet<>();
    }

    public DijkstraPathfinder(List<Node> destinations) {
        cache = new HashMap<>();
        knownUnreachable = new HashSet<>();
        possibleDestinations = new HashSet<>(destinations);
    }

    public void init(Node start, Set<Node> destinations) throws DestinationUnreachable {
        PathNode source = new PathNode(start, 0, new ArrayList<>());

        Set<Node> settledNodes = new HashSet<>();
        PriorityQueue<PathNode> unsettledNodes = new PriorityQueue<>();
        HashMap<Node, PathNode> pathNodes = new HashMap<>();


        unsettledNodes.add(source);
        //TODO process only until all destinations are found
        //TODO check if can move like this through the intersection
        while (unsettledNodes.size() != 0) {
            PathNode currentNode = unsettledNodes.poll();
            for (Course course :
                    currentNode.node.getCourses()) {
                Road road = course.getToLane().getParentRoad();
                Node target = road.getExitNode();
                if (settledNodes.contains(target)) {
                    continue;
                }
                if (!pathNodes.containsKey(target)) {
                    ArrayList<Road> path = new ArrayList<>(currentNode.path);
                    path.add(road);
                    PathNode newPathNode = new PathNode(target, currentNode.distance + road.getLength(), path);
                    pathNodes.put(target, newPathNode);
                    unsettledNodes.offer(pathNodes.get(target));
                } else {
                    PathNode currentNeighbour = pathNodes.get(target);
                    if (currentNeighbour.distance > currentNode.distance + road.getLength()) {
                        unsettledNodes.remove(currentNeighbour);
                        currentNeighbour.distance = currentNode.distance + road.getLength();
                        ArrayList<Road> path = new ArrayList<>(currentNode.path);
                        path.add(road);
                        currentNeighbour.path = path;
                        unsettledNodes.offer(currentNeighbour);
                    }
                }


            }
            settledNodes.add(currentNode.node);
        }
        for (Node destination : destinations) {
            if (!pathNodes.containsKey(destination)) {
                knownUnreachable.add(destination);
            } else {
                cache.put(new AbstractMap.SimpleEntry<>(start, destination), new Path(pathNodes.get(destination).path));
            }
        }
        cache.remove(new AbstractMap.SimpleEntry<>(start, start));
    }

    @Override
    public Set<Node> getUnreachableDestinations() {
        return knownUnreachable;
    }

    private Path getBestCachedResult(Node start, PlaceOfInterest destination) {
        List<Node> nodes = destination.getNodes();
        Path bestCachedResult = null;
        for (Node node : nodes) {
            Path cachedResult = cache.get(new AbstractMap.SimpleEntry<>(start, node));
            if (cachedResult == null) {
                continue;
            }
            if (bestCachedResult == null) {
                bestCachedResult = cachedResult;
                continue;
            }
            if (cachedResult.getPathLength() < bestCachedResult.getPathLength()) {
                bestCachedResult = cachedResult;
            }

        }
        return bestCachedResult;
    }

    @Override
    public Path findPath(Node start, PlaceOfInterest destination) throws DestinationUnreachable {
        Path bestCachedResult = getBestCachedResult(start, destination);
        if (bestCachedResult != null) {
            return bestCachedResult.copy();
        }
        possibleDestinations.addAll(destination.getNodes());
        init(start, possibleDestinations);
        bestCachedResult = getBestCachedResult(start, destination);
        return bestCachedResult == null ? null : bestCachedResult.copy();
    }
}
