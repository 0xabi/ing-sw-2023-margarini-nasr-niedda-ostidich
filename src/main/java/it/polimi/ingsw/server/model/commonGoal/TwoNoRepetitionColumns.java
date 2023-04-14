package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Two columns each formed by 6 different types of tiles.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionColumns extends CommonGoal {

    private static final int numDifferentTiles = 6;

    private static final int times = 2;

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     * @param playerNumber is the number of player of the game match
     * @author Abdullah Nasr
     */
    public TwoNoRepetitionColumns(int playerNumber) {
        super(playerNumber);
    }

    /**
     * <p>
     * check if the number of different tiles in front of each other in the column is equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     * @param shelf the player's shelf
     * @param col the column in which to check repetition
     * @return true if the number of different tiles in front of each other in the column is equal to the number {@code NUM_DIFFERENT_TILES}, false otherwise
     * @author Abdullah Nasr
     */
    @SuppressWarnings("DuplicatedCode")
    private boolean checkCol(@NotNull Shelf shelf, int col) {
        ArrayList<Tile> checkedList = new ArrayList<>();
        int row = shelf.getRowNumber();
        boolean different = true;
        int nDifferent =0;
        int index=0;
        Tile current;

        current = shelf.getPosition(new Coordinates(index,col));
        while(current == Tile.EMPTY) {
            index++;
            current = shelf.getPosition(new Coordinates(index,col));
        }

        //can't count num_different_tiles
        if(row-index < numDifferentTiles)
            return false;

        for(;index<row;index++) {
            current = shelf.getPosition(new Coordinates(index,col));
            for (Tile t: checkedList) {
                if (t == current) {
                    different = false;
                    break;
                }
            }
            if(different) {
                nDifferent++;
                checkedList.add(current);
            }
            else {
                nDifferent=0;
                checkedList.clear();
                checkedList.add(current);
            }
        }

        return nDifferent >= numDifferentTiles;
    }

    /**
     * <p>
     *  count the number of column that have the number of different tiles in front of each other equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     * @param shelf the player's shelf
     * @return the number of column that satisfy the pattern
     * @author Abdullah Nasr
     */
    private int countColumnsWithDifferentTiles(Shelf shelf)
    {
        int count = 0;
        int col = shelf.getColumnNumber();

        //for each column
        for(int i = 0; i < col; i++) {
            if(checkCol(shelf, i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        return countColumnsWithDifferentTiles(shelf)>= times;

    }

}
