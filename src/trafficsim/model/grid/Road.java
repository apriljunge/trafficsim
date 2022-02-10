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
        v.addPassedRoad(this);
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

    public void applyRules() {
        for (int position = this.road.length - 1; position >= 0; position--) {
            Vehicle v = this.road[position];

            if (v == null) {
                continue;
            }

            v.setBraking(v.isBrakingForNextTimeStep());
            v.setBrakingForNextTimeStep(false);

            accelerate(v);
            brake(v, position);
            dawdle(v, position);
            move(v, position);
            v.setSpeedColor();
        }
    }

    public boolean isEmpty(int lower, int upper) {
        int from = Math.max(0, lower);
        int to = Math.min(upper, this.road.length);

        for (int i = from; i <= to; i++) {
            if (this.road[i] != null) {
                return false;
            }
        }

        return true;
    }

    public int getDistanceToNextCar(int position) {
        for (int i = position + 1; i < this.road.length; i++) {
            if (this.road[i] != null) {
                return i - position - this.road[i].getLength() - (int)Math.ceil(this.maxV / 2);
            }
        }

        return this.maxV + 1;
    }

    private void accelerate(Vehicle v) {
        int speed = v.getSpeed();

        this.dawdleFactor = 0.5;
        if (speed > 0) {
            this.dawdleFactor = 0.1;
        }

        if (speed < this.maxV) {
            v.setSpeed(Math.min(speed + v.getAccelerationParameter(), this.maxV));
        }
    }

    private void brake(Vehicle v, int position) {
        int distanceToNextCar = getDistanceToNextCar(position);
        int distanceToEnd = distanceToEnd(position);
        int initialSpeed = v.getSpeed();

        if (distanceToNextCar <= v.getSpeed()) {
            v.setSpeed(distanceToNextCar - 1);
        }

        if (distanceToEnd < 2 * this.maxV && v.getSpeed() > 1) {
            v.setSpeed(v.getSpeed() - 1);
        }

        v.setBraking(initialSpeed > v.getSpeed());
    }

    private void dawdle(Vehicle v, int position) {
        int speed = v.getSpeed();

        if (isDawdling(position)) {
            if (speed > 0) {
                v.setSpeed(speed - v.getAccelerationParameter());
            }
        }
    }

    private void move(Vehicle v, int position) {
        if (v.getSpeed() <= 0) {
            return;
        }

        if (isFirst(v) && distanceToEnd(position) < v.getSpeed()) {
            transfer(v, position);
        } else {
            moveForward(v, position);
        }
    }

    private boolean isFirst(Vehicle v) {
        for (int i = this.road.length - 1; i >= 0; i--) {
            if (this.road[i] != null) {
                return this.road[i] == v;
            }
        }

        return false;
    }

    private int distanceToEnd(int position) {
        return this.road.length - position - 1;
    }

    private boolean isDawdling(int position) {
        double dawdleFactor = this.dawdleFactor;
        boolean nextVBreakingInNextStep = false;

        for (int i = position; i < road.length; i++) {
            if (road[i] != null) {
                nextVBreakingInNextStep = road[i].isBrakingForNextTimeStep();
                break;
            }
        }

        if (nextVBreakingInNextStep && isInReactionHorizon(position)) {
            dawdleFactor = 0.94;
        }

        return rnd.nextDouble() <= dawdleFactor;
    }

    private void transfer(Vehicle v, int position) {
        int distanceToEnd = distanceToEnd(position);
        int newPosition = v.getSpeed() - distanceToEnd;

        if (roadEnd.canMoveTo(newPosition)) {
            roadEnd.transferVehicle(this, v, newPosition);
        } else {
            v.setSpeed(distanceToEnd);
            moveForward(v, position);
        }
    }

    private void moveForward(Vehicle v, int position) {
        this.road[position] = null;
        this.road[position + v.getSpeed()] = v;
    }

    private boolean isInReactionHorizon(int position) {
        int speed = road[position].getSpeed();
        if (speed == 0)  {
            return false;
        }

        return getDistanceToNextCar(position) / speed < 7;
    }
}
