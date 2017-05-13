package routing;

import de.topobyte.osm4j.core.model.impl.Node;

import java.util.Collection;

/**
 * Created by max on 13.05.17.
 */
public interface IRouter {
    public Collection<Node> findShortestPath(Node start, Node goal) throws NotReachableException;
}
