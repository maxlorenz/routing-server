package osm;

import de.topobyte.osm4j.core.model.impl.Node;

/**
 * Created by max on 13.05.17.
 */
public class Utils {
    final static double R = 6371e3;

    public static double getApproximateDistance(Node from, Node to) {
        double x_ = to.getLongitude() - from.getLongitude();
        double x = x_ * Math.cos((from.getLatitude() + to.getLatitude()) / 2.0);
        double y = to.getLatitude() - from.getLatitude();

        return Math.sqrt(x*x + y*y) * R;
    }
}
