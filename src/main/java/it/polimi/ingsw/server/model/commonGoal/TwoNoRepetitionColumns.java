package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Two columns each formed by 6 different types of tiles.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionColumns extends CommonGoal {

    private static final int TIMES = 2;

    private static final String commonGoalName = "TwoNoRepetitionColumns";

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     *
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
     *
     * @param shelf the player's shelf
     * @param col   the column in which to check repetition
     * @return true if the number of different tiles in front of each other in the column is equal to the number {@code NUM_DIFFERENT_TILES}, false otherwise
     * @author Abdullah Nasr
     */
    private boolean checkCol(@NotNull Shelf shelf, int col) {
        Set<Tile> checked = new HashSet<>();
        for (int i = 0; i < Shelf.getColumnLength(); i++) {
            checked.add(shelf.getPositions()[col][i]);
        }
        return checked.size() >= Shelf.getColumnLength();
    }

    /**
     * <p>
     * count the number of column that have the number of different tiles in front of each other equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     *
     * @param shelf the player's shelf
     * @return the number of column that satisfy the pattern
     * @author Abdullah Nasr
     */
    private int countColumnsWithDifferentTiles(Shelf shelf) {
        int count = 0;
        int col = Shelf.getRowLength();
        //for each column
        for (int i = 0; i < col; i++) {
            if (checkCol(shelf, i)) {
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
        return countColumnsWithDifferentTiles(shelf) >= TIMES;
    }

    /**
     * @author Adbullah Nasr
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
