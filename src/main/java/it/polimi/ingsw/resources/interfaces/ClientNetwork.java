package it.polimi.ingsw.resources.interfaces;

/**
 * Action doable from controller to network.
 *
 * @author Francesco Ostidich
 */
public interface ClientNetwork {

    /**
     * Passes all information required to build the connection to the server.
     *
     * @return server as method callable on it
     * @param serverIP is the server IP
     * @param playerName is the player's name
     * @param view is the view to pass to the server
     * @author Francesco Ostidich
     */
    ServerController connect(String serverIP, String playerName, ClientController view);

    void joinRoom(String room);

    void createNewRoom(String newRoomName, int playerNumber);

    void askForRooms();

}
