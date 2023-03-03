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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;
import java.util.*;

/**
 * Campus Map represents a Map of University of Washington which allows user to enter in
 * two buildings and help them find the shortest path between those two buildings.
 */
public class CampusMap implements ModelAPI {

    //Abstract Function:
    //buildings is a list that represent all the buildings in the University of Washington with the short name, long name
    //and the coordinate of the building.
    //campusMap is a graph represent the map of University of Washington. The edges are the paths between
    //two buildings, and the node are the coordinate point of the building.

    //Rep invariant:
    //buildings != null
    //campusMap != null
    //all entities of buildings cannot be null, including short names/long names/ and coordinates cannot be null.
    //the distance between two buildings (the distance between two nodes) should be non-negative.

    List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");;
    List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
    Graph<Point, Double> campusMap = new Graph<>();;
    Map<String, String> names = new HashMap<>();
    public static final boolean DEBUG = false;


    /**
     * Construct a campus map using the buildings' name and path data from the given file
     */
    public CampusMap() {
        for (CampusBuilding building : buildings) {
            names.put(building.getShortName(), building.getLongName());
        }
        for (CampusPath p : paths) {
            Graph.Node<Point> building1 = new Graph.Node<>(new Point(p.getX1(), p.getY1()));
            Graph.Node<Point> building2 = new Graph.Node<>(new Point(p.getX2(), p.getY2()));
            campusMap.addNode(building1);
            campusMap.addNode(building2);
            campusMap.addEdge(building1, building2, p.getDistance());
            campusMap.addEdge(building2, building1, p.getDistance());
        }
        checkRep();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        return names.containsKey(shortName);
    }

    public void checkRep() {
        assert (this.campusMap != null);
        assert (this.names != null);
        if (DEBUG) {
            assert (!buildings.contains(null));
            for (Graph.Node<Point> node : campusMap.listNodes()) {
                for (Graph.Edge<Point, Double> edge : campusMap.listChildren(node)) {
                    assert (edge.getLabel() >= 0.0);
                }
            }
        }
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if(!names.containsKey(shortName)) {
            throw new IllegalArgumentException("The short name provided doesn't exist");
        }
        checkRep();
        return names.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        return new HashMap<>(names);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException();
        }
        if (!shortNameExists(startShortName) || ! shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }

        Point startPoint = null;
        Point endPoint = null;
        for (CampusBuilding building : buildings) {
            if (building.getShortName().equals(startShortName)) {
                startPoint = new Point(building.getX(), building.getY());
            }
            if (building.getShortName().equals(endShortName)) {
                endPoint = new Point(building.getX(), building.getY());
            }
        }
        checkRep();
        return DijkstrasAlgorithm.dijkstraPath(startPoint, endPoint, campusMap);
    }
}
