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

    /**
     * Class constructor.
     *
     * @author Pietro Andrea Niedda
     */
    public Shelf(){
        positions = new Tile[rowNumber][columnNumber];
        for(int i = 0; i < rowNumber; i++)
            for(int j = 0; j < columnNumber; j++)
                positions[i][j] = Tile.EMPTY;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public static int getRowNumber(){
        return rowNumber;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public static int getColumnNumber(){
        return columnNumber;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public Tile[][] getPositions() {
        return positions;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param coordinates
     * @author Pietro Andrea Niedda
     */
    public Tile getPosition(@NotNull Coordinates coordinates) {
        return positions[coordinates.x()][coordinates.y()];
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param column
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    public boolean insertInColumn(List<Tile> tiles, int column) {int index = 1;

        int index = 1;

        while(index < rowLength + 1 && positions[index-1][column] == Tile.EMPTY) index++;

        for(int i = 0; i < tiles.size(); i++){
            positions[i][column] = tiles.get(i);
            index--;
        }

        return true;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param column
     * @param selectionLength
     * @author Pietro Andrea Niedda
     */
    private boolean checkSpaceInColumn(int selectionLength, int column) {
        int availableSpace=0;
        for(int i = 0; i < rowNumber; i++)
            if(positions[i][column] != Tile.EMPTY) availableSpace = rowNumber -i;

        if(availableSpace < selectionLength) return false;
        return true;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @param column
     * @author Pietro Andrea Niedda
     */
    public int getTilesInColumn(int column){
        for(int i = 0; i < rowNumber; i++)
            if(positions[i][column] != Tile.EMPTY) return rowNumber -i;
        return rowNumber;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    public boolean isFull(){
        for(int i = 0; i < rowNumber; i++)
            if(positions[i][0] == null) return false;
        return true;
    }

}


