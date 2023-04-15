package it.polimi.ingsw.resources.exceptions;

/**
 * Exception pops out if there's no player name string provided in any players of players list.
 *
 * @author Francesco Ostidich
 */
public class PlayerNotFoundException extends RuntimeException {

    /**
     * Prints out exception's message.
     *
     * @author Francesco Ostidich
     * @param s is the string got from exception source
     */
    public PlayerNotFoundException(String s) {
        System.out.println("PlayerNotFoundException: " + s + "!");
    }

}
