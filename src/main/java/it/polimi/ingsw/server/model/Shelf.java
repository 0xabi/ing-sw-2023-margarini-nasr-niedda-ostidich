package it.polimi.ingsw.server.model;

import it.polimi.ingsw.Debugging;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * The shelf is a matrix of tiles. Common goals can read the shelf to execute the check. Player cannot pick more tiles
 * than they can insert. It's also checked whether the shelf is full in order to give the end game token points to the first
 * who has it filled.
 *
 * @author Abdullah Nasr
 */
public class Shelf {

    private static final int ROW_LENGTH = 5;

    private static final int COLUMN_LENGTH = 6;

    private final Tile[][] positions;

    /**
     * Class constructor.
     *
     * @author Abdullah Nasr
     */
    public Shelf() {
        positions = new Tile[ROW_LENGTH][COLUMN_LENGTH];
        if (Debugging.isDebugging()) {
            Debugging.fillShelf(this);
        }
    }

    /**
     * @return the shelf's row number
     * @author Abdullah Nasr
     */
    public static int getRowLength() {
        return ROW_LENGTH;
    }

    /**
     * @return the shelf's column number
     * @author Abdullah Nasr
     */
    public static int getColumnLength() {
        return COLUMN_LENGTH;
    }

    /**
     * @return the array containing the tiles
     * @author Abdullah Nasr
     */
    public Tile[][] getPositions() {
        return positions;
    }

    /**
     * @param coordinates tile's position to pick
     * @return a tile specified by a valid coordinates , null for invalid coordinates.
     * @author Abdullah Nasr
     */
    public Tile getPosition(@NotNull Coordinates coordinates) {
        if (coordinates.x() >= ROW_LENGTH || coordinates.y() >= COLUMN_LENGTH)
            throw new IndexOutOfBoundsException("coordinates out of shelf bounds");
        return positions[coordinates.x()][coordinates.y()];
    }

    /**
     * @param column the shelf's column selected to insert the tiles
     * @return the number of free slots, 0 if there isn't enough free slots to put all the tiles
     * @author Abdullah Nasr
     */
    private int checkSpaceInColumn(int column) {
        return COLUMN_LENGTH - getTilesInColumn(column);
    }

    /**
     * @return true if the shelf is full, false otherwise
     * @author Abdullah Nasr
     */
    protected boolean isFull() {
        for (int i = 0; i < ROW_LENGTH; i++)
            if (positions[i][COLUMN_LENGTH - 1] == null) return false;
        return true;
    }

    /**
     * @param column the column in which to count the tiles
     * @return the number of the tiles present in the specified column
     * @author Abdullah Nasr
     */
    public int getTilesInColumn(int column) {
        if (column > ROW_LENGTH - 1) throw new IndexOutOfBoundsException("cant find column since out of bound");
        int i = 0;
        while (i < COLUMN_LENGTH && positions[column][i] != null) ++i;
        return i;
    }

    /**
     * @param column    The column number to insert into the specified tiles
     * @param tilesList A list of tiles to insert into the specified column
     * @return true for success insertion of tiles into the specified column, false otherwise
     * @author Abdullah Nasr
     */
    public boolean insertInColumn(@NotNull List<Tile> tilesList, int column) {
        if (tilesList.contains(Tile.EMPTY)) throw new IllegalArgumentException("shelf cant contain EMPTY");
        List<Tile> tiles = new LinkedList<>(tilesList);
        int freeSpace = checkSpaceInColumn(column);
        if (freeSpace < tiles.size()) {
            return false;
        }
        while (!tiles.isEmpty() && freeSpace > 0) {
            positions[column][COLUMN_LENGTH - freeSpace] = tiles.remove(0);
            freeSpace--;
        }
        return true;
    }

    /**
     * Used only in tests, it works a bit differently from dual "insertInColumn".
     * <p>It overrides tiles! (different from other method)</p>
     *
     * @param tiles is the tiles' list to insert
     * @param row   is the row in which insert to
     * @return true if all went right
     * @author Francesco Ostidich
     */
    public boolean insertInRow(List<Tile> tiles, int row) {
        List<Tile> tilesList = new LinkedList<>(tiles);
        if (tilesList.contains(Tile.EMPTY) || row >= COLUMN_LENGTH) {
            return false;
        }
        for (int i = 0; !tilesList.isEmpty(); i++) {
            positions[i][row] = tilesList.remove(0);
        }
        return true;
    }

}


