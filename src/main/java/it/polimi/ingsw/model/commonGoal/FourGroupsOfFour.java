package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import org.jetbrains.annotations.NotNull;

/**
 * Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 *
 * @author Edoardo Margarini
 */
public class FourGroupsOfFour extends CommonGoal {

    private static final int groupsNumber = 4;

    private static final int tilesInGroup = 4;

    /**
     * @author Edoardo Margarini
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        //check
        return false;
    }

}
