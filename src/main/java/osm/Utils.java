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

    public static double getAccurateDistance(Node from, Node to) {
        double phi1 = Math.toRadians(from.getLatitude());
        double phi2 = Math.toRadians(to.getLatitude());
        double dPhi = Math.toRadians(to.getLatitude() - from.getLatitude());
        double dLambda = Math.toRadians(to.getLongitude() - from.getLongitude());

        double a = Math.sin(dPhi/2.0) * Math.sin(dPhi/2.0) +
                Math.cos(phi1) * Math.cos(phi2) +
                Math.sin(dLambda/2.0) * Math.sin(dLambda/2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
