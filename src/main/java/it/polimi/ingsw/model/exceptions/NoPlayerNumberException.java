package it.polimi.ingsw.model.exceptions;

/**
 * Exception pops out if CommonGoal.java constructor does not get a parameter.
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
