package server;

import de.topobyte.osm4j.core.model.impl.Node;
import persistence.IPersistence;
import routing.DijkstraRouter;
import routing.IRouter;

import java.util.ArrayList;
import java.util.Collection;

import static spark.Spark.get;

/**
 * Created by max on 13.05.17.
 */
public class RestApi {
    public void createServer(IPersistence db) {
        IRouter router = new DijkstraRouter(db);
        JSONTransformer toJson = new JSONTransformer();

        get("/query/:name", (request, response) -> {
            Collection<Node> query = db.queryNodes("name", request.params(":name"));
            Collection<JSONNode> res = new ArrayList<>();

            for (Node n : query)
                res.add(new JSONNode(n));

            return res;
        }, toJson);

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
}
