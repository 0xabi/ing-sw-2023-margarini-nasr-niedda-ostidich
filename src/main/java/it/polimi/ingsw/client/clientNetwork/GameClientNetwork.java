package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.interfaces.ClientControllerToNetwork;

/**
 * The controller sets the View object to send to the server IP, so it can take full control over it. A method remains opened
 * until match is finished.
 * There are two methods the controller can use, one for RMI and one for Socket.
 * In case of RMI connection, the viewAccess object is directly sent; in case of Socket connection, server receives a virtualView
 * object to treat in the same way. The virtualView calls the same methods and sends packets; when client receives them should
 * call the respective action on the view object it has saved.
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
