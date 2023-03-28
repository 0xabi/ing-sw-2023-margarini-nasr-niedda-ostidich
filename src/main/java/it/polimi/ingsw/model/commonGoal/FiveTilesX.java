package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;

/**
 * Five tiles of the same type forming an X.
 *
 * @author Francesco Ostidich
 */
public class FiveTilesX extends CommonGoal {

    /**
     * <p>Implements abstract method of CommonGoal.</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Francesco Ostidich
     */
    @Override
    public boolean check(Shelf shelf) {
        //check
        return false;
    }

}
