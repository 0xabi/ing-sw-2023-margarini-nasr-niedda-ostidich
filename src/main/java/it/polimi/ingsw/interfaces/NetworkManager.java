package it.polimi.ingsw.interfaces;

import it.polimi.ingsw.messages.Message;

/**
 * Collects the methods to call when data is to be sent via Socket or RMI.
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
    void send(Message message);

}
