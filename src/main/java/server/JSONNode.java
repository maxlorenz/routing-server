package server;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.util.OsmModelUtil;
import de.topobyte.osm4j.utils.OsmUtils;

import java.util.Map;

/**
 * Created by max on 13.05.17.
 */
public class JSONNode {
    public double longitude;
    public double latitude;
    public Map<String, String> tags;

    public JSONNode(Node node) {
        this.longitude = node.getLongitude();
        this.latitude = node.getLatitude();
        this.tags = OsmModelUtil.getTagsAsMap(node);
    }
}
