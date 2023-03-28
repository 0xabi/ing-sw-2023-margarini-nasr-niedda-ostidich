package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The shelf is a matrix of tiles. Common goals can read the shelf to execute the check. Player cannot pick more tiles
 * than they can insert. It's also checked whether the shelf is full in order to give the end game token points to the first
 * who has it filled.
 *
 * @author Pietro Andrea Niedda
 */
public class Shelf {

    private static final int rowNumber = 6;

    private static final int columnNumber = 5;

    private final Tile[][] positions;

    public Shelf(){
        positions = new Tile[rowNumber][columnNumber];
        for(int i = 0; i < rowNumber; i++)
            for(int j = 0; j < columnNumber; j++)
                positions[i][j] = null;
    }

    public static int getRowNumber(){
        return rowNumber;
    }

    public static int getColumnNumber(){
        return columnNumber;
    }

    public Tile[][] getPositions() {
        return positions;
    }

    public Tile getPosition(@NotNull Coordinates coordinates) {
        return positions[coordinates.x()][coordinates.y()];
    }

    public boolean insertInColumn(List<Tile> tiles, int column) {
        //inserts tiles in column with list order
        //uses method checkSpaceInColumn()
        return false;
    }

    private boolean checkSpaceInColumn(int selectionLength, int column) {
        //checks the remaining space in a certain column
        return false;
    }

    public int getTilesInColumn(int column){
        for(int i = 0; i < rowNumber; i++)
            if(positions[i][column] != null) return rowNumber -i;
        return rowNumber;
    }

    public boolean isFull(){
        for(int i = 0; i < rowNumber; i++)
            if(positions[i][0] == null) return false;
        return true;
    }

}


