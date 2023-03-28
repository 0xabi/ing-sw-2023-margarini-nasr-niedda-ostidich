package it.polimi.ingsw.model;

import java.util.*;

public class Bag {

    private static final int totalQuantity = 22;

    private final Map<Tile, Integer> tilesLeft;

    public Bag() {
        tilesLeft = new HashMap<>();

        tilesLeft.put(Tile.CATS, totalQuantity);
        tilesLeft.put(Tile.BOOKS, totalQuantity);
        tilesLeft.put(Tile.FRAMES, totalQuantity);
        tilesLeft.put(Tile.GAMES, totalQuantity);
        tilesLeft.put(Tile.PLANTS, totalQuantity);
        tilesLeft.put(Tile.TROPHIES, totalQuantity);
    }

    public void addTile(Tile tile) {
        tilesLeft.put(tile, tilesLeft.get(tile)+1);
    }

    private void removeTile(Tile tile){
        tilesLeft.put(tile,tilesLeft.get(tile)-1);
    }

    public Tile draw(){
        Random random = new Random();
        int number = random.nextInt(5);
        while(tilesLeft.get(Tile.values()[number])<=0) { //if a tile has no quantity retry the draw
            number = random.nextInt(5);
        }
        removeTile(Tile.values()[number]);
        return Tile.values()[number];
    }

}
