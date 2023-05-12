package it.polimi.ingsw.resources.exceptions;

/**
 * Exception pops out if tiles cannot be inserted in player's shelf.
 *
 * @author Francesco Ostidich
 */
public class UnavailableInsertionException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public UnavailableInsertionException(String s) {
        System.out.println("UnavailableInsertionException: " + s + "!");
    }
}
