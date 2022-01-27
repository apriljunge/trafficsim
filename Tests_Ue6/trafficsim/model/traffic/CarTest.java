package trafficsim.model.traffic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest extends Vehicle {
    private final Car car = new Car(10);
    private final Truck truck = new Truck(5);
    @Test
    void testSpeed(){
        assertEquals(10, car.getSpeed());
        assertEquals(5, truck.getSpeed());
    }
}