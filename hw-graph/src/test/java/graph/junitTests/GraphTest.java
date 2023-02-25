package graph.junitTests;
import graph.*;
import org.junit.*;
import org.junit.rules.Timeout;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This class contains tests to ensure the Graph class is correctly implemented
 */
public class GraphTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //this graph
    private Graph<String, String> myGraph = new Graph<>();

    //some nodes
    Graph.Node<String> a = new Graph.Node<>("a");
    Graph.Node<String> a1 = new Graph.Node<>("a");
    Graph.Node<String> b = new Graph.Node<>("b");
    Graph.Node<String> c = new Graph.Node<>("c");
    Graph.Node<String> d = new Graph.Node<>("d");

    //some basic edges
    Graph.Edge<String, String> e1 = new Graph.Edge<>(a, b, "e1");
    Graph.Edge<String, String> e4 = new Graph.Edge<>(a, d, "e1");

    /**
     * Test if the graph size matched with the number of nodes in it.
     */
    @Test
    public void testSize() {
        myGraph = new Graph<>();
        assertEquals(myGraph.size(), 0);
        myGraph.addNode(a);
        assertEquals(myGraph.size(), 1);
    }

    /**
     * Test if the graph has the given node
     */
    @Test
    public void testContainsNode() {
        myGraph = new Graph<>();
        assertFalse(myGraph.containsNode(c));
        myGraph.addNode(b);
        assertTrue(myGraph.containsNode(b));
    }

    /**
     * Test the graph doesn't allow duplicate nodes
     */
    @Test
    public void testAddDuplicateNode() {
        myGraph = new Graph<>();
        myGraph.addNode(a);
        myGraph.addNode(a1);
        assertEquals(myGraph.size(), 1);
        assertTrue(myGraph.containsNode(a));
    }

    /**
     * Test the graph doesn't allow duplicate edges
     */
    @Test
    public void testAddDuplicateEdge() {
        myGraph = new Graph<>();
        myGraph.addNode(a);
        myGraph.addNode(b);
        myGraph.addEdge(a, b, "e1");
        myGraph.addEdge(a, b, "e1");
        Graph.Edge<String, String> expected = new Graph.Edge<>(a, b, "e1");
        assertEquals(myGraph.listChildren(a).size(), 1);
        assertTrue(myGraph.listChildren(a).contains(expected));
    }

    /**
     * Test addEdge doesn't allow no parent
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNoParent() {
        myGraph = new Graph<>();
        myGraph.addNode(b);
        myGraph.addEdge(a, b, "ab");
    }

    /**
     * Test addEdge doesn't allow no children
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNoChildren() {
        myGraph = new Graph<>();
        myGraph.addNode(a);
        myGraph.addEdge(a, b, "ab");
    }

    /**
     * Test if listChildren return empty set when the given node doesn't exist in the graph
     */
    @Test
    public void testListChildrenGivenNotExist() {
        myGraph = new Graph<>();
        Set<Graph.Edge<String, String>> edgesFrom = new HashSet<>();
        myGraph.addNode(a);
        assertEquals(myGraph.listChildren(b), edgesFrom);
    }

    /**
     * Test if listChildren return empty set if the given node doesn't have edges.
     */
    @Test
    public void testListChildrenGivenNoEdge() {
        myGraph = new Graph<>();
        Set<Graph.Edge<String, String>> edgesFrom = new HashSet<>();
        myGraph.addNode(a);
        assertEquals(myGraph.listChildren(a), edgesFrom);
    }

    /**
     * Test if listNode return an empty set if there are no nodes in the graph.
     */
    @Test
    public void testListNodeEmpty() {
        myGraph = new Graph<>();
        Set<Graph.Node<String>> expected = new HashSet<>();
        assertEquals(myGraph.listNodes(), expected);
    }
}
