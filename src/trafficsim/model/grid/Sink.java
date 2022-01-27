package trafficsim.model.grid;

import trafficsim.model.traffic.Vehicle;

public class Sink implements TrafficNode {
    @Override
    public boolean canMoveTo(int position) {
        return true;
    }

    @Override
    public void transferVehicle(Road origin, Vehicle v, int position) {
        origin.removeVehicle(v);
    }
}
