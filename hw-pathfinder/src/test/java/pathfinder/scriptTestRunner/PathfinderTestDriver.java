/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;
import graph.Graph;

import java.io.*;
import java.util.*;
import graph.Graph;
import pathfinder.DijkstrasAlgorithm;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {
    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }
    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph.Node<String> n = new Graph.Node<>(nodeName);
        Graph<String, Double> g  = graphs.get(graphName);
        g.addNode(n);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.valueOf(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {

        Graph<String, Double> g = graphs.get(graphName);
        Graph.Node<String> parent = new Graph.Node<>(parentName);
        Graph.Node<String> children = new Graph.Node<>(childName);
        g.addEdge(parent, children, edgeLabel);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to "
                + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String, Double> g = graphs.get(graphName);
        Set<Graph.Node<String>> nodeSet = g.listNodes();
        List<String> nodeList = new ArrayList<>();
        for (Graph.Node<String> n : nodeSet) {
            nodeList.add(n.getData());
        }
        Collections.sort(nodeList);
        StringBuilder myOutput = new StringBuilder(graphName + " contains:");
        for (String s : nodeList) {
            myOutput.append(" ").append(s);
        }
        output.println(myOutput);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String, Double> g = graphs.get(graphName);
        Graph.Node<String> parentNode = new Graph.Node<>(parentName);
        Set<Graph.Edge<String, Double>> childEdge = g.listChildren(parentNode);
        List<String> edgeNames = new ArrayList<>();
        for (Graph.Edge<String, Double> e : childEdge) {
            edgeNames.add(e.getChild().getData() + "(" + e.getLabel() + ")");
        }
        StringBuilder myOutput = new StringBuilder();
        if (edgeNames.size() > 0) {
            Collections.sort(edgeNames);
            for (String s : edgeNames) {
                myOutput.append(" ").append(s);
            }
        }
        output.println("the children of " + parentName + " in " + graphName + " are:" + myOutput);
    }
    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        findPath(graphName, parentName, childName);

    }

    private void findPath(String graphName, String parentName, String childName) {
        Graph<String, Double> graph = graphs.get(graphName);
        Graph.Node<String> parent = new Graph.Node<>(parentName);
        Graph.Node<String> child = new Graph.Node<>(childName);

        if (!graph.containsNode(parent) && !graph.containsNode(child)) {
            output.println("unknown: " + parentName);
            output.println("unknown: " + childName);
        } else if(!graph.containsNode(parent)) {
            output.println("unknown: " + parentName);
        } else if (!graph.containsNode(child)){
            output.println("unknown: " + childName);
        } else {
            output.println("path from " + parentName + " to " + childName + ":");
            Path<String> shortestPaths = DijkstrasAlgorithm.dijkstraPath(parent.getData(), child.getData(), graph);
            if (shortestPaths == null) {
                output.println("no path found");
            } else {
                Iterator<Path<String>.Segment> iterator = shortestPaths.iterator();

                while (iterator.hasNext()) {
                    Path<String>.Segment current = iterator.next();
                    String start = current.getStart();
                    String end = current.getEnd();
                    output.println(start + " to " + end + " with weight " + String.format("%.3f", current.getCost()));

                }
                output.println("total cost: " + String.format("%.3f", shortestPaths.getCost()));
            }
        }

    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
