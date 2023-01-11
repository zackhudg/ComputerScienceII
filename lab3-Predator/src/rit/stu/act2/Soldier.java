package rit.stu.act2;
/**
 * Soldier players which go into enemyBase to rescue hostages
 *
 * @author Zack Hudgins
 */
public class Soldier implements Player{
    /**
     * soldier's id
     */
    private int id;

    /**
     * constructs soldier with given id
     * @param id soldier's id when created
     */
    public Soldier(int id){
        this.id = id;
    }

    /**
     * defeat message for soldier
     * @param player player that soldier loses to
     */
    public void defeat(Player player){
        System.out.println(this.toString() + " cries, 'Beseigt von "+player.toString() + "!'");
    }

    /**
     * victory message for soldiers
     * @param player player that soldier defeats
     */
    public void victory(Player player){
        System.out.println(this.toString() + " yells, 'Sieg über " + player.toString() + "!'");
    }

    /**
     * stringifies soldier
     * @return soldier in string form
     */
    @Override
    public String toString(){
        return "Soldier #" + this.id;
    }
}