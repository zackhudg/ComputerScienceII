package rit.stu.act2;
import rit.stu.act1.StackNode;

/**
 * The chopper stack which rescues hostages and soldiers if any left alive
 *
 * @author Zack Hudgins
 */
public class Chopper{
    /**
     * chopper: stack that represents chopper
     * MAX_OCCUPANCY: max number of passengers chopper can hold
     * numPassengers: number of passengers in chopper
     * numRescued: total number of people rescued
     */
    private StackNode<Player> chopper;
    private final static int MAX_OCCUPANCY = 6;
    private int numPassengers;
    private int numRescued;

    /**
     * constructs empty chopper stack with no passengers and none rescued
     */
    public Chopper(){
        this.numPassengers = 0;
        this.numRescued = 0;
        this.chopper = new StackNode<>();
    }

    /**
     * tells whether chopper empty or not
     * @return true if empty, false if not
     */
    public boolean isEmpty(){
        return this.numPassengers == 0;
    }

    /**
     * tells if chopper full
     * @return true if full, false if not
     */
    public boolean isFull(){
        return this.numPassengers == MAX_OCCUPANCY;
    }

    /**
     * tells how many people rescued total
     * @return num rescued
     */
    public int getNumRescued(){
        return numRescued;
    }

    /**
     * transports passengers to safety, empties chopper, increases num rescued per person transported
     */
    public void rescuePassengers(){
        while(!this.isEmpty()){
            Player passenger = chopper.pop();
            this.numRescued++;
            this.numPassengers--;
            System.out.println("Chopper transported "+ passenger.toString() + " to safety!");
        }
    }

    /**
     * pushes players into chopper, calls rescuePassengers if full
     * @param player player who boards chopper
     */
    public void boardPassenger(Player player){
        if (this.isFull()){
            this.rescuePassengers();
        }
        this.numPassengers++;
        chopper.push(player);
        System.out.println(player.toString() + " boards the chopper!");
    }
}