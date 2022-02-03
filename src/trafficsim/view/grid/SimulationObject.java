package trafficsim.view.grid;

import java.awt.*;

public interface SimulationObject {
    void paint(Graphics2D g2d, boolean isRudolf);
    void update();
    void reset();
}
