/**
 * A class to represent a single parking lot
 *
 * @author Zack Hudgins
 */
import java.util.ArrayList;

public class ParkingLot {
    /**  static int to fill int requirements when illegally parked */
    static int ILLEGAL_SPOT = -1;
    /**  static int to determine how many spots in row when printing lot */
    private static int SPOTS_PER_LINE = 10;
    /**  max capacity, aka total number of spots, both filled and unfilled */
    private int capactiy;
    /**  number of total general spots, both filled and unfilled */
    private int generalSpots;
    /**  number of handicapped spots, both filled and unfilled */
    private int handicappedSpots;
    /**  list of all available spots in lot */
    private ArrayList<ParkingSpot> lot;
    /**  number of vehicles parked in lot */
    private int parkedVehicles;
    /**  number of reserved spots, both filled and unfilled */
    private int reservedSpots;

    /**
     * constructor for ParkingLot class
     * @param handicappedSpots  number handicapped spots to be created
     * @param reservedSpots     number reserved spots to be created
     * @param generalSpots      number general spots to be created
     */
    public ParkingLot(int handicappedSpots, int reservedSpots, int generalSpots){
        this.handicappedSpots = handicappedSpots;
        this.reservedSpots = reservedSpots;
        this.generalSpots = generalSpots;
        this.capactiy = handicappedSpots + reservedSpots + generalSpots;
        this.parkedVehicles = 0;
        this.lot = new ArrayList<>(0);
        initializeSpots();
    }

    /**
     * helper to constructor to assign permit types to each spot in desired quantities
     */
    private void initializeSpots(){
        for(int i = 0; i<handicappedSpots; i++){
            this.lot.add(new ParkingSpot(i, Permit.Type.HANDICAPPED));
        }
        for(int i = handicappedSpots; i<reservedSpots+handicappedSpots; i++){
            this.lot.add(new ParkingSpot(i, Permit.Type.RESERVED));
        }
        for(int i = handicappedSpots+reservedSpots; i < capactiy; i++){
            this.lot.add(new ParkingSpot(i, Permit.Type.GENERAL));
        }
    }

    /**
     * getter for capacity attribute
     * @return capacity of lot
     */
    public int getCapactiy(){
        return this.capactiy;
    }

    /**
     * getter for parkedVehicles attribute
     * @return number of cars parked in lot
     */
    public int getNumParkedVehicles(){
        return this.parkedVehicles;
    }

    /**
     * tells if given int is a spot id in lot
     * @param spot id of spot trying to be found in lot
     * @return true if spot id exists, false if not
     */
    public boolean isSpotValid(int spot){
        if (0 <= spot && spot < capactiy){
            return true;
        }else{
            return false;
        }
    }

    /**
     * getter for spot from lot attribute (array list)
     * @param spot id of spot to be found in lot
     * @return  ParkingSpot object in lot
     */
    public ParkingSpot getSpot(int spot){
        return this.lot.get(spot);
        }

    /**
     * tests whether spot at given id is vacant or not
     * @param spot  id to be tested for vacancy in lot
     * @return      true if vacant, false if taken
     */
    public boolean isSpotVacant(int spot){
        if (this.lot.get(spot).getVehicle() == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * attempts to park car in given spot
     * @param vehicle   vehicle to be parked in spot
     * @param spot      spot in which vehicle will park
     * @return          true if vehicle successfully parks, false if already occupied
     */
    public boolean parkVehicle(Vehicle vehicle, int spot){
        if (this.lot.get(spot).getVehicle() == null){
            this.lot.get(spot).occupySpot​(vehicle);
            return true;
        }else{
            return false;
        }
    }

    /**
     * removes vehicle from its spot
     * @param vehicle   determines which vehicle will be removed
     * @return          spot id that vehicle was removed from
     */
    public int removeVehicle(Vehicle vehicle){
        for (int i = 0; i<this.capactiy; i++){
            if (vehicle.equals(this.lot.get(i).getVehicle())){
                this.lot.get(i).vacateSpot();
                return i;
            }

        }
        return ILLEGAL_SPOT;
    }

    /**
     * converts parking lot to grid-like string to be easily viewed
     * @return  formatted string of parking lot, number of vacant spots
     */
    @Override
    public String toString(){
        int i = 0;
        String result = "";
        while(i<this.capactiy){
            for (int j = 0; j<SPOTS_PER_LINE; j++){
                if (i<this.capactiy){
                    result += (this.lot.get(i).toString() + " ");
                    i++;
                }
            }
            result += "%n";
        }
        int k = 0;
        for (ParkingSpot spot:this.lot){
            if (spot.getVehicle() != null){
                k++;
            }
        }
        return String.format(result += "Vacant Spots: " + (this.capactiy-k));
    }

    /**
     * tests ParkingLot methods and attributes
     * @param args not used
     */
    public static void main​(String[] args){
        ParkingLot lot = new ParkingLot(5, 5, 5);
        System.out.println(lot.getCapactiy());
        System.out.println(lot.isSpotValid(12));

        Vehicle v1 = new Vehicle(123);
        System.out.println(lot.isSpotVacant(12));
        System.out.println(lot.parkVehicle(v1, 12));
        System.out.println(lot.isSpotVacant(12));
        System.out.println(lot.getNumParkedVehicles());
        System.out.println(lot.getSpot(12).toString());

        System.out.println(lot.toString());

        System.out.println(lot.removeVehicle(v1));
        System.out.println(lot.toString());
    }
}
