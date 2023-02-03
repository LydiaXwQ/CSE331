package graph;

import java.util.Set;

/**
 * <b>Graph</b> represents a mutable, directed, labeled graph.
 * A Graph allows to have multiple edges between two different nodes, and node points to itself,
 * but a graph can't have duplicate edges or nodes. (Ex. No two edges have same parent and child nodes
 * have the same label, and no two nodes share the same label.)
 */
public class Graph {
    /**
     * Constructs a new Graph.
     * @spec.effects Construct a new Graph.
     */
    public Graph () {
        throw new RuntimeException("Graph constructor is not yet implemented");
    }

    /**
     * Add the given node to the graph if it doesn't already exist in the graph.
     * @param n Node to be added.
     * @spec.requires n!= null
     * @spec.modifies this
     * @spec.effects add the given node to this graph.
     * @throws IllegalArgumentException if the added node already exist in the graph. (i.e graph
     * already contains a node with name same as the given node).
     */
    public void addNode(Node n) {
        throw new RuntimeException("Graph.addNode() is not yet implemented");
    }

    /**
     * Add the Edge with the given parent Node, child Node and label to the graph if the Edge doesn't already
     * exist in the graph.
     * @param parent the parent node in which the edge points from
     * @param child the child node in which the edge points to
     * @param label the label of the edge
     * @spec.modifies this
     * @spec.effects add the edge with the given parent node, child node and label to this graph.
     * @throws IllegalArgumentException If the added Edge already exist in the Graph
     * (i.e there exist an Edge in graph that has the same parent, child and label as the given),
     * or parentNode or childNode didn't already exist in the graph.
     * @spec.requires parent != null, child != null, label != null
     */
    public void addEdge(Node parent, Node child, String label) {
        throw new RuntimeException("Graph.addEdge() is not yet implemented");
    }

    /**
     * Check if the given Node is contained in the graph.
     * @param n Node to be checked
     * @return true if the given node is in the Graph, false otherwise.
     * @spec.requires n != null
     */
    public boolean containsNode(Node n) {
        throw new RuntimeException("Graph.addEdge() is not yet implemented");
    }

    /**
     * Check if the given Edge is contained in the graph.
     * @param e the Edge to be checked
     * @return true if the given edge is in the Graph, false otherwise.
     * @spec.requires e != null
     */
    public boolean containsEdge(Edge e) {
        throw new RuntimeException("Graph.containsEdge() is not yet implemented");
    }

    /**
     * Returns a set of all Nodes from this Graph.
     * @return set of Nodes in the Graph. If there are no nodes in the graph, return an empty set.
     */
    public Set<Node> listNodes() {
        throw new RuntimeException("Graph.listNodes() is not yet implemented");
    }

    /**
     * Return a set of all Edges from a particular Node.
     * @param n the node needs to get edges from
     * @return a set of all Edges from a particular Node,
     *         if the node doesn't exist in Graph or has no edges, return an empty set.
     * @spec.requires n != null
     */
    public Set<Edge> listChildren(Node n) {
        throw new RuntimeException("Graph.listChildren() is not yet implemented");
    }

    /**
     * Return the number of Nodes in this Graph
     * @return the number of Nodes in the Graph
     */
    public int size() {
        throw new RuntimeException("Graph.size() is not yet implemented");
    }

    /**
     * <b>Node</b> represents an immutable Node in the Graph.
     * Each Node can store some data in it.
     */
    public class Node {
        /**
         * Construct a new Node.
         * @param data data to be stored in the Node.
         * @spec.effects Construct a new Node that stores Node data.
         */
        public Node(String data) {
            throw new RuntimeException("Node constructor is not yet implemented");
        }

        /**
         * Get the data stored in the Node
         * @return the data stored in the Node
         * @spec.requires this != null
         */
        public String getData() {
            throw new RuntimeException("Node.getData() is not yet implemented");
        }
        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent
         * the same Node.
         * @spec.requires obj != null
         */
        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("Edge.equals() is not yet implemented");
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            throw new RuntimeException("Edge.hashCode() is not yet implemented");
        }
    }

    /**
     * <b>Edge</b> represents an immutable Edge that has a parent node, a child node and a label.
     * An Edge is a one-way directed Edge, meaning
     * it points from start, points to end, and marked by a label, l.
     */
    public class Edge {
        /**
         * Construct a new Edge.
         * @param start the start (parent) Node of the Edge
         * @param end the end (child) Node of the Edge
         * @param l the label of the Edge
         * @spec.requires start != null, end != null, label != null
         * @spec.effects Construct a new Edge with the start node start, destination end, and label l
         */
        public Edge(Node start, Node end, String l) {
            throw new RuntimeException("Edge constructor is not yet implemented");
        }

        /**
         * Returns the parent (start) Node of this Edge.
         * @return the parent Node of this Edge.
         * @spec.requires this != null
         */
        public Node getParent() {
            throw new RuntimeException("Edge.getParent() is not yet implemented");
        }

        /**
         * Returns the child (end) Node of this Edge.
         * @return the child Node of this Edge.
         * @spec.requires this != null
         */
        public Node getChild() {
            throw new RuntimeException("Edge.getChild() is not yet implemented");
        }

        /**
         * Returns the label of this Edge.
         *
         * @return the label of this Edge.
         * @spec.requires this != null
         */
        public String getLabel() {
            throw new RuntimeException("Edge.getLabel() is not yet implemented");
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
         * the same Edge.
         * @spec.requires obj != null
         */
        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("Edge.equals() is not yet implemented");
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            throw new RuntimeException("Edge.hashCode() is not yet implemented");
        }

    }
}
