package it.polimi.ingsw.model;
import java.util.List;

public class Player {

    private String name;

    private int points;

    private PersonalGoal personalGoal;

    private Shelf shelf;

    public Player(String name){
        this.name = name;
        points = 0;
        shelf = new Shelf();
        personalGoal = new PersonalGoal();
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

    public void addPoints(int points){
        this.points = this.points + points;
    }

    public List<Coordinates> pickTiles(Board board){
        return null;
    }

    private int checkAvailablePickNumber() {
        return 0;
    }

    public void  insertTiles(List<Tile> tiles, int column){

    }

    private void chooseOrder(List<Tile> tiles){

    }

}
