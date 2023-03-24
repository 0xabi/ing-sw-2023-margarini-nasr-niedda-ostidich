package it.polimi.ingsw.model;

import java.util.List;
import java.util.Optional;

public class Board {

    private static final int rowLength = 9;

    private static final int columnLength = 9;

    private Tile[][] spaces;

    private final Bag bag = new Bag();

    private final Optional<EndGameToken> endGameToken;

    public Board() {
        spaces = new Tile[rowLength][columnLength];
        endGameToken = Optional.of(new EndGameToken());
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
