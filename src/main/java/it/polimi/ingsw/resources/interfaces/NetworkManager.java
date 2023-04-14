package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

/**
 * Collects the methods to call when data is to be sent via Socket or RMI.
 * The controller is to use Socket stream to send message objects;
 * commands that a network manager does by itself are to be called on the RMI.
 *
 * @author Francesco Ostidich
 */
public interface NetworkManager {

    /**
     * Calls methods needed to send the message via network connection.
     *
     * @author Francesco Ostidich
     * @param message is the wrapped information to send
     */
    void sendMessage(Message message);

    Message retrieveFromQueue();



}
