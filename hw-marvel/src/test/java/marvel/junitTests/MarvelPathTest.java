package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * This class contains tests to ensure the MarvelPath class is correctly implemented
 */
public class MarvelPathTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //this graph
    private Graph graph;
    @Before
    public void setUp() throws Exception {
        graph = MarvelPaths.buildGraph("UWProfessorsCustom.csv");
    }

    /**
     * Test for the file name is null
     */
    @Test (expected = IllegalArgumentException.class)
    public void nullFileName() {
        MarvelPaths.buildGraph(null);
    }

    /**
     * Test for finding path on a null Graph
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPathFindingOnNullGraph() {
        Graph.Node start = new Graph.Node("Schafer");
        Graph.Node end = new Graph.Node("Ri.Anderson");
        MarvelPaths.findShortestPath(start, end, null);
    }

    /**
     * Test for path finding on a null start node
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPathFindingOnNullStart() {
        Graph.Node end = new Graph.Node("Ri.Anderson");
        MarvelPaths.findShortestPath(null, end, graph);
    }

    /**
     * Test for path finding on a null end node
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPathFindingOnNullEnd() {
        Graph.Node start = new Graph.Node("Ri.Anderson");
        MarvelPaths.findShortestPath(start, null, graph);
    }
}
