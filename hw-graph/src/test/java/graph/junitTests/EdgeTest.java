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
    Graph.Node a = myGraph.new Node("a");
    Graph.Node a1 = myGraph.new Node("a");
    Graph.Node b = myGraph.new Node("b");
    Graph.Node c = myGraph.new Node("c");
    Graph.Node d = myGraph.new Node("d");

    //some basic edges
    Graph.Edge e1 = myGraph.new Edge(a, b, "e1");
    Graph.Edge e2 = myGraph.new Edge(a, a, "e2");
    Graph.Edge e3 = myGraph.new Edge(b, c, "e3");
    Graph.Edge e4 = myGraph.new Edge(a, d, "e1");

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
     * Test if two Edges with the same label are the same.
     */
    @Test
    public void testEqual() {
        assertTrue(e1.equals(e4));
        assertFalse(e1.equals(e3));
    }

    /**
     * Test if two equal edges have the same HashCode.
     */
    @Test
    public void testHashCode() {
        assertEquals(e1.hashCode(), e4.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }
}
