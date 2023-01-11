package rit.stu.act2;
/**
 * The guerilla player that holds hostages hostage
 *
 * @author Zack Hudgins
 */
public class Guerilla implements Player{
    /**
     * CHANCE_TO_BEAT_SOLDIER: % chance guerilla defeates a soldier
     * id: guerilla's id
     */
    final public static int CHANCE_TO_BEAT_SOLDIER = 20;
    private int id;

    /**
     * constructs guerilla with given id
     * @param id guerilla's id
     */
    public Guerilla(int id){
        this.id = id;
    }

    /**
     * defeat message for guerilla
     * @param player player guerilla loses to
     */
    public void defeat(Player player){
        System.out.println(this.toString() +" cries, 'Derrotado por " + player.toString() + "!'");
    }

    /**
     * victory royale message for guerilla
     * @param player playaer guerilla beats
     */
    public void victory(Player player){
        System.out.println(this.toString() + " yells, 'Victoria sobre " + player.toString() + "!'");
    }

    /**
     * stringifies guerilla
     * @return guerilla in string form
     */
    @Override
    public String toString(){
        return "Guerilla #" + this.id;
    }
}