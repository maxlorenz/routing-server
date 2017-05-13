package routing;

import de.topobyte.osm4j.core.model.impl.Node;

/**
 * Created by max on 13.05.17.
 */
public class NotReachableException extends Exception {
    private Node goal;

    public NotReachableException(Node goal) {
        this.goal = goal;
    }

    public Node getGoal() {
        return goal;
    }
}
