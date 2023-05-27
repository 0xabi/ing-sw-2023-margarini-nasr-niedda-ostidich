package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.Tile;

import java.io.Serializable;
import java.util.*;

/**
 * The number of each single tile is given by a constant. A map with tiles as key remembers the remaining quantities in the bag.
 * It's called to draw tiles when the board is to be refilled (if there are any tile left of that type in the bag).
 * Before the refill, tiles remaining on the board should be emptied in bag.
 *
 * @author Edoardo Margarini
 */
public class Bag implements Serializable {

    private static final int TOTAL_QUANTITY = 22;

    private final Map<Tile, Integer> tilesLeft;

    /**
     * Class constructor.
     *
     * @author Edoardo Margarini
     */
    public Bag() {
        tilesLeft = new HashMap<>();
        for (Tile tile : Tile.values())
            if (!tile.equals(Tile.EMPTY))
                tilesLeft.put(tile, TOTAL_QUANTITY);
    }

    /**
     * Getter for tiles' type total quantity.
     *
     * @return total quantity of tiles' type int
     * @author Francesco Ostidich
     */
    public static int getTotalQuantity() {
        return TOTAL_QUANTITY;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tile
     * @author Edoardo Margarini
     */
    protected void addTile(Tile tile) {
        tilesLeft.put(tile, tilesLeft.get(tile) + 1);
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tile
     * @author Edoardo Margarini
     */
    private void removeTile(Tile tile) {
        tilesLeft.put(tile, tilesLeft.get(tile) - 1);
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Edoardo Margarini
     */
    protected Tile draw() {
        Random random = new Random();
        int number = random.nextInt(6);
        while (tilesLeft.get(Tile.values()[number]) <= 0) { //if a tile has no quantity retry the draw
            number = random.nextInt(6);
        }
        removeTile(Tile.values()[number]);
        if(Tile.values()[number]==Tile.PLANTS)
            System.out.println("YESSS");
        return Tile.values()[number];
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Edoardo Margarini
     */
    protected int size() {
        return tilesLeft.values()
                .stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public Map<Tile, Integer> getTilesLeft() {
        return tilesLeft;
    }

}
