package it.polimi.ingsw.resources.interfaces;

/**
 * Action doable from controller to network.
 *
 * @author Francesco Ostidich
 */
public interface ClientControllerToNetwork {

    /**
     * This method do not return until the match is finished.
     * It has to contain all the actions permitting the server to get the RMI and have it play directly the game on the client.
     * When game ends server should notify the client to close this method.
     * The RMI passed it's directly the object giving power over the view.
     *
     * @author Francesco Ostidich
     */
    void connectionRMIToServer();

    /**
     * This method do not return until the match is finished.
     * It has to contain all the actions permitting the server to get a VirtualView object and have it play directly the game on the client.
     * When game ends server should notify the client to close this method.
     * When a method is called on the VirtualView it send respective message on Socket connection, and this very class
     * is to call the same method on the ViewAccess class as attribute.
     *
     * @author Francesco Ostidich
     */
    void connectionSocketToServer();

}
