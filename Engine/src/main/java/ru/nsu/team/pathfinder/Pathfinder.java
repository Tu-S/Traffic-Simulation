package ru.nsu.team.pathfinder;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.roadmap.PlaceOfInterest;
import ru.nsu.team.entity.trafficparticipant.Path;

import java.util.Set;

public interface Pathfinder {
    /**
     * Get path from start node to the destination.
     *
     * @param start       first node of the path
     * @param destination target place of interest
     * @return path from start to destination
     */
    Path findPath(Node start, PlaceOfInterest destination);

    /**
     * Calculates paths for each destination and stores it in internal cache.
     *
     * @param start        starting node
     * @param destinations set of possible destinations
     */
    void init(Node start, Set<Node> destinations);

    /**
     * Get destinations defined in constructor or init() function that are unreachable from start position;
     *
     * @return set of unreachable destinations
     */
    Set<Node> getUnreachableDestinations();
}
