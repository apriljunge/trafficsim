package trafficsim.model.traffic;

import java.awt.*;

public class Vehicle {
    protected int speed;
    protected int length;
    protected int width;
    protected Color color;
    protected int accelerationParameter;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAccelerationParameter() {
        return accelerationParameter;
    }

    public void setAccelerationParameter(int accelerationParameter) {
        this.accelerationParameter = accelerationParameter;
    }
}
