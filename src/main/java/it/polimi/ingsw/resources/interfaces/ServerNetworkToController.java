package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

import java.util.List;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ServerNetworkToController {

    /**
     * When a game room is full, match is to be started.
     *
     * @author Francesco Ostidich
     * @param names is the list of players' name
     */
    void startGame(List<String> names);

    /**
     * After receiving a message, network handler sends it to the controller.
     *
     * @author Francesco Ostidich
     * @param message is the message received
     */
    void doAction(Message message);

    /**
     * When player is declared disconnected, controller is notified.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has disconnected
     */
    void disconnected(String playerName);

}
