package persistence;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Relation;
import de.topobyte.osm4j.core.model.impl.Way;
import gnu.trove.list.array.TLongArrayList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by max on 21.05.17.
 */
public class InMemoryPersistenceTest {
    private IPersistence db;

    @Before
    public void createNewInstance() {
        db = new InMemoryPersistence();
    }

    @Test
    public void writeNodeRetrieveNode() throws Exception {
        Node testNode = new Node(0, 0, 0,
                new ArrayList<>(Arrays.asList(new TestOsmTag("key", "value"))));
        db.writeNode(testNode);

        assertEquals(testNode, db.getNodeById(0));
    }

    @Test
    public void writeWayRetrieveWay() throws Exception {
        Way testWay = new Way(0, new TLongArrayList());
        db.writeWay(testWay);

        assertEquals(testWay, db.getWayById(0));
    }

    @Test
    public void writeRelationRetrieveRelation() throws Exception {
        Relation testRelation = new Relation(0, new ArrayList<>());
        db.writeRelation(testRelation);

        assertEquals(testRelation, db.getRelationById(0));
    }

    @Test
    public void getNeighbors() throws Exception {
        Node a = new Node(0, 0, 0);
        Node b = new Node(1, 1, 1);
        Node c = new Node(2, 2, 2);

        TLongArrayList nodes = new TLongArrayList();
        nodes.add(0);
        nodes.add(1);
        nodes.add(2);

        Way w = new Way(0, nodes);
        db.writeNode(a);
        db.writeNode(b);
        db.writeNode(c);
        db.writeWay(w);

        Set<Node> aNeighbors = new HashSet<>();
        aNeighbors.add(b);

        assertEquals(aNeighbors, db.getNeighbors(a));

        Set<Node> bNeighbors = new HashSet<>();
        bNeighbors.add(a);
        bNeighbors.add(c);

        assertEquals(bNeighbors, db.getNeighbors(b));

        Set<Node> cNeighbors = new HashSet<>();
        cNeighbors.add(b);

        assertEquals(cNeighbors, db.getNeighbors(c));
    }

}