package it.polimi.ingsw.server.virtualView;

import it.polimi.ingsw.resources.interfaces.ViewActions;
import java.util.Map;

/**
 * Receives connection requests from clients and builds game rooms.
 * When a game room is ready to play, it should be received by the lobby builder which starts the game.
 */
public class GameVirtualView {

    private Map<String, ViewActions> gameRoomReady;

    private final Thread startThread;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameVirtualView() {
        startThread = new Thread(this::start);
        startThread.start();
    }

    /**
     * Method called by main method. When a game room is filled its passed up by this method.
     * This method is always active on a separate thread.
     *
     * @return client name and view object map
     */
    public Map<String, ViewActions> waitForClients() {
        synchronized(this) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
            return gameRoomReady;
        }
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     */
    public void start() {
        //noinspection InfiniteLoopStatement
        while(true) {
            //manage game room requests from clients
            //when a game room is ready and put in the attribute ->
            synchronized(this) {
                this.notifyAll();
            }
        }
    }

}
