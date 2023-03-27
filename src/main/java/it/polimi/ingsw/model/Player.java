package it.polimi.ingsw.model;
import java.util.List;

public class Player {

    private final String name;

    private int points;

    private final PersonalGoal personalGoal;

    private final Shelf shelf;

    public Player(String name, int personalGoalNumber) {
        this.name = name;
        points = 0;
        shelf = new Shelf();
        personalGoal = new PersonalGoal(personalGoalNumber);
    }

    public String getName(){
        return name;
    }

    public int getPoints(){
        return points;
    }

    public PersonalGoal getPersonalGoal(){
        return personalGoal;
    }

    public Shelf getShelf(){
        return shelf;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public List<Coordinates> pickTiles(Board board) {
        //selects (if legally chosen) tiles on the board
        //uses the method checkAvailablePickNumber()
        return null;
    }

    private int checkAvailablePickNumber() {
        //returns the size of empty spaces in the least filled column
        return 0;
    }

    public void insertTiles(List<Tile> tiles) {
        int column = getColumnChoice();
        //inserts the list of tiles in the column asking for the order
        //uses method chooseOrder()
        //uses method getColumnChoice()
    }

    private int getColumnChoice() {
        //asks the player which column to insert in
        return 0;
    }

    private void chooseOrder(List<Tile> tiles){
        //asks the player to choose the order to insert the selection
    }

}
