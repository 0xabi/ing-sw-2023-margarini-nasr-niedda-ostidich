package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.messages.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ServerController extends Remote {

    /**
     * In order to discard connection from player with name already registered, the method
     * tells all the players' name in the rooms.
     *
     * @author Francesco Ostidich
     * @return players' names string set
     */
    Set<String> onlinePlayers() throws RemoteException;

    /**
     * When player connects, method is called in order to add it to room services.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @param client is the client controller interface of the player
     */
    void playerConnected(String playerName, ClientController client)  throws RemoteException;

    /**
     * When player is declared disconnected, controller is notified by network manager.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has disconnected
     */
    void disconnectedPlayer(String playerName) throws RemoteException;

    /**
     * When player is declared reconnected, controller is notified by network manager.
     *
     * @author Francesco Ostidich
     * @param playerName is the player that has reconnected
     */
    void reconnectedPlayer(String playerName) throws RemoteException;

    /**
     * When player wants to pick tiles it sends request to server.
     *
     * @author Francesco Ostidich
     * @param message is the request message
     */
    void pickTilesRequest(PickTilesRequest message) throws RemoteException;

    /**
     * When player selects order and column, asks the server for insertion.
     *
     * @author Francesco Ostidich
     * @param message is the request message
     */
    void insertTilesRequest(InsertTilesRequest message) throws RemoteException;

    /**
     * Player selects a room to join.
     *
     * @author Francesco Ostidich
     * @param message is the client message
     */
    void joinRoom(JoinRoom message) throws RemoteException;

    /**
     * Player wants to create a new room providing information needed.
     *
     * @author Francesco Ostidich
     * @param message is the client message
     */
    void createNewRoom(CreateNewRoom message) throws RemoteException;

    /**
     * Player asks for available rooms to be joined.
     *
     * @author Francesco Ostidich
     */
    void askForRooms(AskForRooms message) throws RemoteException;

    boolean PlayerIDisAvailable(Hello msg) throws RemoteException;
}
