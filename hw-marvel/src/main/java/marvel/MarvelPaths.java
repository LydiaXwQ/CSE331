package marvel;

import graph.Graph;

import java.util.*;

/**
 * This class represents a directed, labeled graph that models the social network among characters
 * in Marvel comic books.
 * Each node in the graph represents a character, and the edge between two nodes represents the book they share.
 * (i.e Character1 to Character2 with edge name "SuperHero" meaning Char1 appears in the book "SuperHero"
 * that Char2 also appears in, and vice versa).
 */
public class MarvelPaths {
    //This class is not an ADT

    /**
     * Construct a directed, labeled Graph according to the data in the given file.
     * Graph nodes are characters, Graph edges are book name that two characters share.
     * @param fileName the file that want to be used to create the graph.
     * @return a directed labeled graph from the information of given file.
     *         return empty graph if the graph is empty.
     * @throws IllegalArgumentException if the fileName is null.
     */
    public static Graph<String, String> buildGraph(String fileName) {
        Graph<String,String> graph = new Graph<>();
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null.");
        }

        Map<String, ArrayList<String>> container = MarvelParser.parseData(fileName);
        for (String bookName : container.keySet()) {
            ArrayList<String> character = container.get(bookName);
            for (String s : character) {
                Graph.Node<String> node = new Graph.Node<>(s);
                if (!graph.containsNode(node)) {
                    graph.addNode(node);
                }
            }
            for (int i = 0; i < character.size(); i++) {
                for (int j = i + 1; j < character.size(); j++) {
                    graph.addEdge(new Graph.Node<>(character.get(i)), new Graph.Node<>(character.get(j)), bookName);
                    graph.addEdge(new Graph.Node<>(character.get(j)), new Graph.Node<>(character.get(i)), bookName);
                }
            }
        }
        return graph;
    }

    /**
     * The method finds the shortest path between the given starting character and the ending character in the Graph
     * using a BFS algorithm.
     * @param starting starting character which the search starts from
     * @param ending ending character which the search ends on
     * @param graph the graph we want to find the shortest path on
     * @return the shortest path between two characters, null if there is no path between two characters.
     * @throws  IllegalArgumentException if the given starting or ending or the graph is null, or node to be
     * finding paths on doesn't exist in the graph.
     */
    public static ArrayList<Graph.Edge<String, String>> findShortestPath(Graph.Node<String> starting, Graph.Node<String> ending, Graph<String, String> graph) {
        if (starting == null || ending == null || graph == null) {
            throw new IllegalArgumentException("starting/ending/graph cannot be null");
        }

        if (!graph.containsNode(starting) || !graph.containsNode(ending)) {
            throw new IllegalArgumentException("node must exist before finding the path");
        }
        Graph.Node<String> start = starting;
        Graph.Node<String> dest = ending;
        Queue<Graph.Node<String>> queue = new LinkedList<>();
        Map<Graph.Node<String>, ArrayList<Graph.Edge<String, String>>> map = new HashMap<>();

        queue.add(start);
        map.put(start, new ArrayList<>());
        while (!queue.isEmpty()) {
            Graph.Node<String> parent = queue.remove();
            if (parent.equals(dest)) {
                return map.get(parent);
            }

            Set<Graph.Edge<String, String>> sortedListChildren = new TreeSet<>(new edgeComparator());
            sortedListChildren.addAll(graph.listChildren(parent));
            for (Graph.Edge<String, String> e : sortedListChildren) {
                Graph.Node<String> child = e.getChild();
                if (!map.containsKey(child)) {
                    ArrayList<Graph.Edge<String, String>> parentPath = new ArrayList<>(map.get(parent)); //no rep exposure
                    map.put(child, parentPath);
                    map.get(child).add(e);
                    queue.add(child);

                }
            }
        }

        return null;
    }

    /**
     * Compares two edges for order. Returns a negative number if the edge is less lexicographical in childNode's name,
     * returns a positive number if the edge is greater lexicographical in childNode's name.
     * If childNode are the same, returns a negative number if the edge is less lexicographical in edge label
     * returns positive if it is greater lexicographical in label. Returns 0 when two edges are same in both
     * child's name and edge label.
     */
    private static class edgeComparator implements Comparator<Graph.Edge<String, String>> {
        /**
         * compare two edges based on their child nodes and labels lexicographically.
         * @param e1 the first edge to be compared.
         * @param e2 the second edge to be compared.
         * @return a negative integer, zero,or a positive integer as the first edge is lexicographically less than,
         * equal to, or greater than the second.x
         */
        @Override
        public int compare(Graph.Edge<String, String> e1, Graph.Edge<String, String> e2) {
            //compare child node, if they are different, sort in increasing order
            if (!e1.getChild().equals(e2.getChild())) {
                return e1.getChild().getData().compareTo(e2.getChild().getData());
            } else {
                return e1.getLabel().compareTo(e2.getLabel());
            }
        }
    }

    /**
     * Allows the user to find the shortest path between the two marvel character they entered.
     * If there isn't a path between two character, then will tell them no path found.
     * The program will could play for many rounds until the user entered "no"/ "NO".
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        Graph<String, String> graph = buildGraph("marvel.csv");
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Marvel Path finder!");
        System.out.println("Here I can help you to find the shortest relationship between two characters " +
                "if you tell me their names!");
        System.out.println("Input names in all CAPS connected with a dash. (i.e CAPTAIN-AMERICA)");
        System.out.println();

        boolean playAgain = true;
        while (playAgain) {
            System.out.print("Now enter your first character: ");
            String first = input.nextLine();
            System.out.print("Now enter your second character: ");
            String second = input.nextLine();
            Graph.Node<String> parent = new Graph.Node<>(first);
            Graph.Node<String> child = new Graph.Node<>(second);
            if (!graph.containsNode(parent) && !graph.containsNode(child)) {
                System.out.println("Both characters you entered doesn't exist in the marvel universe!");
            } else if (!graph.containsNode(parent)) {
                System.out.println("The first character you entered doesn't exist in the marvel universe!");
            } else if (!graph.containsNode(child)) {
                System.out.println("The second character you entered doesn't exist in the marvel universe!");
            } else {
                System.out.println("path from " + first + " to " + second + ":");
                List<Graph.Edge<String, String>> shortestPaths = MarvelPaths.findShortestPath(parent, child, graph);
                if (shortestPaths == null) {
                    System.out.println("no path found");
                } else {
                    for (Graph.Edge<String, String> e : shortestPaths) {
                        System.out.println(e.getParent().getData() + " -> " + e.getChild().getData() + " in book of " + e.getLabel());
                    }
                }
            }
            System.out.print("Do you want to play again? ");
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("no")) {
                playAgain = false;
                System.out.println();
                System.out.println("Thanks for using!");
            }
        }
    }
}
