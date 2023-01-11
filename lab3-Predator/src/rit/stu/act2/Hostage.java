package rit.stu.act2;
/**
 * the hostage player which are being rescued
 *
 * @author Zack Hudgins
 */
public class Hostage implements Player{
    /**
     * id: hostage's id
     */
    private int id;

    /**
     * constructs hostage with given id
     * @param id hostage's id
     */
    public Hostage(int id){
        this.id = id;
    }

    /**
     * defeat message for hostage
     * @param player player hostage loses to
     */
    public void defeat(Player player){
        System.out.println(this.toString() + " cries, 'Defeated by "+player.toString()+"!'");
    }

    /**
     * victory message for hostage
     * @param player player hostage beats
     */
    public void victory(Player player){
        System.out.println(this.toString() + " yells, 'Victory over " + player.toString() + "!'");
    }

    /**
     * stringifies hostage
     * @return string form of hostage
     */
    @Override
    public String toString(){
        return "Hostage #"+ this.id;
    }
}