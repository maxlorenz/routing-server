package osm;

import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.pbf.seq.PbfReader;
import persistence.IPersistence;
import persistence.PersistingOsmHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by max on 20.05.17.
 */
public class PBFSource implements IOSMSource {
    private PbfReader reader;

    public PBFSource(String file) throws FileNotFoundException {
        reader = new PbfReader(file, false);
    }

    @Override
    public void persistTo(IPersistence persistence) throws IOException {
        PersistingOsmHandler handler = new PersistingOsmHandler(persistence);
        reader.setHandler(handler);

        try {
            reader.read();
        } catch (OsmInputException e) {
            throw new IOException("Could not read file: " + e.getMessage());
        }
    }
}
