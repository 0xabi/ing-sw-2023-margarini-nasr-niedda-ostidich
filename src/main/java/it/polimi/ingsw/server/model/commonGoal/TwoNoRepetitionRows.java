package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionRows extends CommonGoal {

    private static final int TIMES = 2;

    private static final String commonGoalName = "TwoNoRepetitionRows";

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     *
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
     *
     * @param shelf the player's shelf
     * @param row   the row in which to check repetition
     * @return true if the number of different tiles in front of each other in the row is equal to the number {@code NUM_DIFFERENT_TILES}, false otherwise
     * @author Abdullah Nasr
     */
    private boolean checkRow(@NotNull Shelf shelf, int row) {
        Set<Tile> checked = new HashSet<>();
        for (int i = 0; i < Shelf.getRowLength(); i++) {
            checked.add(shelf.getPositions()[i][row]);
        }
        return checked.size() == Shelf.getRowLength();
    }

    /**
     * <p>
     * count the number of row that have the number of different tiles in front of each other equal to the number {@code NUM_DIFFERENT_TILES}
     * </p>
     *
     * @param shelf the player's shelf
     * @return the number of rows that satisfy the pattern
     * @author Abdullah Nasr
     */
    private int countRowsWithDifferentTiles(@NotNull Shelf shelf) {
        int count = 0;
        int row = Shelf.getColumnLength();
        //for each row
        for (int i = 0; i < row; i++) {
            if (checkRow(shelf, i)) {
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
        return countRowsWithDifferentTiles(shelf) >= TIMES;
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
