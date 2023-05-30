package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.messages.*;
import org.jetbrains.annotations.NotNull;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Groups all the action doable to the client controller
 *
 * @author Francesco Ostidich
 */
public interface ClientController extends Remote {


    /**
     * Takes an event to be processed.
     *
     * @param event is the view event sent
     * @author Francesco Ostidich
     */
    void update(Event event) throws RemoteException;

    /**
     * If parameters passed to the client controller are unavailable, view is asked to restart.
     *
     * @author Francesco Ostidich
     */
    void restart() throws RemoteException;

    /**
     * If client disconnects from server the view must tell it.
     *
     * @author Francesco Ostidich
     */
    void disconnectedFromServer() throws RemoteException;

    /**
     * When server is connected, player's asked to join or create a new room.
     *
     * @author Francesco Ostidich
     */
    void serverConnected() throws RemoteException;

    /**
     * If game room name is not available, it asks again for it.
     *
     * @author Francesco Ostidich
     */
    void roomNameNotAvailable(RoomNameNotAvailable message) throws RemoteException;

    /**
     * When client asks to join a room, the list of rooms is to be sent.
     *
     * @param message contains the game rooms list
     * @author Francesco Ostidich
     */
    void showRooms(ShowRooms message) throws RemoteException;

    /**
     * When client asks to create a room, the room is to be updated when requested.
     *
     * @param message contains the game room information
     * @author Francesco Ostidich
     */
    void showPersonalRoom(ShowPersonalRoom message) throws RemoteException;

    /**
     * When game starts, client is updated on every entry information it needs.
     *
     * @param message contains all starting game information
     * @author Francesco Ostidich
     */
    void notifyGameHasStarted(NotifyGameHasStarted message) throws RemoteException;

    /**
     * When current player ends it turn, all client are updated.
     * If game is ending, method to close the game are called.
     *
     * @param message contains all information to update
     * @author Francesco Ostidich
     */
    void newTurn(NextPlayer message) throws RemoteException;

    /**
     * When current player ends it turn, all client are updated.
     * If game is ending, method to close the game are called.
     *
     * @param message contains all information to update
     * @author Francesco Ostidich
     */
    void newTurn(EndGame message) throws RemoteException;

    /**
     * After choosing coordinates on the board, if they are legitimate,
     * the pick is accepted and player is asked for next information.
     *
     * @param message contains chosen tiles' list
     * @author Francesco Ostidich
     */
    void pickAccepted(PickAccepted message) throws RemoteException;

    void endGame(@NotNull EndGame msg) throws RemoteException;
}
