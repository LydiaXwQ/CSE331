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

package campuspaths;

import campuspaths.utils.CORSFilter;
import campuspaths.utils.Info;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        CampusMap map = new CampusMap();
        Spark.get("/find-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startString = request.queryParams("start");
                String endString = request.queryParams("end");
                if(startString == null || endString == null) {
                    Spark.halt(400, "must have start and end");
                }
                Path<Point> shortestPath = map.findShortestPath(startString, endString);

                Gson gson = new Gson();
                return gson.toJson(shortestPath);
            }
        });

        Spark.get("/building-names", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                //return a JsonString of all building names
                return gson.toJson(map.buildingNames());
            }
        });
    }

}
