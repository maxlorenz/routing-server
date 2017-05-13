import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import osm.IOSMSource;
import osm.OverpassWebXML;
import persistence.IPersistence;
import persistence.InMemoryPersistence;
import routing.DijkstraRouter;
import routing.IRouter;

import java.util.Collection;

public class OSMImporter {
    public static void main(String[] args) {
        IPersistence db = new InMemoryPersistence();
        IOSMSource src = new OverpassWebXML("http://www.overpass-api.de/api/xapi?*" +
                "[bbox=10.7065,53.8368,10.7109,53.8412]");;

        try {
            src.persistTo(db);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        IRouter router = new DijkstraRouter(db);

        try {
            Node start = db.getNodeById(593196498L);
            Node goal = db.getNodeById(319574535L);

            Collection<Node> path = router.findShortestPath(start, goal);

            for (Node n : path) {
                System.out.println(OsmModelUtil.getTagsAsMap(n).get("name"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
