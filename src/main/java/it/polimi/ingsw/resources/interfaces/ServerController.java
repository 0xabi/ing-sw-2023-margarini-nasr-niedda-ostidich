package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;
import java.util.Map;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ServerController {

    /**
     * When a game room is full, match is to be started.
     *
     * @author Francesco Ostidich
     * @param names is the list of players' name
     */
    void startGame(Map<String, ClientController> names);

    /**
     * When player is declared disconnected, controller is notified by network manager.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has disconnected
     */
    void disconnectedPlayer(String playerName);

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
     * @param room is the room selected
     */
    void joinRoom(String room);

    /**
     * Player wants to create a new room providing information needed.
     *
     * @author Francesco Ostidich
     * @param newRoomName is the name of the new room
     * @param playerNumber is the player number of the new room
     */
    void createNewRoom(String newRoomName, int playerNumber);

    /**
     * Player asks for available rooms to be joined.
     *
     * @author Francesco Ostidich
     */
    void askForRooms();

}
