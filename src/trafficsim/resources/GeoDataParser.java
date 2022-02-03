package trafficsim.resources;

import java.awt.Color;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import trafficsim.model.geometry.Point;
import trafficsim.model.grid.*;
import trafficsim.view.grid.*;

public class GeoDataParser {

    private final Map<Integer, Source> sourceMap = new HashMap<>();
    private final Map<Integer, TrafficNode> nodeMap = new HashMap<>();
    private final Map<Integer, GraphicPoint> pointMap = new HashMap<>();
    private final List<SimulationObject> simulationObjects = new ArrayList<>();

    public List<SimulationObject> getSimulationObjects(String preset) {
        simulationObjects.clear();
        try {
            makePoints(preset);
            makeRoads(preset);
        } catch (Exception ignored) {
        }
        return simulationObjects;
    }

    private void makePoints(String preset) throws Exception {
        String path = "src/trafficsim/resources/geoData/" + preset + "/points.json";
        Path fileName = Path.of(path);
        String content = Files.readString(fileName);

        JSONObject obj = new JSONObject(content);
        JSONArray pointArray = obj.getJSONArray("points");

        for (Object pointObject : pointArray) {
            JSONObject json = (JSONObject) pointObject;

            int id = json.getInt("id");
            int x = json.getInt("x");
            int y = json.getInt("y");
            double sourcingFactor = json.getDouble("sourcingFactor");

            String type = json.getString("type");

            Point point = new Point(x, y);
            int RADIUS;
            Color FILL, BORDER;
            switch (type) {
                case "Point":
                    RADIUS = 4;
                    FILL = Color.GRAY;
                    BORDER = Color.BLACK;
                    pointMap.put(id, makeGraphicPoint(point, BORDER, FILL, RADIUS));
                    nodeMap.put(id, new Sink());
                    break;
                case "Joint":
                    RADIUS = 2;
                    FILL = Color.DARK_GRAY;
                    BORDER = Color.DARK_GRAY;
                    pointMap.put(id, makeGraphicPoint(point, BORDER, FILL, RADIUS));
                    nodeMap.put(id, new Junction());
                    break;
                case "Junction":
                    RADIUS = 2;
                    FILL = Color.BLUE;
                    BORDER = Color.BLACK;
                    pointMap.put(id, makeGraphicPoint(point, BORDER, FILL, RADIUS));
                    nodeMap.put(id, new Junction());
                    break;
                case "Source":
                    Source source = new Source();
                    source.setSourcingFactor(sourcingFactor);
                    GraphicSource graphicSource = new GraphicSource(source, point);
                    pointMap.put(id, graphicSource);
                    sourceMap.put(id, source);
                    simulationObjects.add(graphicSource);
                    break;
                default:
                    System.out.println(type + " couldn't be initialized! (" + json + ")");
            }

        }
    }

    private GraphicPoint makeGraphicPoint(Point point, Color BORDER, Color FILL, int RADIUS) {
        GraphicPoint graphicPoint = new GraphicPoint(point);
        graphicPoint.setBORDER(BORDER);
        graphicPoint.setFILL(FILL);
        graphicPoint.setRADIUS(RADIUS);
        return graphicPoint;
    }

    private void makeRoads(String preset) throws Exception {
        String path = "src/trafficsim/resources/geoData/" + preset + "/roads.json";
        Path fileName = Path.of(path);

        String content = Files.readString(fileName);
        JSONObject obj = new JSONObject(content);
        JSONArray roadArray = obj.getJSONArray("roads");

        for (Object roadObject : roadArray) {
            JSONObject json = (JSONObject) roadObject;

            int id = json.getInt("id");
            int width = json.getInt("width");
            int sourceID = json.getInt("source");
            int sinkID = json.getInt("sink");
            //String name = json.getString("name");
            int maxV = json.getInt("maxV");
            double density = json.getDouble("density"); //density omitted
            double dally = json.getDouble("dally");

            Road r = new Road(maxV, dally);
            r.setRoadEnd(nodeMap.get(sinkID));
            GraphicRoad graphicRoad = new GraphicRoad(r, pointMap.get(sourceID), pointMap.get(sinkID));
            graphicRoad.setWIDTH(width);
            simulationObjects.add(graphicRoad);
            if (nodeMap.containsKey(sourceID))
                ((Junction) nodeMap.get(sourceID)).addRoad(r);
            else if (sourceMap.containsKey(sourceID))
                sourceMap.get(sourceID).setRoad(r);

        }
    }
}
