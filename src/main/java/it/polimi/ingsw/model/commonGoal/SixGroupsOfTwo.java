package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import org.jetbrains.annotations.NotNull;

/**
 * Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 *
 * @author Edoardo Margarini
 */
public class SixGroupsOfTwo extends CommonGoal {


    private static final int groupsNumber = 6;

    private static final int tilesInGroup = 2;

    /**
     * @author Edoardo Margarini
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        //TODO check method is to be written
        return false;
    }

}
