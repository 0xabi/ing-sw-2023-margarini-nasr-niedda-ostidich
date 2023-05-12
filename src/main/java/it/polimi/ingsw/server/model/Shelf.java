package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The shelf is a matrix of tiles. Common goals can read the shelf to execute the check. Player cannot pick more tiles
 * than they can insert. It's also checked whether the shelf is full in order to give the end game token points to the first
 * who has it filled.
 *
 * @author Abdullah Nasr
 */
public class Shelf {

    private static final int ROW_NUMBER = 6;

    private static final int COLUMN_NUMBER = 5;

    private final Tile[][] positions;

    /**
     * Class constructor.
     *
     * @author Abdullah Nasr
     */
    public Shelf() {
        positions = new Tile[ROW_NUMBER][COLUMN_NUMBER];
        for (int i = 0; i < ROW_NUMBER; i++)
            Arrays.fill(positions[i], Tile.EMPTY);
    }

    /**
     * @return the shelf's row number
     * @author Abdullah Nasr
     */
    public static int getRowNumber() {
        return ROW_NUMBER;
    }

    /**
     * @return the shelf's column number
     * @author Abdullah Nasr
     */
    public static int getColumnNumber() {
        return COLUMN_NUMBER;
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
    public Tile getPosition(Coordinates coordinates) {

        if (coordinates == null || coordinates.x() >= ROW_NUMBER || coordinates.y() >= COLUMN_NUMBER)
            return null;

        return positions[coordinates.x()][coordinates.y()];
    }

    /**
     * @param column The column number to insert into the specified tiles
     * @param tiles  A list of tiles to insert into the specified column
     * @return true for success insertion of tiles into the specified column, false otherwise
     * @author Abdullah Nasr
     */
    public boolean insertInColumn(List<Tile> tiles, int column) {

        List<Tile> tilesList;
        int freeSpace;

        //check input
        if (tiles == null || tiles.contains(Tile.EMPTY) || column >= COLUMN_NUMBER) {
            return false;
        }

        tilesList = new ArrayList<>(tiles);

        //calculate free space
        freeSpace = checkSpaceInColumn(tilesList.size(), column);

        //if there is enough space to put the tiles
        if (freeSpace != 0) {
            while (!tilesList.isEmpty()) {
                positions[freeSpace - 1][column] = tilesList.get(0);
                tilesList.remove(0);
                freeSpace--;
            }

            return true;

        }

        return false;
    }

    /**
     * @param column          the shelf's column selected to insert the tiles
     * @param selectionLength the number of tiles to insert into the selected column
     * @return the number of free slots, 0 if there isn't enough free slots to put all the tiles
     * @author Abdullah Nasr
     */
    private int checkSpaceInColumn(int selectionLength, int column) {

        int emptySlots = ROW_NUMBER - getTilesInColumn(column);

        if (emptySlots >= selectionLength)
            return emptySlots;

        return 0;
    }

    /**
     * @param column the column in which to count the tiles
     * @return the number of the tiles present in the specified column
     * @author Abdullah Nasr
     */
    public int getTilesInColumn(int column) {

        //check valid column
        if (column < COLUMN_NUMBER) {
            //count empty spaces
            for (int i = 0; i < ROW_NUMBER; i++)
                if (positions[i][column] != Tile.EMPTY) return ROW_NUMBER - i;

        }

        return 0;
    }

    /**
     * @return true if the shelf is full, false otherwise
     * @author Abdullah Nasr
     */
    protected boolean isFull() {
        for (int i = 0; i < COLUMN_NUMBER; i++)
            if (positions[0][i] == Tile.EMPTY) return false;
        return true;
    }

}


