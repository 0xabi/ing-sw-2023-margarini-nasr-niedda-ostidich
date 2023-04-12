package it.polimi.ingsw.server.model.exceptions;

/**
 * Exception pops out if the players number requiring action is not considered in the implication.
 *
 * @author Francesco Ostidich
 */
public class NoPlayerNumberException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public NoPlayerNumberException(String s) {
        System.out.println("NoPlayerNumberException: " + s + "!");
    }

}
