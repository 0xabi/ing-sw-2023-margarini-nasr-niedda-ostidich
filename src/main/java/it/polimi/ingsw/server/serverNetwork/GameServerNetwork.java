package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.interfaces.ServerControllerToNetwork;
import it.polimi.ingsw.server.serverController.LobbyBuilder;

import java.util.List;

/**
 * Receives connection requests from clients and builds game rooms.
 * When a game room is ready to play, it should be received by the lobby builder which starts the game.
 */
public class GameServerNetwork implements ServerControllerToNetwork {

    private final LobbyBuilder lobbyBuilder;

    private final Thread startThread;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameServerNetwork(LobbyBuilder lobbyBuilder) {
        this.lobbyBuilder = lobbyBuilder;
        startThread = new Thread(this::start);
        startThread.start();
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     */
    public void start() {
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void endGame(List<String> names) {

    }

}
