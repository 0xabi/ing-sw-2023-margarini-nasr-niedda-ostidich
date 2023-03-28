package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;

/**
 * Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 *
 * @author Edoardo Margarini
 */
public class SixGroupsOfTwo extends CommonGoal {

    @Override
    public boolean check(Shelf shelf) {
        //check
        return false;
    }

}
