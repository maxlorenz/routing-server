package server;

import de.topobyte.osm4j.core.model.impl.Node;
import org.apache.log4j.BasicConfigurator;
import osm.IOSMSource;
import osm.OverpassWebXML;
import persistence.IPersistence;
import persistence.InMemoryPersistence;
import routing.DijkstraRouter;
import routing.IRouter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import static spark.Spark.get;

/**
 * Created by max on 13.05.17.
 */
public class RestApi {
    public void createServer() {
        IPersistence db = new InMemoryPersistence();
        IOSMSource src = new OverpassWebXML("http://www.overpass-api.de/api/xapi?*" +
                "[bbox=10.7065,53.8368,10.7109,53.8412]");;

        try {
            src.persistTo(db);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        IRouter router = new DijkstraRouter(db);

        JsonTransformer toJson = new JsonTransformer();

        // Example: http://localhost:4567/query/Universit%C3%A4t
        get("/query/:name", (request, response) -> {
            Collection<Node> query = db.queryNodes("name", request.params(":name"));
            Collection<JSONNode> res = new ArrayList<>();

            for (Node n : query)
                res.add(new JSONNode(n));

            return res;
        }, toJson);

        // Example: http://localhost:4567/from/593196498/to/319574535
        get("/from/:from/to/:to", (req, res) -> {
            long startId = Long.parseLong(req.params(":from"));
            long goalId = Long.parseLong(req.params(":to"));

            Node start = db.getNodeById(startId);
            Node goal = db.getNodeById(goalId);

            Collection<Node> path = router.findShortestPath(start, goal);
            Collection<JSONNode> result = new ArrayList<>();

            for (Node n : path)
                result.add(new JSONNode(n));

            return result;
        }, toJson);
    }

    public static void main(String[] args) {
        // Log to stdout
        Logger logger = Logger.getLogger(String.valueOf(RestApi.class));
        BasicConfigurator.configure();

        RestApi api = new RestApi();
        api.createServer();
    }
}
