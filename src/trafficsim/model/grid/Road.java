package trafficsim.model.grid;

import trafficsim.model.traffic.Vehicle;

import java.util.Arrays;
import java.util.Random;

public class Road {
    private int maxV;
    private int length;
    private double dawdleFactor;
    private Random rnd = new Random();
    private Vehicle[] road;
    private TrafficNode roadEnd;

    public Road(int maxV, double dawdleFactor) {
        this.maxV = maxV;
        this.dawdleFactor = dawdleFactor;
    }

    public int getMaxV() {
        return maxV;
    }

    public void setMaxV(int maxV) {
        this.maxV = maxV;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
        this.road = new Vehicle[length];
    }

    public double getDawdleFactor() {
        return dawdleFactor;
    }

    public void setDawdleFactor(double dawdleFactor) {
        this.dawdleFactor = dawdleFactor;
    }

    public Vehicle[] getRoad() {
        return road;
    }

    public void setRoad(Vehicle[] road) {
        this.road = road;
    }

    public TrafficNode getRoadEnd() {
        return roadEnd;
    }

    public void setRoadEnd(TrafficNode roadEnd) {
        this.roadEnd = roadEnd;
    }

    public void addVehicle(Vehicle v, int position) {
        this.road[position] = v;
    }

    public void removeVehicle(Vehicle v) {
        int position = Arrays.asList(this.road).indexOf(v);
        this.road[position] = null;
    }

    public void clearRoad() {
        this.road = new Vehicle[this.length];
    }

    public Vehicle getVehicle(int position) {
        return this.road[position];
    }
}
