package ru.nsu.team.pathfinder;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.trafficparticipant.Path;

import java.util.Set;

public interface Pathfinder {
    /**
     * Get path from start node to the destination.
     *
     * @param start       first node of the path
     * @param destination last node of the path
     * @return path from start to destination
     */
    Path findPath(Node start, Node destination);

    /**
     * Calculates paths for each destination and stores it in internal cache.
     *
     * @param start        starting node
     * @param destinations set of possible destinations
     */
    void init(Node start, Set<Node> destinations);
}
