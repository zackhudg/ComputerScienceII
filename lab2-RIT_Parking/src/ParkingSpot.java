/**
 * A class to represent a single parking spot
 *
 * @author Zack Hudgins
 */
public class ParkingSpot {
    /**  * represents taken spot when lot is printed */
    private static String OCCUPIED_STR = "*";
    /**  unique id for each spot in lot */
    private int spot;
    /**  determines which type of permit is required for parking in this spot */
    private Permit.Type type;
    /**  the vehicle object which is parked in the spot currently */
    private Vehicle vehicle;

    /**
     * constructor for ParkingSpot Class
     * @param spot  spot id number
     * @param type  permit type required for parking here
     */
    public ParkingSpot(int spot, Permit.Type type){
        this.spot = spot;
        this.type = type;
        this.vehicle = null;
    }

    /**
     * getter for spot attribute
     * @return spot id
     */
    public int getSpot(){
        return this.spot;
    }

    /**
     * getter for type attribute
     * @return type of permit required to park
     */
    public Permit.Type getType(){
        return this.type;
    }

    /**
     * getter for vehicle attribute
     * @return vehicle currently parked in spot
     */
    public Vehicle getVehicle(){
        return this.vehicle;
    }

    /**
     * parks a vehicle in spot if empty
     * @param vehicle vehicle object that will park in spot
     */
    public void occupySpot​(Vehicle vehicle){
        if (this.vehicle == null) {
            this.vehicle = vehicle;
            this.vehicle.setParked(true);
        }
    }

    /**
     * removes vehicle from current spot if one is parked there
     */
    public void vacateSpot(){
        if (this.vehicle != null){
            this.vehicle.setParked(false);
            this.vehicle = null;
        }
    }

    /**
     * turns ParkingSpot into readable string
     * @return string containing information about the spot
     */
    @Override
    public String toString(){
        if (this.vehicle == null) {
            if (this.type == Permit.Type.HANDICAPPED){
                return String.format("%02d", this.spot) + ":H";
            }else if (this.type == Permit.Type.RESERVED) {
                return String.format("%02d", this.spot) + ":R";
            }else{
                return String.format("%02d", this.spot) + ":G";
            }
        }else{
            return String.format("%02d", this.spot) + ":" + OCCUPIED_STR;
        }
    }

    /**
     * compares spot with spot attributes passed as arguments
     * @param spotVar   name of spot that is comparing to arguments, used for output only
     * @param s         spot being compared to args
     * @param spot      spot id to be compared to s
     * @param type      type to be compared to s
     * @param vehicle   vehicle to be compared to s
     */
    private static void verifySpot​(String spotVar, ParkingSpot s, int spot,
                                    Permit.Type type, Vehicle vehicle){
        System.out.println(spotVar+":");
        if (s.getSpot() == spot){
            System.out.println("Spot ID is correct");
        }else{
            System.out.println("Spot ID is incorrect");
        }
        if (s.getType() == type){
            System.out.println("Permit type is correct");
        }else{
            System.out.println("Permit type is incorrect");
        }
        if (s.getVehicle() == vehicle){
            System.out.println("Vehicle is correct");
        }else{
            System.out.println("Vehicle is incorrect");
        }
    }

    /**
     * testing methods and attributes for ParkingSpot class
     * @param args none used
     */
    public static void main​(String[] args){
        ParkingSpot s1 = new ParkingSpot(0, Permit.Type.HANDICAPPED);
        ParkingSpot s2 = new ParkingSpot(1, Permit.Type.RESERVED);
        ParkingSpot s3 = new ParkingSpot(2, Permit.Type.GENERAL);

        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());
        System.out.println(s2.getSpot());
        System.out.println(s1.getType());
        System.out.println(s3.getVehicle());

        Vehicle v1 = new Vehicle(123);
        verifySpot​("s4", s1, 0, Permit.Type.GENERAL, null);
        verifySpot​("s5", s2, 0, Permit.Type.RESERVED, v1);
    }
}
