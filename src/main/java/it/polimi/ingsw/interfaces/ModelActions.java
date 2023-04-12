package it.polimi.ingsw.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Defines all the actions doable on the model.
 *
 * @author Francesco Ostidich
 */
public interface ModelActions {

    /**
     * <p>When the turn cycle is finished, and at least a shelf is filled, points are being processed and sent to the controller
     * in a map.</p>
     * <p>Order of actions to calculate points:<br>
     * - personal goals are calculated<br>
     * - adjacent tiles goals are calculated</p>
     * <p>Controller should call method GameModel.getTurnCycleOrder() if there's a tie scenario, in order to
     * choose a winner</p>
     * @author Francesco Ostidich
     * @return the map with players' names as keys and respective points as values
     */
    Map<String, Integer> calculatePoints();

    /**
     * <p>Used in case of a tie</p>
     * <p>Returns the list of players' names based on the turn cycle order</p>
     *
     * @author Francesco Ostidich
     * @return the list of players' names sorted as the turn cycle was played
     */
    List<String> getTurnCycleOrder();

}
