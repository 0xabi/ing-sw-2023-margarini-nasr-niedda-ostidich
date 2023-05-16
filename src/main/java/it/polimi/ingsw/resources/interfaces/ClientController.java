package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.messages.*;

/**
 * Groups all the action doable to the client controller
 *
 * @author Francesco Ostidich
 */
public interface ClientController {

    /**
     * Takes an event to be processed.
     *
     * @author Francesco Ostidich
     * @param event is the view event sent
     */
    void update(Event event) throws Exception;

    /**
     * If parameters passed to the client controller are unavailable, view is asked to restart.
     *
     * @author Francesco Ostidich
     */
    void restart() throws Exception;

    /**
     * If client disconnects from server the view must tell it.
     *
     * @author Francesco Ostidich
     */
    void disconnectedFromServer();

    void serverConnected() throws Exception;

    /**
     * If game room name is not available, it asks again for it.
     *
     * @author Francesco Ostidich
     */
    void roomNameNotAvailable(RoomNameNotAvailable message) throws Exception;

    /**
     * When client asks to join a room, the list of rooms is to be sent.
     *
     * @author Francesco Ostidich
     * @param message contains the game rooms list
     */
    void showRooms(ShowRooms message) throws Exception;

    /**
     * When client asks to create a room, the room is to be updated when requested.
     *
     * @author Francesco Ostidich
     * @param message contains the game room information
     */
    void showPersonalRoom(ShowPersonalRoom message);

    /**
     * When game starts, client is updated on every entry information it needs.
     *
     * @author Francesco Ostidich
     * @param message contains all starting game information
     */
    void notifyGameHasStarted(NotifyGameHasStarted message) throws Exception;

    /**
     * When current player ends it turn, all client are updated.
     * If game is ending, method to close the game are called.
     *
     * @author Francesco Ostidich
     * @param message contains all information to update
     */
    void newTurn(NewTurn message) throws Exception;

    /**
     * After choosing coordinates on the board, if they are legitimate,
     * the pick is accepted and player is asked for next information.
     *
     * @author Francesco Ostidich
     * @param message contains chosen tiles' list
     */
    void pickAccepted(PickAccepted message) throws Exception;

}
