package persistence;

import de.topobyte.osm4j.core.model.iface.OsmTag;

/**
 * Created by max on 21.05.17.
 */
public class TestOsmTag implements OsmTag {
    private String key;
    private String value;

    public TestOsmTag(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
