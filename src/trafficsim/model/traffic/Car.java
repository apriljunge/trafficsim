package trafficsim.model.traffic;

import java.awt.*;

public class Car extends Vehicle {
    public Car (int speed) {
        super.speed = speed;
        super.length = 9;
        super.width = 3;
        super.color = Color.BLUE;
        super.accelerationParameter = 2;
    }
}
