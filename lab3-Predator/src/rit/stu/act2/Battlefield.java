package rit.stu.act2;

import java.util.Random;

/**
 * The main simulation class is run on the command line as:<br>
 * <br>
 * $ java Battlefield #_hostages #_soldiers #_guerillas
 *
 * @author Sean Strout @ RIT CS
 * @author Zack Hudgins
 */
public class Battlefield {
    /** the single instance of the random number generator */
    private final static Random rng = new Random();

    /** the seed the random number generator will use */
    private final static int SEED = 0;

    // TODO: ADD THE REMAINING STATE HERE

    private Bunker bunker;
    private Chopper chopper;
    private EnemyBase enemyBase;
    private Predator predator;


    /**
     * Generate a random integer between min and max inclusive.  For example: <br>
     * <br>
     * <tt>Battlefield.nextInt(1, 5): A random number, 1-5</tt><br>
     * <br>
     *
     * @param min the smallest value allowed.
     * @param max the largest value allowed.
     * @return A random integer
     */
    public static int nextInt(int min, int max) {
        return rng.nextInt(max - min + 1) + min;
    }

    /**
     * Create the battlefield.  This method is responsible for seeding the
     * random number generator and initializing all the supporting classes
     * in the simulation: Chopper, EnemyBase, Bunker and Predator.
     *
     * @param numHostages number of hostages being held in enemy base at start
     * @param numSoldiers number of soldiers to rescue the hostages at start
     * @param numGuerillas number of guerillas in the enemy base at start
     */
    public Battlefield(int numHostages, int numSoldiers, int numGuerillas) {
        this.rng.setSeed(SEED);


        //TODO: Initialize the other classes here
        chopper = new Chopper();
        enemyBase = new EnemyBase(numHostages, numGuerillas);
        bunker = new Bunker(numSoldiers);
        predator = new Predator();
    }

    /**
     * Prints out the current statistics of the simulation in terms of the number
     * of hostages, soldiers, guerillas, and rescued people are.  The format
     * of the message is on a single line:<br>
     * <br>
     * Statistics: # hostages remain, # soldiers remain, # guerillas remain, # rescued<br>
     */
    private void printStatistics() {

        // TODO
        System.out.println("Statistics: " + enemyBase.getNumHostages() + " hostages remain, "
                            + bunker.getNumSoldiers() + " soldiers remain, " + enemyBase.getNumGuerillas()
                            + " guerillas remain, " + chopper.getNumRescued() + " rescued");
    }

    /**
     * The main battle simulation loop runs here.  The simulation runs until either
     * all the hostages have been rescued, or there are no more soldiers left
     * to rescue hostages.  The steps to follow are:<br>
     * <br>
     * 1. Print the start up message, "Get to the choppa!"<br>
     * <br>
     * 2. While the simulation is still going:<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 3. Print the current statistics<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 4. Deploy the next soldier into the enemy base to
     * attempt rescuing the next hostage.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 5. If the hostage was not rescued goto step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 6. Otherwise do steps 7-X.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 7. Print a message "{hostage} rescued from enemy
     * base by soldier {solder}".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 8. Roll the die, 1-100, to get the predators chance to
     * defeat the soldier and display the message, "{soldier} encounters the
     * predator who rolls a #".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 9. If the die roll is greater than the predator's
     * chance to defeat the soldier, declare victory of the soldier over the predator
     * and defeat of the predator to the soldier.  Add the soldier back to the
     * bunker and go to step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 10. Otherwise declare victory of the predator over the
     * soldier and defeat of the soldier to the predator and continue to step 11.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 11. Roll the die again, 1-100, to get the predators
     * chance to defeat a hostage and display the message, "{hostage} encounter the
     * predator who rolls a #".<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 12. If the die roll is less than or equal to the predator's
     * chance to default the hostage, declare victory of the predator over the hostage
     * and defeat of the hostage to the predator.  Go to step 2.<br>
     * <br>
     * &nbsp;&nbsp;&nbsp;&nbsp; 13. Otherwise declare victory of the hostage over the
     * predator and defeat of the predator to the hostage and then have the
     * hostage board the chopper.  Go to step 2.<br>
     * <br>
     * 14. At the end of the simulation any soldier that may be left in the bunker
     * should board the chopper.<br>
     * <br>
     * 15. If there are any remaining passengers on the chopper, rescue them to safety.<br>
     * <br>
     * 16. Print the statistics one last time.<br>
     */
    private void battle() {

        //TODO

        System.out.println("Get to the choppa!");
        while(enemyBase.getNumHostages() > 0 && bunker.hasSoldiers()) {
            printStatistics();
            Soldier soldier = bunker.deployNextSoldier();
            Player hostage = enemyBase.rescueHostage(soldier);
            if (hostage != null) {
                System.out.println(hostage.toString() + " rescued from enemy base by " + soldier.toString() + ".");
                int roll = nextInt(1, 100);
                System.out.println(soldier.toString() + " encounters the predator who rolls a " + roll + ".");
                if (roll > predator.CHANCE_TO_BEAT_SOLDIER) {
                    soldier.victory(predator);
                    predator.defeat(soldier);
                    bunker.fortifySoldiers(soldier);
                    chopper.boardPassenger(hostage);
                } else {
                    predator.victory(soldier);
                    soldier.defeat(predator);
                    int roll2 = nextInt(1, 100);
                    System.out.println(hostage.toString() + " encounters the predator who rolls a " + roll2 + ".");
                    if (roll2 <= predator.CHANCE_TO_BEAT_HOSTAGE) {
                        predator.victory(hostage);
                        hostage.defeat(predator);
                    } else {
                        hostage.victory(predator);
                        predator.defeat(hostage);
                        chopper.boardPassenger(hostage);
                    }
                }
            }
        }while (bunker.hasSoldiers()){
            chopper.boardPassenger(bunker.deployNextSoldier());
        }
        chopper.rescuePassengers();
        printStatistics();
    }

    /**
     * The main method expects there to be three command line arguments:<br>
     * <br>
     *  1: the number of hostages (a positive integer)<br>
     *  2: the number of soldiers (a positive integer)<br>
     *  3: the number of guerillas (a positive integer)<br>
     * <br>
     * If all the arguments are supplied it will create the battle field
     * and then begin the battle.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Battlefield #_hostages #_soldiers #_guerillas");
        } else {
            Battlefield field = new Battlefield(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));
            field.battle();
        }
    }

}
