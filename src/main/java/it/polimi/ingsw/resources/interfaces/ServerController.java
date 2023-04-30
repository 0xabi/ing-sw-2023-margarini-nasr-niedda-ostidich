package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ServerController {

    /**
     * When player is declared disconnected, controller is notified.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has disconnected
     */
    void disconnected(String playerName);


    void pickTilesRequest(Message message);

    void insertTilesRequest(Message message);
}
