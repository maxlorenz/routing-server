package routing;

import de.topobyte.osm4j.core.model.impl.Node;

/**
 * Created by max on 13.05.17.
 */
public class DistanceNode implements Comparable {
    private Node node;
    private DistanceNode parent;
    private double distance;

    public DistanceNode(Node node, double distance) {
        this.node = node;
        this.distance = distance;
    }

    public DistanceNode(Node node, DistanceNode parent, double distance) {
        this.node = node;
        this.parent = parent;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public int compareTo(Object o) {
        double d1 = ((DistanceNode) o).getDistance();
        return (int) (distance - d1);
    }

    public DistanceNode getParent() {
        return parent;
    }

    public void setParent(DistanceNode parent) {
        this.parent = parent;
    }
}
