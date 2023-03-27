package it.polimi.ingsw.model.exceptions;

public class CommonGoalNotPresentException extends IllegalStateException {

    public CommonGoalNotPresentException(String s) {
        System.out.println("CommonGoalNotPresentException: " + s + "!");
    }
}
