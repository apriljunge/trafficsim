package trafficsim.model.grid;

import trafficsim.model.traffic.Vehicle;

import java.util.ArrayList;
import java.util.Random;

public class Junction implements TrafficNode {
    private ArrayList<Road> outgoingRoads = new ArrayList<>(0);
    private ArrayList<Road> freeRoads = new ArrayList<>(0);
    private Random rnd = new Random();

    @Override
    public boolean canMoveTo(int position) {
        findFreeRoads(position);
        return freeRoads.size() > 0;
    }

    @Override
    public void transferVehicle(Road origin, Vehicle v, int position) {
        if (freeRoads.size() > 0) {
            int roadNumber = rnd.nextInt(freeRoads.size());
            freeRoads.get(roadNumber).addVehicle(v, position);
            origin.removeVehicle(v);
        }
    }

    public void addRoad(Road r) {
        this.outgoingRoads.add(r);
    }

    private void findFreeRoads(int position) {
        freeRoads.clear();
        for (Road road: this.outgoingRoads) {
            if (road.getDistanceToNextCar(0) >= position && road.getVehicle(0) == null) {
                freeRoads.add(road);
            }
        }
    }
}
