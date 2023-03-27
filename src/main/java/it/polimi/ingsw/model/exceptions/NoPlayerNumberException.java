package it.polimi.ingsw.model.exceptions;

public class NoPlayerNumberException extends RuntimeException {

    public NoPlayerNumberException(String s) {
        System.out.println("NoPlayerNumberException: " + s + "!");
    }

}
