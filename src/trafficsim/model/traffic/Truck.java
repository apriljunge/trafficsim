package trafficsim.model.traffic;

import java.awt.*;

public class Truck extends Vehicle {
    public Truck (int speed) {
        super.speed = speed;
        super.length = 20;
        super.width = 5;
        super.color = Color.BLACK;
        super.accelerationParameter = 1;
    }
}
