package it.polimi.ingsw.resources.exceptions;

/**
 * Exception pops out if Message.content() method is called on a message not containing a single object as content.
 *
 * @author Francesco Ostidich
 */
public class UnavailableContentSizeException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public UnavailableContentSizeException(String s) {
        System.out.println("UnavailableContentSizeException: " + s + "!");
    }

}
