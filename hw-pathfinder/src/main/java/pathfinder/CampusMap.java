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
    List<CampusBuilding> buildings;
    List<CampusPath> paths;
    Graph<Point, Double> campusMap;
    Map<String, String> names;

    /**
     * Construct a campus map using the buildings' name and path data from the given file
     */
    public CampusMap() {
        buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        names = new HashMap<>();
        for (CampusBuilding building : buildings) {
            names.put(building.getShortName(), building.getLongName());
        }
        campusMap = new Graph<>();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return names.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if(!names.containsKey(shortName)) {
            throw new IllegalArgumentException("The short name provided doesn't exist");
        }
        return names.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        return new HashMap<>(names);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException();
        }
        if (!shortNameExists(startShortName) || ! shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }

        for (CampusPath p : paths) {
            Graph.Node<Point> building1 = new Graph.Node<>(new Point(p.getX1(), p.getY1()));
            Graph.Node<Point> building2 = new Graph.Node<>(new Point(p.getX2(), p.getY2()));
            campusMap.addNode(building1);
            campusMap.addNode(building2);
            campusMap.addEdge(building1, building2, p.getDistance());
            campusMap.addEdge(building2, building1, p.getDistance());
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
        return DijkstrasAlgorithm.dijkstraPath(startPoint, endPoint, campusMap);
    }
}
