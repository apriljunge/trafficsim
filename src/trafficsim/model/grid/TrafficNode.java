package trafficsim.model.grid;

import trafficsim.model.traffic.Vehicle;

public interface TrafficNode {
    public boolean canMoveTo(int position);
    public void transferVehicle(Road origin, Vehicle v, int position);
}
