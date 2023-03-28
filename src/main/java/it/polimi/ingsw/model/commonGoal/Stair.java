package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;

/**
 * Five columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 *
 * @author Pietro Andrea Niedda
 */
public class Stair extends CommonGoal {

    private static final int chosenColumn = 0;

    /**
     * <p>Implements abstract method of CommonGoal.</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(Shelf shelf) {

        if(shelf.getTilesInColumn(0) == 5) return ReverseStairs(0, shelf);
        else if (shelf.getTilesInColumn(0) == 1) return Stairs(0, shelf);

        return false;
    }

    private boolean Stairs(int column, Shelf shelf){

        if (column == shelf.getColumn()-1) return true;
        if(shelf.getTilesInColumn(column) > shelf.getTilesInColumn(column+1)) return Stairs(column+1, shelf);

        return false;
    }

    private boolean ReverseStairs(int column, Shelf shelf){

        if (column == shelf.getColumn()-1) return true;
        if(shelf.getTilesInColumn(column) < shelf.getTilesInColumn(column+1)) return Stairs(column+1, shelf);

        return false;
    }

}
