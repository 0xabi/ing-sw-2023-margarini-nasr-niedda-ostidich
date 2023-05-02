package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;
import java.util.Set;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ServerController {

    /**
     * In order to discard connection from player with name already registered, the method
     * tells all the players' name in the rooms.
     *
     * @author Francesco Ostidich
     * @return players' names string set
     */
    Set<String> onlinePlayers();

    /**
     * When player connects, method is called in order to add it to room services.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @param client is the client controller interface of the player
     */
    void playerConnected(String playerName, ClientController client);

    /**
     * When player is declared disconnected, controller is notified by network manager.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has disconnected
     */
    void disconnectedPlayer(String playerName);

    /**
     * When player is declared reconnected, controller is notified by network manager.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has reconnected
     */
    void reconnectedPlayer(String playerName);

    /**
     * When player wants to pick tiles it sends request to server.
     *
     * @author Francesco Ostidich
     * @param message is the request message
     */
    void pickTilesRequest(Message message);

    /**
     * When player selects order and column, asks the server for insertion.
     *
     * @author Francesco Ostidich
     * @param message is the request message
     */
    void insertTilesRequest(Message message);

    /**
     * Player selects a room to join.
     *
     * @author Francesco Ostidich
     * @param message is the client message
     */
    void joinRoom(Message message);

    /**
     * Player wants to create a new room providing information needed.
     *
     * @author Francesco Ostidich
     * @param message is the client message
     */
    void createNewRoom(Message message);

    /**
     * Player asks for available rooms to be joined.
     *
     * @author Francesco Ostidich
     */
    void askForRooms(Message message);

}
