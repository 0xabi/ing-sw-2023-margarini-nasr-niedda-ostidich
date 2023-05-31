package it.polimi.ingsw.general.exceptions;

/**
 * Exception pops out if the config file is not to be found.
 *
 * @author Francesco Ostidich
 */
public class ConfigFileNotFoundException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public ConfigFileNotFoundException(String s) {
        System.out.println("ConfigFileNotFoundException: " + s + "!");
    }

}
