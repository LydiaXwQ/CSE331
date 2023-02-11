package graph.junitTests;
import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * This class contains tests to ensure the Edge class is correctly implemented
 */
public class EdgeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //this graph
    private Graph myGraph = new Graph();

    //some nodes
    Graph.Node a = new Graph.Node("a");
    Graph.Node a1 = new Graph.Node("a");
    Graph.Node b = new Graph.Node("b");
    Graph.Node c = new Graph.Node("c");
    Graph.Node d = new Graph.Node("d");

    //some basic edges
    Graph.Edge e1 = new Graph.Edge(a, b, "e1");
    Graph.Edge e2 = new Graph.Edge(a, a, "e2");
    Graph.Edge e3 = new Graph.Edge(b, c, "e3");
    Graph.Edge e4 = new Graph.Edge(a, d, "e1");
    Graph.Edge e5 = new Graph.Edge(a, b, "e1");

    /**
     * Test if the parent node of the edge can be accessed.
     */
    @Test
    public void testGetParent() {
        assertEquals(e1.getParent(), a);
        assertEquals(e2.getParent(), a);
    }

    /**
     * Test if the child node of the edge can be accessed.
     */
    @Test
    public void testGetChild() {
        assertEquals(e1.getChild(), b);
        assertEquals(e2.getChild(), a);
    }

    /**
     * Test if the label of an edge can be accessed.
     */
    @Test
    public void testGetLabel() {
        assertEquals(e1.getLabel(),"e1");
        assertEquals(e2.getLabel(),"e2");
        assertEquals(e3.getLabel(), "e3");
    }

    /**
     * Test two Edges are equal to each other only when they have same parent, child and label.
     */
    @Test
    public void testEqual() {
        assertFalse(e1.equals(e4));
        assertFalse(e1.equals(e3));
        assertTrue(e1.equals(e5));
    }

    /**
     * Test if two equal edges have the same HashCode.
     */
    @Test
    public void testHashCode() {
        assertNotEquals(e1.hashCode(), e4.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }
}
