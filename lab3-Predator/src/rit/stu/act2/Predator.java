package rit.stu.act2;
/**
 * predator character which is final fight before resucing hostages
 *
 * @author Zack Hudgins
 */
public class Predator implements Player{
    /**
     * percentage chance to defeat various other players
     */
    public final static int CHANCE_TO_BEAT_SOLDIER = 50;
    public final static int CHANCE_TO_BEAT_HOSTAGE = 75;

    /**
     * constructs empty predator with no attributes
     */
    public Predator(){

    }

    /**
     * victory message for predator
     * @param player player that predator defeats
     */
    public void victory(Player player){
        System.out.println("The predator yells out in triumphant victory over " + player.toString()+"!");
    }

    /**
     * defeat message for predator
     * @param player player that predator loses to
     */
    public void defeat(Player player){
        System.out.println("The predator cries out in glorious defeat to " + player.toString() + "!");
    }

    /**
     * stringifies predator
     * @return predator in string form
     */
    @Override
    public String toString(){
        return "Predator";
    }
}