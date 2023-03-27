package it.polimi.ingsw.model.exceptions;

public class ConfigFileNotFoundException extends RuntimeException {

    public ConfigFileNotFoundException(String s) {
        System.out.println("ConfigFileNotFoundException: " + s + "!");
    }

}
