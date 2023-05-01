package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.interfaces.ServerNetwork;

import java.util.List;

/**
 * Receives connection requests from clients, and sends the client interface to the RoomServices.java.
 * When "disconnect" method is called, the connection must be interrupted.
 * Every action done on the ClientController interface must be forwarded to the client either as RMI or Socket calling
 * the method directly in the client on the controller.
 */
public class GameServerNetwork implements ServerNetwork {

    private final ServerController roomServices;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameServerNetwork(ServerController roomServices) {
        this.roomServices = roomServices;
        Thread startThread = new Thread(this::waitForClients);
        startThread.start();
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     */
    private void waitForClients() {
        //while(true)
        //when player connects calls roomServices.playerConnected()
    }

    @Override
    public void disconnect(List<String> names) {

    }

}
