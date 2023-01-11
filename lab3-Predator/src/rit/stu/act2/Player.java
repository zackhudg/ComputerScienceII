package rit.stu.act2;
/**
 * Player interface which is implemented to create all characters in sim
 *
 * @author Zack Hudgins
 */
public interface Player {
    /**
     * victory message for player
     * @param player class that implements Player
     */
    void victory(Player player);

    /**
     * defeaat message for player
     * @param player class that implements Player
     */
    void defeat(Player player);
}
