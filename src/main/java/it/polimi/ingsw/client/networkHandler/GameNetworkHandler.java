package it.polimi.ingsw.client.networkHandler;

import it.polimi.ingsw.client.clientController.NetworkHandlerAccess;
import it.polimi.ingsw.resources.interfaces.NetworkManager;
import it.polimi.ingsw.resources.Message;

/**
 * It receives from client controller the messages to be sent on the connection bus, from client to server.
 * From the server receives messages to forward to the client controller.
 *
 * @author Francesco Ostidich
 */
public class GameNetworkHandler implements NetworkManager {

    private final NetworkHandlerAccess networkHandlerAccess;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param networkHandlerAccess manages the bridge between network handler and client controller
     */
    public GameNetworkHandler(NetworkHandlerAccess networkHandlerAccess) {
        this.networkHandlerAccess = networkHandlerAccess;
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public Message retrieveFromQueue() {
        return null;
    }

}
