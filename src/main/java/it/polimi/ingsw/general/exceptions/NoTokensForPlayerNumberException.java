package it.polimi.ingsw.general.exceptions;

/**
 * Exception pops out if there's no points values for the player number provided during the scoring token stack
 * construction (reading from json file).
 *
 * @author Francesco Ostidich
 */
public class NoTokensForPlayerNumberException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public NoTokensForPlayerNumberException(String s) {
        System.out.println("NoTokensForPlayerNumberException: " + s + "!");
    }

}
