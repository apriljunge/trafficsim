package trafficsim.model.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trafficsim.model.traffic.Car;
import trafficsim.model.traffic.Vehicle;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest{

    //System.out.println(Arrays.toString(r1.getRoad()));
    //use to get car positions in Array

    private Road r1 = new Road(5,1);
    private Road r2 = new Road(5,1);
    private final Car c1 = new Car(4);
    private final Car c2 = new Car(4);

    @BeforeEach
    void setUp(){
        r1.setLength(50);
        r2.setLength(50);
    }

    @Test
    void setLength(){
        assertEquals(50, r1.getLength());
        assertEquals(50, r1.getRoad().length);
    }

    @Test
    void vehicleInteraction(){
        //add car
        r1.addVehicle(c1, 5);
        Vehicle[] array = r1.getRoad();
        assertEquals(c1, array[5]);

        //get car
        assertEquals(c1, r1.getVehicle(5));

        //Remove car
        r1.removeVehicle(c1);
        array = r1.getRoad();
        assertNull(array[5]);
    }

    @Test
    void clearTest(){
        r1.addVehicle(c1, 0);
        r1.addVehicle(c2, 49);
        r1.clearRoad();
        Vehicle[] expected=new Vehicle[r1.getLength()];
        assertArrayEquals(expected, r1.getRoad());
    }

    @Test
    void isEmpty(){
        r1.addVehicle(c1, 5);
        assertTrue(r1.isEmpty(0, 4));
        assertFalse(r1.isEmpty(0, 10));
	assertFalse(r1.isEmpty(0, 5));

    }

    @Test
    void accelerate() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("accelerate", Vehicle.class);
        method.setAccessible(true);
        method.invoke(r1, c1);
        assertEquals(5, c1.getSpeed());
        method.invoke(r1, c1);
        assertEquals(5, c1.getSpeed());
    }

    @Test
    void distanceToEnd() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("distanceToEnd", int.class);
        method.setAccessible(true);
        assertEquals(44, (int) method.invoke(r1, 5));
    }

    @Test
    void isFirst() throws Exception{
        r1.addVehicle(c1,5);
        r1.addVehicle(c2,3);
        Method method = r1.getClass().getDeclaredMethod("isFirst", Vehicle.class);
        method.setAccessible(true);
        assertTrue((boolean) method.invoke(r1, c1));
        assertFalse((boolean) method.invoke(r1, c2));
    }

    @Test
    void moveForward() throws Exception{
        r1.addVehicle(c1,1);
        Method method = r1.getClass().getDeclaredMethod("moveForward", Vehicle.class, int.class);
        method.setAccessible(true);
        method.invoke(r1, c1,1);
        assertNull(r1.getVehicle(1));
        assertEquals(c1, r1.getVehicle(5));
    }

    @Test
    void moveForward_withNoSpeed() throws Exception{
        r1.addVehicle(c1,1);

        Method method = r1.getClass().getDeclaredMethod("moveForward", Vehicle.class, int.class);
        method.setAccessible(true);

        c1.setSpeed(0);
        r1.addVehicle(c1,45);

        method.invoke(r1, c1,45);
        assertEquals(c1, r1.getVehicle(45));
    }

    @Test
    void getDistanceToNextCar() throws Exception{
        r1.addVehicle(c1,25);
        Method method = r1.getClass().getDeclaredMethod("getDistanceToNextCar", int.class);
        method.setAccessible(true);
        assertEquals(6, (int) method.invoke(r1, 26));   //maxV+1
        assertEquals(4, (int) method.invoke(r1, 10));  //25-10-9-2
    }

    @Test
    void brake() throws Exception{
        r1.addVehicle(c2,25);
        Method method = r1.getClass().getDeclaredMethod("brake", Vehicle.class, int.class);
        method.setAccessible(true);
        method.invoke(r1, c1,10);
        assertEquals(3, c1.getSpeed());   //Check brake due to car in front

        method.invoke(r1, c1,26);
        assertEquals(3, c1.getSpeed());  //Check brake due to road end
    }

    @Test
    void dawdle() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("dawdle", Vehicle.class);
        method.setAccessible(true);

        //dawdlefactor 0 -> no dawdling
        r1.setDawdleFactor(0);
        method.invoke(r1, c1);
        assertEquals(4, c1.getSpeed());

        //dawdle factor 1 -> always dawdling
        r1.setDawdleFactor(1);
        method.invoke(r1, c1);
        assertEquals(3, c1.getSpeed());
    }

    @Test
    void transfer_ToNewRoad() throws Exception{
        Junction j1 = new Junction();
        j1.addRoad(r2);
        r1.setRoadEnd(j1);
        r1.addVehicle(c1,48);

        Method method = r1.getClass().getDeclaredMethod("transfer", Vehicle.class, int.class);
        method.setAccessible(true);

        method.invoke(r1, c1,48);

        assertEquals(c1, r2.getVehicle(3));
    }

    @Test
    void transfer_OnSameRoad() throws Exception{
        Junction j1 = new Junction();
        j1.addRoad(r1);
        j1.addRoad(r2);
        r1.setRoadEnd(j1);

        r1.addVehicle(c1,0);
        r1.addVehicle(c1,48);
        r2.addVehicle(c1,0);

        Method method = r1.getClass().getDeclaredMethod("transfer", Vehicle.class, int.class);
        method.setAccessible(true);

        method.invoke(r1, c1,48);

        assertEquals(c1, r1.getVehicle(49));

    }

   @Test
    void move_noSpeed() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("move", Vehicle.class, int.class);
        method.setAccessible(true);

        c1.setSpeed(0);
        r1.addVehicle(c1,45);

        method.invoke(r1,c1,45);
        assertEquals(c1,r1.getVehicle(45));
    }

    @Test
    void move_negativeSpeed() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("move", Vehicle.class, int.class);
        method.setAccessible(true);

        c1.setSpeed(-5);
        r1.addVehicle(c1,45);

        method.invoke(r1,c1,45);
        assertEquals(c1,r1.getVehicle(45));
    }

    @Test
    void move_notFirst() throws Exception{
        Method method = r1.getClass().getDeclaredMethod("move", Vehicle.class, int.class);
        method.setAccessible(true);

        r1.addVehicle(c1,45);
        r1.addVehicle(c2,48);

        method.invoke(r1,c1,45);
        assertEquals(c1,r1.getVehicle(49));
    }

    @Test
    void move_isFirst() throws Exception{
        Junction j1 = new Junction();
        j1.addRoad(r2);
        r1.setRoadEnd(j1);
        r1.addVehicle(c1,48);

        Method method = r1.getClass().getDeclaredMethod("move", Vehicle.class, int.class);
        method.setAccessible(true);

        method.invoke(r1,c1,48);
        boolean test = r1.getVehicle(3) == c1|| r2.getVehicle(3) == c1;

        assertTrue(test);
    }

    @Test
    void applyRules(){
        //checks if rules are applied backwards
        r1.setRoadEnd(new Sink());
        r1.addVehicle(c1, 45);
        r1.addVehicle(c2,47);

        r1.applyRules();

        assertEquals(c1, r1.getVehicle(48));
    }

}