package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EndGame extends NewTurn {

    private final List<Integer> personalGoals;

    private final Map<String, Integer> personalGoalPoints;

    private final Map<String, Integer> adjacentGoalPoints;

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

    public EndGame(String playerName, MessageID messageID, Tile[][] board, boolean endGameToken, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, Map<Integer, String> commonGoal1GivenPlayers, Map<Integer, String> commonGoal2GivenPlayers, Map<String, Tile[][]> playerShelves, Map<String, Integer> playerPoints, List<Integer> personalGoals, Map<String, Integer> personalGoalPoints, Map<String, Integer> adjacentGoalPoints, String winner) {
        super(playerName, messageID, board, endGameToken, bag, commonGoal1TokenStack, commonGoal2TokenStack, commonGoal1GivenPlayers, commonGoal2GivenPlayers, playerShelves, playerPoints);
        this.personalGoals = personalGoals;
        this.personalGoalPoints = personalGoalPoints;
        this.adjacentGoalPoints = adjacentGoalPoints;
        this.winner = winner;
    }

}
