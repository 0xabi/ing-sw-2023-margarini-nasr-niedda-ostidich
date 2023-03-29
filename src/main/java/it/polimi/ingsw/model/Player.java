package it.polimi.ingsw.model;
import java.util.List;

/**
 * A player is constructed on his unique name. It has his own shelf and points (initialized as 0 and only addable).
 * Players are called to pick, choose order, and insert tiles from board to shelf. The process is completely checked to
 * avoid illegal moves.
 *
 * @author Pietro Andrea Niedda
 */
public class Player {

    private final String name;

    private int points;

    private final PersonalGoal personalGoal;

    private final Shelf shelf;

    /**
     * Class constructor.
     *
     * @param name is the player's name
     * @param personalGoalNumber is the sorted out number for player's personal goal
     * @author Pietro Andrea Niedda
     */
    public Player(String name, int personalGoalNumber) {
        this.name = name;
        points = 0;
        shelf = new Shelf();
        personalGoal = new PersonalGoal(personalGoalNumber);
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public String getName(){
        return name;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public int getPoints(){
        return points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public PersonalGoal getPersonalGoal(){
        return personalGoal;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public Shelf getShelf(){
        return shelf;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param points
     * @author Pietro Andrea Niedda
     */
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param board
     * @author Pietro Andrea Niedda
     */
    public List<Coordinates> pickTiles(Board board) {
        //TODO method code is to be written
        //selects (if legally chosen) tiles on the board
        //uses the method checkAvailablePickNumber()
        return null;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    private int checkAvailablePickNumber() {
        //TODO method code is to be written
        //returns the size of empty spaces in the least filled column
        return 0;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    public void insertTiles(List<Tile> tiles) {
        //TODO method code is to be written
        int column = getColumnChoice();
        //inserts the list of tiles in the column asking for the order
        //uses method chooseOrder()
        //uses method getColumnChoice()
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    private int getColumnChoice() {
        //TODO method code is to be written
        //asks the player which column to insert in
        return 0;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    private void chooseOrder(List<Tile> tiles) {
        //TODO method code is to be written
        //asks the player to choose the order to insert the selection
    }

}
