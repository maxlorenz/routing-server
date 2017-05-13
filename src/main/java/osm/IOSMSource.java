package osm;

import persistence.IPersistence;

import java.io.IOException;

/**
 * Created by max on 01.05.17.
 */
public interface IOSMSource {
    public void persistTo(IPersistence persistence) throws IOException;
}
