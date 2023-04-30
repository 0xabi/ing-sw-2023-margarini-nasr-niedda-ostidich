package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.interfaces.ServerNetwork;

import java.util.List;

/**
 * Receives connection requests from clients and builds game rooms.
 * When a game room is ready to play, it should be received by the lobby builder which starts the game.
 */
public class GameServerNetwork implements ServerNetwork {

    private final Thread startThread;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameServerNetwork() {
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
    public void endGame(List<String> names) {

    }

}
