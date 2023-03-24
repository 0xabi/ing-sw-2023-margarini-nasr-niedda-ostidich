package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Bag {

    private static final int totalQuantity = 22;

    private final Map<Tile, Integer> tilesLeft;

    public Bag() {
        tilesLeft = new HashMap<Tile, Integer>();
        //fill the bag
    }

    private int tilesLeft(Tile tile) {
        return tilesLeft.get(tile);
    }

    private void addTiles(Tile tile, int quantity) {
        //add quantity to value in key tile
    }

    public void refillBoard(Board board) {
        //set new filled board
    }

    private void emptyBoardInBag(Board board) {
        //adds remains in board to bag
    }

}
