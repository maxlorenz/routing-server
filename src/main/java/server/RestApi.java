package server;

import org.apache.log4j.BasicConfigurator;

import java.util.logging.Logger;

import static spark.Spark.get;

/**
 * Created by max on 13.05.17.
 */
public class RestApi {
    JsonTransformer toJson;

    public void createServer() {
        toJson = new JsonTransformer();

        get("/query", (request, response) -> {
            return "Hello, world";
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
