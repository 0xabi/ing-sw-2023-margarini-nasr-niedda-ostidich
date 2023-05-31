package it.polimi.ingsw.general.interfaces;

import java.util.List;

/**
 * Action doable from controller to network.
 *
 * @author Francesco Ostidich
 */
public interface ServerNetwork {

   /**
    * When game ends, client is to be notified.
    *
    * @author Francesco Ostidich
    * @param names is the match's players' name string list
    */
   void disconnect(List<String> names);

}
