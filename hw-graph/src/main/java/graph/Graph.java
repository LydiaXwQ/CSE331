package graph;

import java.util.*;

/**
 * <b>Graph</b> represents a mutable, directed, labeled graph.
 * A Graph allows to have multiple edges between two different nodes, and node points to itself,
 * but a graph can't have duplicate edges or nodes. (Ex. No two edges have same parent and child nodes
 * have the same label, and no two nodes share the same label.)
 * D is the node type and L is the Edge Label type.
 */
public class Graph<D, L> {

    // Abstract Function:
    // Graph stores pairs of Parent Node and the outgoing Edges from that Node.
    // (i.e one object in graph can be represented by <parentNode, edgesFromParent>
    // in which parentNode is a Node object from this and edgesFromParent is a set of Edge
    // objects from this).
    //
    // graph.size() represents the number of nodes in a graph.
    // Graph can have self pointed Node meaning that there exist an Edge in the
    // edgesFromParent such that Edge.getChild().equals(parentNode).
    // If the there are no entities in graph, graph represents an empty graph.

    // Rep Invariant:
    // Graph cannot be null and all entities (keys/values and every Node and Edge) in graph cannot be null.
    // No two edges have the same parent and child share the same label.
    // No two nodes share the same label.
    // An Edge cannot exist if its parent or child doesn't already exist in the graph.

    // The switch for checkRep
    private final static boolean DEBUG = false;

    // The holder of nodes and corresponding edges, which represents the graph
    private Map<Node<D>, Set<Edge<D, L>>> graph;

    // Throws an exception if the representation invariant is violated.
    private void checkRep() {
        assert (graph != null) : "graph cannot be null!";
        Set<Graph.Node<D>> nodes = graph.keySet();
        if (DEBUG) {
            for (Node<D> n : nodes) {
                assert (n != null) : "Node cannot be null";
                Set<Graph.Edge<D, L>> edgeSet = graph.get(n);
                Set<Graph.Edge<D, L>> visitedEdge = new HashSet<>();
                for(Graph.Edge<D, L> e : edgeSet) {
                    assert (e != null) : "Edge cannot be null";
                    assert (graph.containsKey(e.getParent())) : "Parent node must exist before Edge was added";
                    assert (graph.containsKey(e.getChild())) : "Child node must exist before Edge was added";
                    assert (!visitedEdge.contains(e)) : "Duplicate edge are not allowed";
                    visitedEdge.add(e);
                }
            }
        }
    }


