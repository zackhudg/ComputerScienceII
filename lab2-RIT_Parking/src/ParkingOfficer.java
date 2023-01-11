/**
 * A class to represent a single parking officer
 *
 * @author Zack Hudgins
 */
import java.util.ArrayList;

public class ParkingOfficer {
    /** static int determines time officer pauses to write ticket */
    private static int PAUSE_TIME = 1000;
    /**  ParkingLot that officer patrols */
    private ParkingLot lot;
    /**  Tickets that officer has given out */
    private ArrayList<Ticket> tickets;

    /**
     * constructor for ParkingOfficer class
     */
    public ParkingOfficer(){
        this.lot = null;
        this.tickets = new ArrayList<>(0);
    }

    /**
     * determines fine to be given based off the car's permit and what permit the spot requires
     * @param vehicle   vehicle parked in spot
     * @param spot      spot vehicle is parked in
     * @return          fine that is to be given as a ticket
     */
    public static Fine getFineVehicleSpot(Vehicle vehicle, ParkingSpot spot){
        if(spot.getType() == Permit.Type.HANDICAPPED){
            if (!vehicle.hasPermit() || vehicle.getPermit().getType() != spot.getType()){
                return Fine.PARKING_HANDICAPPED;
            }
        }else if(spot.getType() == Permit.Type.RESERVED){
            if (!vehicle.hasPermit() || vehicle.getPermit().getType() == Permit.Type.GENERAL){
                return Fine.PARKING_RESERVED;
            }
        }else{
            if(!vehicle.hasPermit()){
                return Fine.NO_PERMIT;
            }
        }
        return Fine.NO_FINE;
    }

    /**
     * setter function to fill ParkingOfficer's lot attribute
     * @param lot   lot to be patrolled by officer
     */
    public void setParkingLot(ParkingLot lot) {
        if (this.lot == null) {
            this.lot = lot;
        }
    }

    /**
     * getter for tickets attribute
     * @return ticets officer has given out
     */
    public ArrayList<Ticket> getTickets(){
        return this.tickets;
    }

    /**
     * adds ticket to officers tickets attibute and vehicles ticket attribute
     * @param vehicle   vehicle that gets the ticket
     * @param spot      spot vehicle is in that caused the issuing of said ticket
     * @param fine      amount that ticket charges
     */
    private void issueTicket​(Vehicle vehicle, int spot, Fine fine){
        if (fine != Fine.NO_FINE){
            Ticket t = new Ticket(vehicle.getPlate(), fine);
            vehicle.giveTicket(t);
            this.tickets.add(t);
            System.out.println("Issuing ticket to: " + vehicle.toString() + " in spot " + spot + " for " + fine);
        }
    }

    /**initiates sequence where officer finds which vehicles are illegally parked
     */
    public void patrolLot(){
        for (int i = 0; i<this.lot.getCapactiy(); i++){
            if(!this.lot.isSpotVacant(i)){
                Fine f = getFineVehicleSpot(this.lot.getSpot(i).getVehicle(), this.lot.getSpot(i));
                if (f != Fine.NO_FINE){
                    issueTicket​(this.lot.getSpot(i).getVehicle(), i, f);
                    RITParking.pause(PAUSE_TIME);
                }
            }

        }
    }

    /**
     * tests ParkingOfficer methods and attributes
     * @param args not used
     */
    public static void main​(String[] args){
        ParkingLot lot = new ParkingLot(5, 5, 5);
        Vehicle v1 = new Vehicle(123);
        System.out.println(lot.parkVehicle(v1, 12));
        Permit p1 = new Permit(1, Permit.Type.GENERAL);
        v1.setPermit(p1);

        ParkingOfficer po1 = new ParkingOfficer();
        po1.setParkingLot(lot);
        po1.patrolLot();
        System.out.println(po1.tickets.toString());

        lot.removeVehicle(v1);
        System.out.println(lot.parkVehicle(v1, 1));
        po1.patrolLot();
        System.out.println(po1.tickets.toString());

    }
}
