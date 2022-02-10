package trafficsim.model.grid;

import trafficsim.model.traffic.Vehicle;

import java.util.ArrayList;
import java.util.Random;

public class Junction implements TrafficNode {
    private ArrayList<Road> outgoingRoads = new ArrayList<>(0);
    private ArrayList<Road> freeRoads = new ArrayList<>(0);
    private ArrayList<Road> freeNotPassedRoads = new ArrayList<>(0);
    private Random rnd = new Random();

    @Override
    public boolean canMoveTo(int position) {
        findFreeRoads(position);
        return freeRoads.size() > 0;
    }

    @Override
    public void transferVehicle(Road origin, Vehicle v, int position) {
        ArrayList<Road> roads = this.freeRoads;

        findfreeNotPassedRoads(v);
        if (this.freeNotPassedRoads.size() > 0) {
            roads = this.freeNotPassedRoads;
        }

        if (roads.size() > 0) {
            int roadNumber = rnd.nextInt(roads.size());
            roads.get(roadNumber).addVehicle(v, position);
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

    private void findfreeNotPassedRoads(Vehicle v) {
        freeNotPassedRoads.clear();
        for (Road road: this.freeRoads) {
            if (!v.getPassedRoads().contains(road)) {
                freeNotPassedRoads.add(road);
            }
        }
    }
}
