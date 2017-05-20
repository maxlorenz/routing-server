import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import osm.IOSMSource;
import osm.PBFSource;
import persistence.IPersistence;
import persistence.InMemoryPersistence;
import server.RestApi;

import static org.apache.log4j.helpers.UtilLoggingLevel.SEVERE;

public class ExampleRouting {
    public static void main(String[] args) {
        // Log to stdout
        Logger logger = Logger.getLogger(String.valueOf(RestApi.class));
        BasicConfigurator.configure();

        try {
            IPersistence db = new InMemoryPersistence();
            IOSMSource src = new PBFSource("res/monaco-latest.osm.pbf");

            src.persistTo(db);

            RestApi api = new RestApi();
            api.createServer(db);
        } catch (Exception ex) {
            logger.log(SEVERE, "Could not start server because of an error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
