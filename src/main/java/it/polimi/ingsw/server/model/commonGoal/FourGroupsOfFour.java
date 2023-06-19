package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 *
 * @author Edoardo Margarini
 */
public class FourGroupsOfFour extends CommonGoal {

    private Tile[][] copy;

    private static final int dimGroup = 4;

    private static final int times = 4;

    private static final String commonGoalName = "FourGroupsOfFour";

    public FourGroupsOfFour(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Edoardo Margarini
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        copy = shelf.getPositions().clone();
        int count = 0;
        for (int i = 0; i < Shelf.getRowLength(); i++)
            for (int j = 0; j < Shelf.getColumnLength(); j++)
                if (copy[i][j] != null) { //if it's full, checks whether part of a 4-tiles group
                    if (BelongToABlock(i, j) == dimGroup)
                        count++;
                }
        return count >= times;
    }

    /**
     * This method returns a list of adjacent tiles to the given coordinates (x, y). The tiles are determined
     * by comparing the tile at the given coordinates with its adjacent tiles in the 'copy' array.
     * The adjacent tiles are checked in the north, south, west, and east directions.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return a list of adjacent tiles as {@link Coordinates} objects
     *
     * @throws IndexOutOfBoundsException if the given coordinates are out of bounds in the 'copy' array
     *
     * @author Edoardo Margarini
     */

    private @NotNull List<Coordinates> adjacentTile(int x, int y) {
        //noinspection DuplicatedCode
        List<Coordinates> adjTiles = new LinkedList<>();
        try {
            if (copy[x][y] == copy[x-1][y]) adjTiles.add(new Coordinates(x-1, y));
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            if (copy[x][y] == copy[x+1][y]) adjTiles.add(new Coordinates(x+1, y));
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            if (copy[x][y] == copy[x][y-1]) adjTiles.add(new Coordinates(x, y-1));
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            if (copy[x][y] == copy[x][y+1]) adjTiles.add(new Coordinates(x, y+1));
        } catch (IndexOutOfBoundsException ignored) {
        }
        return adjTiles;
    }

    /**
     *
     * This method determines the number of tiles belonging to a block, starting from the given coordinates (x, y).
     * It performs a breadth-first search (BFS) algorithm to explore adjacent tiles and counts the number of visited tiles.
     *
     * <p>The method modifies the 'copy' array by setting the visited tiles to null.</p>
     *
     * @param x the x-coordinate of the starting tile
     * @param y the y-coordinate of the starting tile
     * @return the number of tiles belonging to the block
     *
     * @author Edoardo Margarini
     */

    @SuppressWarnings("DuplicatedCode")
    private int BelongToABlock(int x, int y) {
        int count = 0;
        Queue<Coordinates> queue = new LinkedList<>(adjacentTile(x, y));
        copy[x][y] = null;
        count++;
        int xTemp;
        int yTemp;
        while (queue.size() > 0) {
            xTemp = queue.peek().x();
            yTemp = queue.peek().y();
            queue.remove();
            for (Coordinates e : adjacentTile(xTemp, yTemp)) {
                if (!queue.stream().toList().contains(e))
                    queue.add(e);
            }
            copy[xTemp][yTemp] = null;
            count++;
        }
        return count;
    }

    /**
     * @author Edoardo Margarini
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}