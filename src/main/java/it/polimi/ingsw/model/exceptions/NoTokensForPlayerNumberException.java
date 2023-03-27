package it.polimi.ingsw.model.exceptions;

import java.util.NoSuchElementException;

public class NoTokensForPlayerNumberException extends RuntimeException {

    public NoTokensForPlayerNumberException(String s) {
        System.out.println("NoTokensForPlayerNumberException: " + s + "!");
    }

}