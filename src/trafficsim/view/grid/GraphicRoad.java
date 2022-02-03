package trafficsim.view.grid;

import trafficsim.model.grid.Road;
import trafficsim.model.traffic.Vehicle;
import trafficsim.model.geometry.Point;
import java.awt.*;

public class GraphicRoad implements SimulationObject {

    private final Road road;
    private final GraphicPoint start;
    private final GraphicPoint end;
    private int WIDTH;

    public GraphicRoad(Road road, GraphicPoint start, GraphicPoint end) {
        this.road = road;
        this.start = start;
        this.end = end;
        int length = (int) Math.round(start.getPoint().getDistance(end.getPoint()));
        this.road.setLength(length);
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    @Override
    public void paint(Graphics2D g2d, boolean isRudolf) {
        Point startPoint = start.getPoint();
        Point endPoint = end.getPoint();

        g2d.setStroke(new BasicStroke(WIDTH));
        g2d.setColor(Color.BLACK);
        int Dx = (int) Math.abs(startPoint.getX() - endPoint.getX());
        int Dy = (int) Math.abs(startPoint.getY() - endPoint.getY());
        double L = road.getLength(); // real length
        double dx = Dx / L;
        double dy = Dy / L;
        int xDir = startPoint.getX() <= endPoint.getX() ? 1 : -1; // >0 --> Source is west
        int yDir = startPoint.getY() <= endPoint.getY() ? 1 : -1; // >0 --> Source is north
        int xRoot = (int) startPoint.getX();
        int yRoot = (int) startPoint.getY();


        //No painted roads on rudolf grid
        if (!isRudolf){
            g2d.setStroke(new BasicStroke(WIDTH + 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
            g2d.drawLine(xRoot, yRoot, (int) end.getPoint().getX(), (int) end.getPoint().getY());
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
            g2d.drawLine(xRoot, yRoot, (int) end.getPoint().getX(), (int) end.getPoint().getY());
        }

        paintVehicles(xRoot, xDir, dx, yRoot, yDir, dy, g2d);
        start.paint(g2d);
        end.paint(g2d);
    }

    private void paintVehicles(int xRoot, int xDir, double dx, int yRoot, int yDir, double dy, Graphics2D g2d) {
        int l, w, x1, x2, y1, y2;
        for (int i = 0; i < road.getLength(); i++) {
            Vehicle vehicle = road.getVehicle(i);
            if (vehicle != null) {
                l = vehicle.getLength();
                w = vehicle.getWidth();

                x1 = (int) Math.round(xRoot + xDir * dx * (i - l * .5));
                x2 = (int) Math.round(xRoot + xDir * dx * (i + l * .5));
                y1 = (int) Math.round(yRoot + yDir * dy * (i - l * .5));
                y2 = (int) Math.round(yRoot + yDir * dy * (i + l * .5));

                g2d.setStroke(new BasicStroke(w, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                g2d.setColor(vehicle.getColor());
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    @Override
    public void update() {
        road.applyRules();
    }

    @Override
    public void reset() {
        road.clearRoad();
    }
}