package trafficsim.view.grid;

import trafficsim.model.geometry.Point;
import trafficsim.model.grid.Source;

import java.awt.*;

public class GraphicSource extends GraphicPoint implements SimulationObject {
    private Source source;

    public GraphicSource(Source source, Point point) {
        super(point);
        this.source = source;
        super.RADIUS = 4;
        super.FILL = Color.RED;
        super.BORDER = Color.BLACK;
    }

    @Override
    public void paint(Graphics2D g2d, boolean isRudolf) {

    }

    @Override
    public void update() {
        this.source.generateVehicle();
    }

    @Override
    public void reset() {

    }
}
