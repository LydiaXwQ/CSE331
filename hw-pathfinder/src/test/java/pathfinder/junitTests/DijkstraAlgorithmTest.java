package pathfinder.junitTests;
import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.Timeout;
import pathfinder.*;
import pathfinder.datastructures.Path;

/**
 * This class contains tests to ensure the DijkstrasAlgorithm class is correctly implemented
 */
public class DijkstraAlgorithmTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    //this graph
    private Graph<String, Double> graph;
    private Graph.Node<String> n1;
    private Graph.Node<String> n2;
    private Graph.Node<String> n3;
    @Before
    public void setUp() throws Exception {
        graph = new Graph<>();
        n1 = new Graph.Node<>("n1");
        n2 = new Graph.Node<>("n2");
        n3 = new Graph.Node<>("n3");
        graph.addNode(n1);
        graph.addNode(n2);
    }

    /**
     * Test for the graph is null
     */
    @Test (expected = IllegalArgumentException.class)
    public void findOnNullGraph() {
        DijkstrasAlgorithm.dijkstraPath(n1.getData(), n2.getData(), null);
    }

    /**
     * Test for path finding on a null parent node
     */
    @Test (expected = IllegalArgumentException.class)
    public void findOnNullParent() {
        DijkstrasAlgorithm.dijkstraPath(null, n2.getData(), graph);
    }

    /**
     * Test for path finding on a null child node
     */
    @Test (expected = IllegalArgumentException.class)
    public void findOnNullChild() {
        DijkstrasAlgorithm.dijkstraPath(n1.getData(), null, graph);
    }

    /**
     * Test for path finding on a start node doesn't exist in graph
     */
    @Test (expected = IllegalArgumentException.class)
    public void startNotExist() {
        DijkstrasAlgorithm.dijkstraPath(n3.getData(), n2.getData(), graph);
    }

    /**
     * Test for path finding on an end node doesn't exist in graph
     */
    @Test (expected = IllegalArgumentException.class)
    public void endNotExist() {
        DijkstrasAlgorithm.dijkstraPath(n1.getData(), n3.getData(), graph);
    }
}
