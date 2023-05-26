package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

/**
 * Five columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 *
 * @author Pietro Andrea Niedda
 */
public class Stair extends CommonGoal {

    private static final String commonGoalName = "Stair";

    public Stair(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        if(shelf.getTilesInColumn(0) == 0 || shelf.getTilesInColumn(0) == 5)
            return(Stairs(0, shelf) || ReverseStairs(Shelf.getRowLength() - 1, shelf));
        return false;
    }

    /**
     * Recursively checks next column if one shorter.
     *
     * @param column is the one to be checked with the next
     * @param shelf  is the player shelf
     * @return true if the check succeeds
     * @author Pietro Andrea Niedda
     */
    private boolean Stairs(int column, @NotNull Shelf shelf) {
        if (column == Shelf.getRowLength() - 1) return true;
        if (shelf.getTilesInColumn(column) == shelf.getTilesInColumn(column + 1) + 1) return Stairs(column + 1, shelf);
        return false;
    }

    /**
     * Recursively checks previous column if one shorter.
     *
     * @param column is the one to be checked with the previous
     * @param shelf  is the player shelf
     * @return true if the check succeeds
     * @author Pietro Andrea Niedda
     */
    private boolean ReverseStairs(int column, @NotNull Shelf shelf) {
        if (column == 0) return true;
        if (shelf.getTilesInColumn(column) == shelf.getTilesInColumn(column - 1) + 1)
            return ReverseStairs(column - 1, shelf);
        return false;
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
