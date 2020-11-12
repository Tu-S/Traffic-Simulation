package ru.nsu.team.pathfinder;

import ru.nsu.team.entity.roadmap.Node;
import ru.nsu.team.entity.trafficparticipant.Path;

import java.util.Set;

public interface Pathfinder {
    Path findPath(Node start, Node destination);

    void init(Node start, Set<Node> destinations);
}