    /**
     * Constructs a new Graph.
     * @spec.effects Construct a new Graph.
     */
    public Graph () {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Add the given node to the graph if it doesn't already exist in the graph.
     * @param n Node to be added.
     * @spec.modifies this
     * @spec.effects add the given node to this graph.
     * @spec.requires n != null
     */
    public void addNode(Node<D> n) {
        checkRep();
        if (!graph.containsKey(n) && n != null) {
            graph.put(n, new HashSet<>());
        }
        checkRep();
    }

    /**
     * Add the Edge with the given parent Node, child Node and label to the graph if the Edge doesn't already
     * exist in the graph.
     * @param parent the parent node in which the edge points from
     * @param child the child node in which the edge points to
     * @param label the label of the edge
     * @spec.modifies this
     * @spec.effects add the edge with the given parent node, child node and label to this graph.
     * @throws IllegalArgumentException if added Edge's parentNode or childNode didn't already exist in the graph.
     * @spec.requires parent != null, child != null, label != null.
     */
    public void addEdge(Node<D> parent, Node<D> child, L label) {
        checkRep();
        if (!containsNode(parent) || !containsNode(child)) {
            throw new IllegalArgumentException("Parent and Child must have existed!");
        }

        Set<Edge<D, L>> fromParent = graph.get(parent);
        Edge<D, L> edge = new Edge<>(parent, child, label);

//        if (fromParent.contains(edge)) {
//            throw new IllegalArgumentException("Edge to be added already exist in the graph!");
//        }

        fromParent.add(edge);
        checkRep();
    }

    /**
     * Check if the given Node is contained in the graph.
     * @param n Node to be checked
     * @return true if the given node is in the Graph, false otherwise.
     * @spec.requires n != null
     */
    public boolean containsNode(Node<D> n) {
        checkRep();
        return graph.containsKey(n);
    }

    /**
     * Returns a set of all Nodes from this Graph.
     * @return set of Nodes in the Graph. If there are no nodes in the graph, return an empty set.
     */
    public Set<Node<D>> listNodes() {
        checkRep();
        return Collections.unmodifiableSet(graph.keySet());
    }

    /**
     * Return a set of all Edges from a particular Node.
     * @param n the node needs to get edges from
     * @return a set of all Edges from a particular Node,
     *         if the node doesn't exist in Graph or has no edges, return an empty set.
     * @spec.requires n != null
     */
    public Set<Edge<D,L>> listChildren(Node<D> n) {
        checkRep();
        if(!graph.containsKey(n) || graph.get(n).isEmpty()) {
            return new HashSet<>();
        }
        checkRep();
        return Collections.unmodifiableSet(graph.get(n));
    }

    /**
     * Return the number of Nodes in this Graph
     * @return the number of Nodes in the Graph
     */
    public int size() {
        checkRep();
        return graph.size();
    }

    /**
     * <b>Node</b> represents an immutable Node in the Graph.
     * Each Node can store some data in it.
     */
    public static class Node<D> {
        //Abstract Function:
        //A Node represent a Node in Graph
        //Node's data is Node's label

        //Abstract inv:
        //Node's data can never be null
        private final D data;

        private void checkRep() {
            assert (this.data != null): "Node data cannot be null";
        }
        /**
         * Construct a new Node.
         * @param data data to be stored in the Node.
         * @spec.effects Construct a new Node that stores Node data.
         * @spec.requires data != null
         */
        public Node(D data) {
            this.data = data;
            checkRep();
        }

        /**
         * Get the data stored in the Node
         * @return the data stored in the Node
         * @spec.requires this != null
         */
        public D getData() {
            checkRep();
            return this.data;
        }

        /**
         * Standard equality operation.
         *
         * @param o the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent
         * the same Node.
         * @spec.requires obj != null
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) {
                return false;
            }
            Node<?> n = (Node<?>) o;
            return this.data.equals(n.data);
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            return 31 * data.hashCode();
        }
    }

    /**
     * <b>Edge</b> represents an immutable Edge that has a parent node, a child node and a label.
     * An Edge is a one-way directed Edge, meaning
     * it points from start, points to end, and marked by a label, l.
     */
    public static class Edge<D, L> {
        // Abstract Function:
        // start represents the parent node where the edge points from
        // end represents the child node where the edge points to
        // l represents the edge label

        // Rep Inv:
        // start != null, end != null, l != null

        private final Node<D> start;
        private final Node<D> end;
        private final L l;

        private void checkRep() {
            if (DEBUG) {
                assert (this.start != null) : "Parent node cannot be null";
                assert (this.end != null) : "Child node cannot be null";
                assert (this.l != null) : "Edge label cannot be null";
            }
        }

        /**
         * Construct a new Edge.
         * @param start the start (parent) Node of the Edge
         * @param end the end (child) Node of the Edge
         * @param l the label of the Edge
         * @spec.requires start != null, end != null, label != null
         * @spec.effects Construct a new Edge with the node from start, destination end, and label l
         */
        public Edge(Node<D> start, Node<D> end, L l) {
            this.start = start;
            this.end = end;
            this.l = l;
            checkRep();
        }

        /**
         * Returns the parent (start) Node of this Edge.
         * @return the parent Node of this Edge.
         * @spec.requires this != null
         */
        public Node<D> getParent() {
            checkRep();
            return this.start;
        }

        /**
         * Returns the child (end) Node of this Edge.
         * @return the child Node of this Edge.
         * @spec.requires this != null
         */
        public Node<D> getChild() {
            checkRep();
            return this.end;
        }

        /**
         * Returns the label of this Edge.
         *
         * @return the label of this Edge.
         * @spec.requires this != null
         */
        public L getLabel() {
            checkRep();
            return l;
        }

        /**
         * Standard equality operation.
         *
         * @param o the object to be compared for equality
         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
         * the same Edge.
         * @spec.requires obj != null
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Edge)) {
                return false;
            }
            Edge<?, ?> e = (Edge<?, ?>) o;
            return this.start.equals(e.start) && this.end.equals(e.end) && this.l.equals(e.l);
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            return 31 * start.hashCode() + end.hashCode() + l.hashCode();
        }

    }
}
