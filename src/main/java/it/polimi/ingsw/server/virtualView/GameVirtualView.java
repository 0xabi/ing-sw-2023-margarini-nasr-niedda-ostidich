package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.interfaces.NetworkManager;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.serverController.VirtualViewAccess;

/**
 * It receives from server controller the messages to be sent on the connection bus, from server to client.
 * From the clients receives messages to forward to the server controller.
 *
 * @author Francesco Ostidich
 */
public class GameVirtualView implements NetworkManager {

    private final VirtualViewAccess virtualViewAccess;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param virtualViewAccess manages the bridge between virtual view and server controller
     */
    public GameVirtualView(VirtualViewAccess virtualViewAccess) {
        this.virtualViewAccess = virtualViewAccess;
    }

    @Override
    public void send(Message message) {

    }

}
