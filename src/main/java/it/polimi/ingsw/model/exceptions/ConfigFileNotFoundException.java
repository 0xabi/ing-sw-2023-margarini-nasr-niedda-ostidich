package it.polimi.ingsw.model.exceptions;

/**
 * Exception pops out if the files PersonalGoal.java is to open are not readable.
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
