package trafficsim.model.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.Car;
import trafficsim.model.traffic.Vehicle;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest{

    //transfer_toNewRoad + move_isFirst produce sometimes error

    private Road r1 = new Road(5,1);
    private Road r2 = new Road(5,1);
    private final Car c1 = new Car(4);
    private final Car c2 = new Car(4);

    @BeforeEach
    void setUp(){
        r1.setLength(50);
        r2.setLength(50);
    }

    @Test
    void setLength(){
        assertEquals(50, r1.getLength());
        assertEquals(50, r1.getRoad().length);
    }

    @Test
    void vehicleInteraction(){
        //add car
        r1.addVehicle(c1, 5);
        Vehicle[] array = r1.getRoad();
        assertEquals(c1, array[5]);

        //get car
        assertEquals(c1, r1.getVehicle(5));

        //Remove car
        r1.removeVehicle(c1);
        array = r1.getRoad();
        assertNull(array[5]);
    }

    @Test
    void clearTest(){
        String temp = r1.getRoad().toString();
        r1.clearRoad();
        assertNotEquals(temp, r1.getRoad().toString());

    }

}