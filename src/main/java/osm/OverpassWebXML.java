package osm;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.xml.dynsax.OsmXmlIterator;
import persistence.IPersistence;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class OverpassWebXML implements IOSMSource {
    String url;

    public OverpassWebXML(String url) {
        this.url = url;
    }

    @Override
    public void persistTo(IPersistence persistence) throws IOException {
        InputStream input = new URL(url).openStream();
        OsmIterator iterator = new OsmXmlIterator(input, false);
        AtomicInteger count = new AtomicInteger();

        for (EntityContainer container : iterator)
        {
            count.incrementAndGet();

            if (container.getType() == EntityType.Node) {
                Node node = (Node) container.getEntity();
                persistence.writeNode(node);
            } else if (container.getType() == EntityType.Way) {
                Way way = (Way) container.getEntity();
                persistence.writeWay(way);
            } else if (container.getType() == EntityType.Relation) {
                Relation relation = (Relation) container.getEntity();
                persistence.writeRelation(relation);
            }
        }

        System.out.println("Entities imported: " + count.get());
    }
}
