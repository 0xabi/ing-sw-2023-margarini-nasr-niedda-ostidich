package it.polimi.ingsw.model.exceptions;

/**
 * Exception pops out if the common goal number that is being extracted does not couple with a common goal in the factory.
 *
 * @author Francesco Ostidich
 */
public class CommonGoalNotPresentException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public CommonGoalNotPresentException(String s) {
        System.out.println("CommonGoalNotPresentException: " + s + "!");
    }

}
