package trafficsim.model.geometry;

import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class PointTest extends Vehicle {

    private final Point p1 = new Point(1, 1);

    @Test
    void getDistance() {
        Point p2 = new Point(4,5);
        assertEquals(5.0, p1.getDistance(p2), 0.00001);
    }

    @Test
    void getX() {
        assertEquals(1, p1.getX());
    }

    @Test
    void setX() {
        p1.setX(2);
        assertEquals(2, p1.getX());
    }

    @Test
    void getY() {
        assertEquals(1, p1.getY());
    }

    @Test
    void setY() {
        p1.setX(5);
        assertEquals(5, p1.getX());
    }
}