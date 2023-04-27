package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.interfaces.ClientControllerToNetwork;

/**
 * When a server message is received, based on player name and on message ID, the right method is called on the view
 * and waited for return data, to pack and send back.
 * In case of the "justScanChat" method, when called from the server, a dedicated thread is started so method can always be up and
 * running.
 */
public class GameClientNetwork implements ClientControllerToNetwork {

    private final String serverIP;

    private final String playerName;

    private final GameView gameView;

    /**
     * Class constructor.
     *
     * @param serverIP   is the IP the client is trying to connect to
     * @param playerName is the player's name string
     * @param gameView   is the view that the server wants to take over
     * @author Francesco Ostidich
     */
    public GameClientNetwork(String serverIP, String playerName, GameView gameView) {
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.gameView = gameView;
    }

    @Override
    public void connectionRMIToServer() {

    }

    @Override
    public void connectionSocketToServer() {

    }


}
