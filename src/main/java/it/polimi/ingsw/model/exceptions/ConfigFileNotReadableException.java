package it.polimi.ingsw.model.exceptions;

/**
 * Exception pops out if the file trying to be read contains no desired value.
 *
 * @author Francesco Ostidich
 */
public class ConfigFileNotReadableException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public ConfigFileNotReadableException(String s) {
        System.out.println("ConfigFileNotFoundException: " + s + "!");
    }

}
