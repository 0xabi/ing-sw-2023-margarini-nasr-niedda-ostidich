package it.polimi.ingsw.client.networkHandler;

import it.polimi.ingsw.client.clientController.ViewAccess;

/**
 * The controller sets the View object to send to the server IP, so it can take full control over it. A method remains opened
 * until match is finished.
 * There are two methods the controller can use, one for RMI and one for Socket.
 * In case of RMI connection, the viewAccess object is directly sent; in case of Socket connection, server receives a virtualView
 * object to treat in the same way. The virtualView calls the same methods and sends packets; when client receives them should
 * call the respective action on the view object it has saved.
 */
public class GameNetworkHandler{

    private final String serverIP;

    private final String playerName;

    private final ViewAccess view;

    /**
     * Class constructor.
     *
     * @param serverIP   is the IP the client is trying to connect to
     * @param playerName is the player's name string
     * @param view       is the view that the server wants to take over
     * @author Francesco Ostidich
     */
    public GameNetworkHandler(String serverIP, String playerName, ViewAccess view) {
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.view = view;
    }

    /**
     * This method do not return until the match is finished.
     * It has to contain all the actions permitting the server to get the RMI and have it play directly the game on the client.
     * When game ends server should notify the client to close this method.
     * The RMI passed it's directly the object giving power over the view.
     */
    public void connectionRMIToServer() {

    }

    /**
     * This method do not return until the match is finished.
     * It has to contain all the actions permitting the server to get a VirtualView object and have it play directly the game on the client.
     * When game ends server should notify the client to close this method.
     * When a method is called on the VirtualView it send respective message on Socket connection, and this very class
     * is to call the same method on the ViewAccess class as attribute.
     */
    public void connectionSocketToServer() {

    }


}
