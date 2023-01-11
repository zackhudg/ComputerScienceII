import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class to represent a single configuration in the Nurikabe puzzle.
 *
 * @author Sean Strout @ RITCS
 * @author Zack Hudgins
 */
public class NurikabeConfig implements Configuration {

    // TODO
    private String[][] board;

    public Map<String, NurikabeBlock> blocks;

    private Map<NurikabeBlock, String> states;

    private int rows;
    private int cols;

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3 3          # rows columns<br>
     * 1 . #        # row 1, .=empty, 1-9=numbered island, #=island, &#64;=sea<br>
     * &#64; . 3    # row 2<br>
     * 1 . .        # row 3<br>
     * </tt><br>
     * @param filename the name of the file to read from
     * @throws FileNotFoundException if the file is not found
     */
    public NurikabeConfig(String filename) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(filename))) {
            // TODO

            blocks = new HashMap<>();
            states = new HashMap<>();
            //creates boardstate with arraylists
            String[] line = in.nextLine().split(" ");
            rows = Integer.parseInt(line[0]);   //3
            cols = Integer.parseInt(line[1]);   //3
            board = new String[rows][cols];

            for (int i = 0; i < rows; i++) {
                line = in.nextLine().split(" ");
                for (int j = 0; j < cols; j++) {
                    board[i][j] = line[j];
                }
            }
            this.setMaps();
        }
    }

    /**
     * creates the maps using the boardstate, as maps can be tricky to reference properly
     * so maps are redefined every time when copied or created
     */
    public void setMaps(){
            for(int i = 0; i<rows; i++) {
                for (int j = 0; j < cols; j++) {
                    NurikabeBlock newBlock = new NurikabeBlock(String.valueOf(i) + String.valueOf(j), this.board[i][j]);
                    this.blocks.put(newBlock.getPosition(), newBlock);
                    this.states.put(newBlock, board[i][j]);
                }
            }
            //sets neighbors
            for(int i = 0; i<rows-1; i++) {
                for (int j = 0; j < cols; j++) {
                    NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                    currentBlock.addNeighbor(blocks.get(String.valueOf(i+1) + String.valueOf(j)));
                }
            }
            for(int i = 1; i<rows; i++) {
                for (int j = 0; j < cols; j++) {
                    NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                    currentBlock.addNeighbor(blocks.get(String.valueOf(i-1) + String.valueOf(j)));
                }
            }
            for(int i = 0; i<rows; i++) {
                for (int j = 0; j < cols-1; j++) {
                    NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                    currentBlock.addNeighbor(blocks.get(String.valueOf(i) + String.valueOf(j+1)));
                }
            }
            for(int i = 0; i<rows; i++) {
                for (int j = 1; j < cols; j++) {
                    NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                    currentBlock.addNeighbor(blocks.get(String.valueOf(i) + String.valueOf(j-1)));
                }
            }
        }

    /**
     * The copy constructor takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    protected NurikabeConfig(NurikabeConfig other) {
        this.rows = other.rows;
        this.cols = other.cols;

        this.blocks = new HashMap<>();
        this.states = new HashMap<>();
        this.board = new String[rows][cols];
        for (int i=0; i<this.rows; ++i) {
            System.arraycopy(other.board[i], 0, this.board[i], 0, this.cols);
        }

        this.setMaps();

    }

    @Override
    /**
     * creates two new configs based off the previous, one with a sea tile and one with a land tile in the next
     * available space
     */
    public Collection<Configuration> getSuccessors() {
        // TODO
        List<Configuration> successors = new LinkedList<>();
        NurikabeConfig copy1 = new NurikabeConfig(this);
        NurikabeConfig copy2 = new NurikabeConfig(this);
        for(int i = 0; i<this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.board[i][j].equals(".")) {
                    copy1.board[i][j] = "#";
                    copy2.board[i][j] = "@";
                    successors.add(copy1);
                    successors.add(copy2);

                    copy1.blocks.get(String.valueOf(i) + String.valueOf(j)).setValue("#");
                    copy2.blocks.get(String.valueOf(i) + String.valueOf(j)).setValue("@");

                    return successors;
                }
            }
        }
        return successors;
    }

    /**
     * used in the island validation, a search to determine how many tiles are connected (not sea tiles,
     * empty tiles are counted as "values" to prevent too early pruning but not visited).
     * @param b         the current tile
     * @param visited   all tiles that are connected
     * @param values    values of the tiles connected
     */
    private void islandDFS(NurikabeBlock b, Set<NurikabeBlock> visited, Set<String>values) {
        for (NurikabeBlock neighbor : b.getNeighbors()) {
            if(neighbor.getValue().equals(("."))) {
                values.add(neighbor.getValue());
            }else if(!visited.contains(neighbor) && !neighbor.getValue().equals("@") ) {
                visited.add(neighbor);
                islandDFS(neighbor, visited, values);
            }
        }
    }

    /**
     * island connectivity validation (checks if only one number tile is connected to correct number of tiles)
     * @return boolean (true if validates correctly)
     */
    public boolean islandNumber(){
        Set values = new HashSet();
        Set visisted = new HashSet();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                values.clear();
                visisted.clear();
                NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                try {
                    int num = Integer.parseInt(currentBlock.getValue());

                    visisted.add(currentBlock);
                    values.add("#");
                    values.add(num);
                    islandDFS(currentBlock, visisted, values);

                    if (visisted.size() > num) {
                        return false;
                    }
                }catch(Exception e){

                    if(currentBlock.getValue().equals("#")) {
                        visisted.add(currentBlock);
                        islandDFS(currentBlock, visisted, values);
                    }
                    for(Object elm: visisted) {
                        if (elm instanceof NurikabeBlock){
                            values.add(((NurikabeBlock) elm).getValue());
                        }
                    }
                }
                if(values.size()==1 ){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * used in the sea validation, searches for all connected sea tiles and adds them to a set.
     * @param b     the active tile
     * @param visited all visited tiles in a set
     */
    private void seaDFS(NurikabeBlock b, Set<NurikabeBlock> visited) {
        for (NurikabeBlock neighbor : b.getNeighbors()) {
            if(!visited.contains(neighbor) && neighbor.getValue().equals("@")) {
                visited.add(neighbor);
                seaDFS(neighbor, visited);
            }
        }
    }

    /**
     * validates that there is only one sea by checking the first sea tile and determining how many sea tiles are connected
     * and comparing it to the total land tiles that should be placed.
     * @return boolean (true if one sea)
     */
    public boolean oneSea(){
        int num=0;
        Set<NurikabeBlock> sea = new HashSet<>();
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                try {
                    num += Integer.parseInt(currentBlock.getValue());
                } catch (Exception e) {
                }
            }
        }
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                if (currentBlock.getValue().equals("@")) {
                    seaDFS(currentBlock, sea);
                    break;
                }
            }
        }
        if(sea.size() + num == rows*cols){
            return true;
        }
        return false;
    }

    /**
     * determines if there are any 2x2 areas where the sea is, creating a pool.
     * part of the validation process
     * @return boolean, true if there are pools present
     */
    private boolean pools(){
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                NurikabeBlock currentBlock = blocks.get(String.valueOf(i) + String.valueOf(j));
                if (currentBlock.getValue().equals("@")) {//each @ block
                    Set<NurikabeBlock> secondNeighbors = new HashSet();

                    for (NurikabeBlock neighbor : currentBlock.getNeighbors()) {
                        if (neighbor.getValue().equals("@")) { //for each @ neighbor of @b

                            for (NurikabeBlock neighbor2 : neighbor.getNeighbors()) {
                                if (neighbor2.getValue().equals("@")&&!neighbor2.equals(currentBlock)) { //for each @ neighbor of @b's @ neighbors

                                    if (secondNeighbors.contains(neighbor2)) {
                                        return true;
                                    } else {
                                        secondNeighbors.add(neighbor2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * validates that the board is in a valid state
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid() {
        // TODO
        if(!islandNumber()){
            System.out.println("!ISLAND");
            return false;
        }else if(pools()){
            System.out.println("POOLS");
            return false;
        }
        return true;
    }

    /**
     * checks if all tiles have been filled in, and if the sea validates
     * @return true if valid final gameboard state, false otherwise
     */
    @Override
    public boolean isGoal() {
        // TODO

        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if (board[i][j].equals(".")){
                    return false;
                }
            }
        }
        if(!oneSea()) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the puzzle, e.g.: <br>
     * <tt><br>
     * 1 . #<br>
     * &#64; . 3<br>
     * 1 . .<br>
     * </tt><br>
     */
    @Override
    public String toString() {
        // TODO
        String end = "";
        for(int i = 0; i<rows; i++){
            String result = "";
            for (int j = 0; j<cols; j++){
                result += board[i][j] + " ";
            }
            end += result + "\n";
        }
        return end;
    }

}
