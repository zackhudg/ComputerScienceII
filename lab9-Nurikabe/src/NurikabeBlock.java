import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to represent one block or tile of the puzzle
 * @author Zack Hudgins
 */

public class NurikabeBlock {

    private String position;

    private Set<NurikabeBlock> neighbors;

    private String value;

    /**
     * constructor the the NBlock class, fills position neighbors and value
     * @param position the tile location
     * @param value     the tile value (@, #, ., number)
     */
    public NurikabeBlock(String position, String value) {
        this.position = position;
        this.neighbors = new HashSet<NurikabeBlock>();
        this.value = value;
    }

    /**
     * getter function to determine tile location
     * @return position of tile
     */
    public String getPosition() {
        return this.position;

    }

    /**
     * sets value of a tile
     * @param value the value to be given
     */
    public void setValue(String value){
        this.value = value;
    }

    /**
     * gets value of a tile
     * @return the tile's value
     */
    public String getValue(){
        return this.value;
    }

    /**
     * gets adjacent tiles
     * @return set of adjacent tiles
     */
    public Collection<NurikabeBlock> getNeighbors() {
        return neighbors;
    }

    /**
     * adds tiles to the neighbors set
     * @param neighbor tile to be added
     */
    public void addNeighbor(NurikabeBlock neighbor) {
        this.neighbors.add(neighbor);
    }

    /**
     * deermines equivalency between tiles based off position
     * @param o tile to be compared
     * @return true if equivalent, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NurikabeBlock NurikabeBlock = (NurikabeBlock) o;

        return position.equals(NurikabeBlock.position);
    }

    /**
     * creates a hashcode from tile
     * @return hashed tile
     */
    @Override
    public int hashCode() {
        return position.hashCode();
    }

    /**
     * String representation of the tile, only giving position
     * @return tiles position in string form
     */
    @Override
    public String toString() {
        return this.position;
    }
}
