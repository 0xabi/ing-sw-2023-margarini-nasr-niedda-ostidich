package it.polimi.ingsw.server.model;

import it.polimi.ingsw.general.Tile;

import java.io.Serializable;
import java.util.*;

/**
 * The Bag class represents a bag of tiles used in the game. It keeps track of the remaining quantities of each tile type.
 * Tiles are drawn from the bag when the board is refilled, and any tiles remaining on the board are returned to the bag.
 * The bag ensures that there are a fixed number of each tile type available for drawing.
 *
 * <p>The number of each single tile is given by a constant. A map with tiles as keys remembers the remaining quantities in the bag.
 * The bag is called to draw tiles when the board is to be refilled (if there are any tiles left of that type in the bag).
 * Before the refill, any tiles remaining on the board should be emptied into the bag.</p>
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
     * Adds a tile to the bag.
     *
     * <p>Adds one instance of the specified tile to the bag. The quantity of the tile is increased by one in the bag's tile count.</p>
     *
     * @param tile the tile to be added to the bag.
     *
     * @author Edoardo Margarini
     */

    protected void addTile(Tile tile) {
        tilesLeft.replace(tile, tilesLeft.get(tile) + 1);
    }

    /**
     * Removes a tile from the bag.
     *
     * <p>Removes one instance of the specified tile from the bag. The quantity of the tile is decreased by one in the bag's
     * tile count.</p>
     *
     * @param tile the tile to be removed from the bag.
     *
     * @author Edoardo Margarini
     */

    private void removeTile(Tile tile) {
        tilesLeft.put(tile, tilesLeft.get(tile) - 1);
    }

    /**
     * Draws a random tile from the bag.
     *
     * <p>A random tile is selected from the remaining tiles in the bag. The quantity of each tile is taken into account,
     * and only tiles with a positive quantity can be drawn. If a tile has no remaining quantity, the draw is retried until
     * a valid tile is selected.</p>
     *
     * @return the randomly drawn tile from the bag.
     *
     * @author Edoardo Margarini
     */

    protected Tile draw() {
        Random random = new Random();
        int number = random.nextInt(6);
        while (tilesLeft.get(Tile.values()[number]) <= 0) { //if a tile has no quantity retries the draw
            number = random.nextInt(6);
        }
        removeTile(Tile.values()[number]);
        return Tile.values()[number];
    }

    /**
     * Returns the total number of tiles remaining in the bag.
     *
     * <p>The size of the bag is calculated by summing up the quantities of all the remaining tiles.</p>
     *
     * @return the total number of tiles remaining in the bag as an integer.
     *
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
