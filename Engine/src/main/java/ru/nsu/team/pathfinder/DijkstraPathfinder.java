package ru.nsu.team.pathfinder;

import ru.nsu.team.entity.roadmap.Course;
import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.Road;
import ru.nsu.team.entity.trafficparticipant.Path;

import java.util.*;

public class DijkstraPathfinder implements Pathfinder {
    Map<Map.Entry<Node, Node>, Path> cache;

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
    }

    public void init(Node start, Set<Node> destinations) throws DestinationUnreachable {
        PathNode source = new PathNode(start, 0, new ArrayList<>());

        Set<Node> settledNodes = new HashSet<>();
        PriorityQueue<PathNode> unsettledNodes = new PriorityQueue<>();
        HashMap<Node, PathNode> pathNodes = new HashMap<>();


        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            PathNode currentNode = unsettledNodes.poll();
            for (Course course :
                    currentNode.node.getCourses()) {
                Road road = course.getFromRoad();
                Node target = road.getExitNode();
                if (settledNodes.contains(target)) {
                    continue;
                }
                if (!pathNodes.containsKey(target)) {
                    ArrayList<Road> path = new ArrayList<Road>(currentNode.path);
                    path.add(road);
                    PathNode newPathNode = new PathNode(target, currentNode.distance + road.getLength(), path);
                    pathNodes.put(target, newPathNode);
                } else {
                    PathNode currentNeighbour = pathNodes.get(target);
                    if (currentNeighbour.distance > currentNode.distance + road.getLength()) {
                        unsettledNodes.remove(currentNeighbour);
                        currentNeighbour.distance = currentNode.distance + road.getLength();
                        ArrayList<Road> path = new ArrayList<Road>(currentNode.path);
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
                throw new DestinationUnreachable();
            }
            cache.put(new AbstractMap.SimpleEntry<>(start, destination), new Path(pathNodes.get(destination).path));
        }
    }

    @Override
    public Path findPath(Node start, Node destination) throws DestinationUnreachable {
        Path cachedResult = cache.get(new AbstractMap.SimpleEntry<>(start, destination));
        if (cachedResult != null) {
            return cachedResult;
        }
        HashSet<Node> destinations = new HashSet<>();
        destinations.add(destination);
        init(start, destinations);
        return cache.get(new AbstractMap.SimpleEntry<>(start, destination));
    }
}