package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

import java.util.Map;
import java.util.Stack;

public class NextPlayer extends NewTurn {

    public String getNextPlayer() {
        return nextPlayer;
    }

    public int getAvailablePickNumber() {
        return availablePickNumber;
    }

    private final int availablePickNumber;

    private final String nextPlayer;

    public NextPlayer(String playerName, MessageID messageID, Tile[][] board, boolean endGameToken, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, Map<Integer, String> commonGoal1GivenPlayers, Map<Integer, String> commonGoal2GivenPlayers, Map<String, Tile[][]> playerShelves, Map<String, Integer> playerPoints, int availablePickNumber, String nextPlayer) {
        super(playerName, messageID, board, endGameToken, bag, commonGoal1TokenStack, commonGoal2TokenStack, commonGoal1GivenPlayers, commonGoal2GivenPlayers, playerShelves, playerPoints);
        this.availablePickNumber = availablePickNumber;
        this.nextPlayer = nextPlayer;
    }

}
