package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EndGame extends Message {

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

    private final List<Integer> personalGoals;

    private final Map<String, Integer> personalGoalPoints;

    private final Map<String, Integer> adjacentGoalPoints;

    public EndGame(String playerName, MessageID messageID, Tile[][] board, boolean endGameToken, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, Map<Integer, String> commonGoal1GivenPlayers, Map<Integer, String> commonGoal2GivenPlayers, Map<String, Tile[][]> playerShelves, Map<String, Integer> playerPoints, List<Integer> personalGoals, Map<String, Integer> personalGoalPoints, Map<String, Integer> adjacentGoalPoints, String winner) {
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
        this.personalGoals = personalGoals;
        this.personalGoalPoints = personalGoalPoints;
        this.adjacentGoalPoints = adjacentGoalPoints;
        this.winner = winner;
    }

    public List<Integer> getPersonalGoals() {
        return personalGoals;
    }

    public Map<String, Integer> getPersonalGoalPoints() {
        return personalGoalPoints;
    }

    public Map<String, Integer> getAdjacentGoalPoints() {
        return adjacentGoalPoints;
    }

    public String getWinner() {
        return winner;
    }

    private final String winner;




}
