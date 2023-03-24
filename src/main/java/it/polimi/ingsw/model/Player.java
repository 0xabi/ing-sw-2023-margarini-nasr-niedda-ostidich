package it.polimi.ingsw.model;

public class Player {

    private String name;
    private int points;
    private PesonalGoal personalGoal;
    private Shelf shelf;

    public Player(String name){
        this.name = name;
        points = 0;
        shelf = new Shelf();
        pesonalGoal = new PersonalGoal();
    }

     public String getName(){
        return name;
     }

     public int getPoints(){
        return points;
     }

     public PersonalGoal(){
        return personalGoal;
     }

     public Shelf getShelf(){
        return shelf;
     }

     public void addPoints(int points){
        this.points = this.points + points;
     }

     public List<Coordinates> pickTiles(Board board){

    }

    private int checkAvailablePickNumber() {

}

    public void  insertTiles(list<tile> tiles, int column){

    }

    private void chooseOrder(list<tile>){

    }
}
