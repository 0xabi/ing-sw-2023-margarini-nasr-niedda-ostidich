package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

import java.util.List;

/**
 * Action doable from controller to network.
 *
 * @author Francesco Ostidich
 */
public interface ServerControllerToNetwork {

   /**
    * Controller calls method to send message on network.
    *
    * @author Francesco Ostidich
    * @param message is the message to send
    */
   void sendMessage(Message message);

   /**
    * When game ends, client is to be notified.
    *
    * @author Francesco Ostidich
    * @param names is the match's players' name string list
    */
   void endGame(List<String> names);

}
