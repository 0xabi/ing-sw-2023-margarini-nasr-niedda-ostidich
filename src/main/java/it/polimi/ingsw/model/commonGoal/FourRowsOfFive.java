package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;

/**
 * Four lines each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 *
 * @author Francesco Ostidich
 */
public class FourRowsOfFive extends CommonGoal {

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
