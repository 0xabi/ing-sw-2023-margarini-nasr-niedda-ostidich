package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.Tile;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class NotifyGameHasStarted extends Message {

    private final Map<String, Integer> gameParameters;

    private final List<String> turnCycle;

    private final Tile[][] board;

    private final Map<Tile, Integer> bag;

    private final Stack<Integer> commonGoal1TokenStack;

    private final Stack<Integer> commonGoal2TokenStack;

    private final int personalGoal;

    private final String commonGoal1;

    private final String commonGoal2;

    public NotifyGameHasStarted(String playerName, MessageID messageID, Map<String, Integer> gameParameters, List<String> turnCycle, Tile[][] board, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, int personalGoal, String commonGoal1, String commonGoal2) {
        super(playerName, messageID);
        this.gameParameters = gameParameters;
        this.turnCycle = turnCycle;
        this.board = board;
        this.bag = bag;
        this.commonGoal1TokenStack = commonGoal1TokenStack;
        this.commonGoal2TokenStack = commonGoal2TokenStack;
        this.personalGoal = personalGoal;
        this.commonGoal1 = commonGoal1;
        this.commonGoal2 = commonGoal2;
    }

    public List<String> getTurnCycle() {
        return turnCycle;
    }

    public Map<String, Integer> getGameParameters() {
        return gameParameters;
    }

    public String getCommonGoal1() {
        return commonGoal1;
    }

    public String getCommonGoal2() {
        return commonGoal2;
    }

    public int getPersonalGoal() {
        return personalGoal;
    }

    public Stack<Integer> getCommonGoal2TokenStack() {
        return commonGoal2TokenStack;
    }

    public Stack<Integer> getCommonGoal1TokenStack() {
        return commonGoal1TokenStack;
    }

    public Map<Tile, Integer> getBag() {
        return bag;
    }

    public Tile[][] getBoard() {
        return board;
    }

}
