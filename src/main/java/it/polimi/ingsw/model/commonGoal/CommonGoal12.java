package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;

public class CommonGoal12 extends CommonGoal {

    private static final int chosenColumn = 0;

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
