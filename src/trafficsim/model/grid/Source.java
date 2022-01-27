package trafficsim.model.grid;

import trafficsim.model.traffic.Car;
import trafficsim.model.traffic.Truck;
import trafficsim.model.traffic.Vehicle;

import java.util.Random;

public class Source {
    private Road road;
    private double sourcingFactor;
    private Random rnd = new Random();

    public Source() {
    }

    public Source(Road road) {
        this.road = road;
    }

    public void generateVehicle() {
        int speed = rnd.nextInt(this.road.getMaxV()) + 1;
        Vehicle v;

        if (road.isEmpty(0, speed) && rnd.nextDouble() < this.sourcingFactor) {
            if (rnd.nextDouble() <= 0.1) {
                v = new Truck(speed);
            } else {
                v = new Car(speed);
            }
            road.addVehicle(v, 0);
        }
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public void setSourcingFactor(double sourcingFactor) {
        this.sourcingFactor = sourcingFactor;
    }
}
