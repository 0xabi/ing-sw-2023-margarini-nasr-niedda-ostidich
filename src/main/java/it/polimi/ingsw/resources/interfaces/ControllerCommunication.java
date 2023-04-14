package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

/**
 * Inside the controllers, a class receiving a message must be able to talk to the controller core, which is to
 * decide the actions based on the message itself (and clearly needs a way to get it).
 *
 * @author Francesco Ostidich
 */
public interface ControllerCommunication {

    /**
     * The message received from the network must be sent to the controller via the class talking to the network package in the controller one.
     *
     * @param message is the message to send to the controller
     * @author Francesco Ostidich
     */
    void sendToController(Message message);

}
