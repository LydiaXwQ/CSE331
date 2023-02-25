package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Dijkstra's Algorithm Class implements Dijkstra's algorithm that help finding the minimum-cost path
 * between two nodes with a type of D in a graph with all non-negative edge weights with a type of Double.
 */
public class DijkstrasAlgorithm {
    //This class is not an ADT

    /**
     * Returns the minimum-cost path between two given nodes (starting and ending) on a graph
     * using the DFS algorithm.
     * @param starting the start node to find path from
     * @param ending the end node to find path to
     * @param graph the graph that we want to find the shortest path on
     * @return the shortest path between the starting and ending node, return null if no path found.
     * @param <D> the type of the node
     * @throws IllegalArgumentException if the starting/ending/graph is null, or the given starting or
     * ending nodes is not exist in the graph.
     */
    public static <D> Path<D> dijkstraPath (D starting, D ending, Graph<D, Double> graph) {
        if (starting == null || ending == null || graph == null) {
            throw new IllegalArgumentException("starting/ending/graph cannot be null");
        }

        if (!graph.containsNode(new Graph.Node<>(starting)) || !graph.containsNode(new Graph.Node<>(ending))) {
            throw new IllegalArgumentException("node must exist before finding the path");
        }

        Graph.Node<D> dest = new Graph.Node<>(ending);
        Queue<Path<D>> active = new PriorityQueue<>(new pathComparator());
        Set<Graph.Node<D>> finished = new HashSet<>();

        active.add(new Path<>(starting));

        while (!active.isEmpty()) {
            Path<D> minPath = active.remove();
            D minDest = minPath.getEnd();

            Graph.Node<D> minDestNode = new Graph.Node<>(minDest);

            if (minDestNode.equals(dest)) {
                return minPath;
            }

            if (!finished.contains(minDestNode)) {
                for (Graph.Edge<D, Double> e: graph.listChildren(minDestNode)) {
                    if (!finished.contains(e.getChild())) {
                        Path<D> newPath = minPath.extend(e.getChild().getData(), e.getLabel());
                        active.add(newPath);
                    }
                    finished.add(minDestNode);
                }
            }

        }

        return null;
    }

    /**
     * Implement a comparator to two path of unknown type by their cost.
     */
    private static class pathComparator implements Comparator<Path<?>> {
        /**
         * compare two paths of unknown type based on their costs.
         * @param p1 the first path to be compared.
         * @param p2 the second path to be compared.
         * @return -1, 0,or a 1 as the first path's cost is less than,
         * equal to, or greater than the second.
         */
        @Override
        public int compare(Path<?> p1, Path<?> p2) {
            return Double.compare(p1.getCost(), p2.getCost());
        }
    }
}



