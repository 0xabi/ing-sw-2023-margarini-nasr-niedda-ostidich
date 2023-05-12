package it.polimi.ingsw.resources.exceptions;

/**
 * Exception pops out if all player are declared disconnected whilst setting the turn of the next player.
 *
 * @author Francesco Ostidich
 */
public class AllPlayerDisconnectedException extends Exception {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public AllPlayerDisconnectedException(String s) {
        System.out.println("AllPlayerDisconnectedException: " + s + "!");
    }

}
