package it.polimi.ingsw.model;

import java.util.List;
import java.util.Optional;


public class Board {

    private static final int rowLength = 9;

    private static final int columnLength = 9;

    private Tile[][] spaces;

    private final Bag bag = new Bag();

    private final Optional<EndGameToken> endGameToken;

    public Board(int num) {
        spaces = new Tile[rowLength][columnLength];
        endGameToken = Optional.of(new EndGameToken());

        if(num>=2 && num<5){

            for (int j = 3; j < 5; j++) {
                    spaces[1][j] = Tile.EMPTY;
                }
            for (int j = 3; j < 6; j++) {
                    spaces[2][j] = Tile.EMPTY;
                }
            for (int j = 2; j < 8; j++) {
                    spaces[3][j] = Tile.EMPTY;
                }
            for (int j = 1; j < 5; j++) {
                    spaces[4][j] = Tile.EMPTY;
                }
            for (int j = 1; j < 7; j++) {
                    spaces[5][j] = Tile.EMPTY;
                }
            for (int j = 3; j < 6; j++) {
                    spaces[6][j] = Tile.EMPTY;
                }
            for (int j = 4; j < 6; j++) {
                    spaces[7][j] = Tile.EMPTY;
                }

            if(num>=3){
                spaces[0][3]=Tile.EMPTY;
                spaces[2][6]=Tile.EMPTY;
                spaces[3][8]=Tile.EMPTY;
                spaces[6][6]=Tile.EMPTY;
                spaces[8][5]=Tile.EMPTY;
                spaces[6][2]=Tile.EMPTY;
                spaces[5][0]=Tile.EMPTY;
                spaces[2][2]=Tile.EMPTY;


            }

            if(num==4){
                spaces[0][4]=Tile.EMPTY;
                spaces[1][5]=Tile.EMPTY;
                spaces[4][8]=Tile.EMPTY;
                spaces[5][7]=Tile.EMPTY;
                spaces[8][4]=Tile.EMPTY;
                spaces[7][3]=Tile.EMPTY;
                spaces[4][0]=Tile.EMPTY;
                spaces[3][1]=Tile.EMPTY;

            }
        }

    }

    public Tile[][] getSpaces() {
        return spaces;
    }

    public void setSpaces(Tile[][] spaces) {
        this.spaces = spaces;
    }

    public boolean checkToRefill() {
        //check if it is to be refilled
        //uses method checkNoMoreTurns()
        return false;
    }

    private boolean checkNoMoreTurns() {
        //check if it is the last turn of the last cycle
        return false;
    }

    public void refill() {
        bag.refillBoard(this);
    }

    public boolean selectTiles(List<Coordinate> selection) {
        //check whether selection is legit and if true empties the tiles selected on the board
        //uses method checkSelection()
        //uses method emptyTiles()
        return false;
    }

    private boolean checkSelection(List<Coordinate> selection) {
        //check whether selection is legit
        return false;
    }

    private void emptyTiles(List<Coordinate> selection) {
        //empties the tile selected on board
    }

}
