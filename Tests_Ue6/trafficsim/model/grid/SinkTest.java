package trafficsim.model.grid;

import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.Car;
import trafficsim.model.traffic.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class SinkTest extends Vehicle {
    private final Sink sink = new Sink();

    @Test
    void canMoveTo() {
        assertTrue(sink.canMoveTo(5));
    }

    @Test
    void transferVehicle() {
        Road r1 = new Road(5,5);
        r1.setLength(50);
        Car c1 = new Car(5);
        r1.addVehicle(c1,5);

        sink.transferVehicle(r1,c1,5);
        assertNull(r1.getVehicle(5));
    }
}