package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.Tile;

import java.util.Map;
import java.util.Stack;

public class NewTurn extends Message {

    private final Tile[][] board;

    private final boolean endGameToken;

    public Map<Tile, Integer> getBag() {
        return bag;
    }

    public Stack<Integer> getCommonGoal1TokenStack() {
        return commonGoal1TokenStack;
    }

    public Stack<Integer> getCommonGoal2TokenStack() {
        return commonGoal2TokenStack;
    }

    private final Map<Tile, Integer> bag;

    private final Stack<Integer> commonGoal1TokenStack;

    private final Stack<Integer> commonGoal2TokenStack;

    private final Map<Integer, String> commonGoal1GivenPlayers;

    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        return commonGoal1GivenPlayers;
    }

    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        return commonGoal2GivenPlayers;
    }

    private final Map<Integer, String> commonGoal2GivenPlayers;

    public boolean getEndGameToken() {
        return endGameToken;
    }

    public Map<String, Tile[][]> getPlayerShelves() {
        return playerShelves;
    }

    private final Map<String, Tile[][]> playerShelves;

    public Tile[][] getBoard() {
        return board;
    }

    private final Map<String, Integer> playerPoints;

    public Map<String, Integer> getPlayerPoints() {
        return playerPoints;
    }

    public NewTurn(String playerName, MessageID messageID, Tile[][] board, boolean endGameToken, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, Map<Integer, String> commonGoal1GivenPlayers, Map<Integer, String> commonGoal2GivenPlayers, Map<String, Tile[][]> playerShelves, Map<String, Integer> playerPoints) {
        super(playerName, messageID);
        this.board = board;
        this.endGameToken = endGameToken;
        this.bag = bag;
        this.commonGoal1TokenStack = commonGoal1TokenStack;
        this.commonGoal2TokenStack = commonGoal2TokenStack;
        this.commonGoal1GivenPlayers = commonGoal1GivenPlayers;
        this.commonGoal2GivenPlayers = commonGoal2GivenPlayers;
        this.playerShelves = playerShelves;
        this.playerPoints = playerPoints;
    }

}
