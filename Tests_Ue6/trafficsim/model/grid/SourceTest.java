package trafficsim.model.grid;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SourceTest{

    private Source s1 = new Source();
    private Road r1;

    @BeforeEach
    void setUp(){
        r1 = new Road(5,1);
        r1.setLength(50);
        s1.setRoad(r1);
        s1.setSourcingFactor(1.0);
    }

    @Test
    void generateVehicleEmpty(){
        s1.generateVehicle();
        boolean test = r1.getVehicle(0) == null;
        assertFalse(test);
    }

    @Test
    void generateWithVehicleInFront(){
        s1.setRoad(r1);
        Truck t1 = new Truck(4);
        r1.addVehicle(t1,0);
        s1.generateVehicle();
        assertEquals(t1, r1.getVehicle(0));
    }
}