package trafficsim.view.grid;

import trafficsim.model.geometry.Point;

import java.awt.*;

public class GraphicPoint {
    private trafficsim.model.geometry.Point point;
    protected Stroke STROKE = new BasicStroke();
    protected int RADIUS;
    protected Color FILL;
    protected Color BORDER;

    public GraphicPoint(trafficsim.model.geometry.Point point) {
        this.point = point;
    }

    public void setSTROKE(Stroke STROKE) {
        this.STROKE = STROKE;
    }

    public void setRADIUS(int RADIUS) {
        this.RADIUS = RADIUS;
    }

    public void setFILL(Color FILL) {
        this.FILL = FILL;
    }

    public void setBORDER(Color BORDER) {
        this.BORDER = BORDER;
    }

    public Point getPoint() {
        return point;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(this.FILL);
        System.out.println(this.FILL);
        g2d.fillOval((int) point.getX() -  this.RADIUS, (int) point.getY() - this.RADIUS, 2 * this.RADIUS, 2 * this.RADIUS);
        g2d.setColor(this.BORDER);
        g2d.setStroke(this.STROKE);
        g2d.drawOval((int) point.getX() -  this.RADIUS, (int) point.getY() - this.RADIUS, 2 * this.RADIUS, 2 * this.RADIUS);
    }
}
