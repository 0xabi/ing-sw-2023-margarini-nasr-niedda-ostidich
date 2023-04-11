package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionRows extends CommonGoal {

    private static final int numDifferentTiles = 5;

    private static final int times = 2;

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     * @param playerNumber is the number of player of the game match
     * @author Abdullah Nasr
     */
    public TwoNoRepetitionRows(int playerNumber) {
        super(playerNumber);
    }

    /**
     * <p>
     * check if the number of different tiles in front of each other in the column is equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     * @param shelf the player's shelf
     * @param row the row in which to check repetition
     * @return true if the number of different tiles in front of each other in the row is equal to the number {@code NUM_DIFFERENT_TILES}, false otherwise
     * @author Abdullah Nasr
     */
    @SuppressWarnings("DuplicatedCode")
    private boolean checkRow(@NotNull Shelf shelf, int row) {
        ArrayList<Tile> checkedList = new ArrayList<>();
        int col = shelf.getColumnNumber();
        boolean different = true;
        int nDifferent =0;
        int index=0;
        Tile current;

        current = shelf.getPosition(new Coordinates(row,index));
        while(current == Tile.EMPTY) {
            index++;
            current = shelf.getPosition(new Coordinates(row,index));
        }

        //can't count num_different_tiles
        if(col-index < numDifferentTiles)
            return false;

        for(;index<col;index++) {
            current = shelf.getPosition(new Coordinates(row,index));
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
     *  count the number of row that have the number of different tiles in front of each other equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     * @param shelf the player's shelf
     * @return the number of rows that satisfy the pattern
     * @author Abdullah Nasr
     */
    private int countRowsWithDifferentTiles(@NotNull Shelf shelf) {
        int count = 0;
        int row = shelf.getRowNumber();

        //for each row
        for(int i = 0; i < row; i++) {
            if(checkRow(shelf, i)) {
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
        return countRowsWithDifferentTiles(shelf) >= times;
    }

}
