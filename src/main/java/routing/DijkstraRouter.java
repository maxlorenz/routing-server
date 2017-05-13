package routing;

import de.topobyte.osm4j.core.model.impl.Node;
import osm.Utils;
import persistence.IPersistence;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by max on 13.05.17.*/
public class DijkstraRouter implements IRouter {
    private IPersistence db;

    public DijkstraRouter(IPersistence db) {
        this.db = db;
    }

    @Override
    public Collection<Node> findShortestPath(Node start, Node goal) throws NotReachableException {
        Set<DistanceNode> explored = new HashSet<>();
        PriorityQueue<DistanceNode> frontier = new PriorityQueue<>();

        frontier.add(new DistanceNode(start, 0));

        while (!frontier.isEmpty()) {
            DistanceNode current = frontier.poll();

            if (current.getNode() == goal) {
                return backtrack(current);
            }

            explored.add(current);

            for (Node neighborNode : db.getNeighbors(current.getNode())) {
                DistanceNode neighbor = toDistanceNode(neighborNode, current);

                if (!frontier.contains(neighbor) && !explored.contains(neighbor)) {
                    frontier.add(neighbor);
                } else if (frontier.contains(neighbor)) {
                    replaceInFrontierIfBetter(frontier, neighbor);
                }
            }
        }

        throw new NotReachableException(goal);
    }

    private Collection<Node> backtrack(DistanceNode n) {
        List<Node> result = new LinkedList<>();
        DistanceNode current = n;

        while (current != null) {
            result.add(0, current.getNode());
            current = current.getParent();
        }

        return result;
    }

    private DistanceNode toDistanceNode(Node node, DistanceNode parent) {
        double distance = Utils.getApproximateDistance(node, parent.getNode());
        double totalDistance = distance + parent.getDistance();

        return new DistanceNode(node, parent, totalDistance);
    }

    private void replaceInFrontierIfBetter(PriorityQueue<DistanceNode> frontier, DistanceNode toTest) {
        Predicate<DistanceNode> shouldBeReplaced = (n) -> {
            boolean isSame = n.getNode() == toTest.getNode();
            boolean isCloser = n.getDistance() > toTest.getDistance();

            return isSame && isCloser;
        };

        if (frontier.removeIf(shouldBeReplaced)) {
            frontier.add(toTest);
        }
    }
}
