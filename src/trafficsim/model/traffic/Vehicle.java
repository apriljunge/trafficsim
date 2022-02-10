package trafficsim.model.traffic;

import trafficsim.model.grid.Road;

import java.awt.*;
import java.util.ArrayList;

public class Vehicle {
    protected int speed;
    protected int length;
    protected int width;
    protected Color color;
    protected int accelerationParameter;
    protected boolean isBraking;
    protected boolean isBrakingForNextTimeStep;
    protected ArrayList<Road> passedRoads = new ArrayList<>(0);

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

    public void setSpeedColor() {
        float maxV = 5;
        float hue = ((float)this.speed / maxV) * 0.3f;
        this.color = Color.getHSBColor(hue, 1.0f, 0.8f);
    }

    public int getAccelerationParameter() {
        return accelerationParameter;
    }

    public void setAccelerationParameter(int accelerationParameter) {
        this.accelerationParameter = accelerationParameter;
    }

    public ArrayList<Road> getPassedRoads() {
        return passedRoads;
    }

    public void addPassedRoad(Road road) {
        this.passedRoads.add(road);
    }

    public boolean isBraking() {
        return isBraking;
    }

    public void setBraking(boolean braking) {
        isBraking = braking;
    }

    public boolean isBrakingForNextTimeStep() {
        return isBrakingForNextTimeStep;
    }

    public void setBrakingForNextTimeStep(boolean brakingForNextTimeStep) {
        isBrakingForNextTimeStep = brakingForNextTimeStep;
    }
}
