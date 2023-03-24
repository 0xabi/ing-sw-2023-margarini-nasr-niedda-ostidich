package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoal.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameModel {

    private final Board board = new Board();

    private final CommonGoal commonGoal1;

    private final CommonGoal commonGoal2;

    private final List<Player> players = new LinkedList<Player>();

    public GameModel(List<String> names) {
        //random choosing of 2 of 12 commonGoal (maybe factory methods pattern)
        commonGoal1 = new CommonGoal1();
        commonGoal2 = new CommonGoal2();
    }

    public boolean turnCycle() {
        //make every player do a turn
        return false;
    }

    public Map<String, Integer> calculatePoints() {
        //return a map with points for every player's name
        //remember to make a decision in a tie game
        return null;
    }

}
