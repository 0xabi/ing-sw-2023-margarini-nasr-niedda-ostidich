package it.polimi.ingsw.model.exceptions;

public class CommonGoalNotPresentException extends RuntimeException {

    public CommonGoalNotPresentException(String s) {
        System.out.println("CommonGoalNotPresentException: " + s + "!");
    }
}
