package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ClientNetwork;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.server.serverController.RoomServices;

/**
 * Asks for connection to the server, and wants the ServerController interface to call methods on it.
 * When "connect" method is called, the connection must be built.
 * Every action done on the ServerController interface must be forwarded to the server either as RMI or Socket calling
 * the method directly in the server on the controller.
 */
public class GameClientNetwork implements ClientNetwork {

    private final String connectionType;

    private ClientController controller;

    private String serverIP;

    private String playerName;

    /**
     * Class constructor.
     *
     * @param connectionType is the type of connection
     * @author Francesco Ostidich
     */
    public GameClientNetwork(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public ServerController connect(String serverIP, String playerName, ClientController controller) {
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.controller = controller;
        //to tweak:
        ServerController serverController = new RoomServices();
        if(serverController.onlinePlayers().contains(playerName)) {
            controller.restart();
            return null;
        }
        return serverController;
    }

}
