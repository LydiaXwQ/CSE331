package graph.junitTests;
import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * This class contains tests to ensure the Node class is correctly implemented.
 */
public final class NodeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //this graph
    private Graph myGraph = new Graph();

    //some simple basic nodes
    Graph.Node a = new Graph.Node("a");
    Graph.Node a1 = new Graph.Node("a");
    Graph.Node b = new Graph.Node("b");

    /**
     * Test if the data stored in Node is the same as its created.
     */
    @Test
    public void testGetData() {
        assertEquals("a", a.getData());
        assertEquals("b", b.getData());
    }

    /**
     * Test if two nodes are equal to each other.
     */
    @Test
    public void testEquals() {
        assertTrue(a.equals(a1));
        assertFalse(a.equals(b));
    }

    /**
     * Test if two same Node have the same HashCode.
     */
    @Test
    public void testHashCode() {
        assertEquals(a.hashCode(), a1.hashCode());
        assertNotEquals(a.hashCode(), b.hashCode());
    }
}
