package trafficsim.model.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.Car;
import trafficsim.model.traffic.Vehicle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JunctionTest extends Vehicle {

    private Road r1 = new Road(5, 1);
    private Road r2 = new Road(5, 1);

    private Junction j1 = new Junction();

    private final Car c1 = new Car(4);
    private final Car c2 = new Car(4);

    @BeforeEach
    void setUp() {
        r1.setLength(50);
        r2.setLength(50);
        j1.addRoad(r1);
        j1.addRoad(r2);
    }

    @Test
    void canMoveTo_emptyRoads() {
        //road2 still empty
        r1.addVehicle(c1, 2);
        assertTrue(j1.canMoveTo(5));
    }

    @Test
    void canMoveTo_busyRoads() {
        r1.addVehicle(c1, 2);
        r2.addVehicle(c2, 2);
        assertFalse(j1.canMoveTo(5));
    }

    @Test
    void canMoveTo_busyRoadsEdgecase() {
        c2.setLength(2);
        r2.setMaxV(2);
        r1.addVehicle(c1, r1.getLength()-1);
        r2.addVehicle(c2, 6);

        Junction j2 = new Junction();
        j2.addRoad(r2);
        assertFalse(j2.canMoveTo(4));
        assertTrue(j2.canMoveTo(3));
    }


    @Test
    void transferVehicle(){
        r1.addVehicle(c1, 2);
        j1.canMoveTo(5);
        j1.transferVehicle(r1, c1, 5);

        assertNull(r1.getVehicle(2));
        assertEquals(c1, r2.getVehicle(5));
    }
}