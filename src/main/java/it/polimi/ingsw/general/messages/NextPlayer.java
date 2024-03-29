package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

import java.util.Map;
import java.util.Stack;

public class NextPlayer extends Message {

    private final Tile[][] board;

    private final boolean endGameToken;

    public String getEndGameTokenPlayer() {
        return endGameTokenPlayer;
    }

    private final String endGameTokenPlayer;

    public NextPlayer(String playerName, MessageID messageID, Tile[][] board, boolean endGameToken, String endGameTokenPlayer, Map<Tile, Integer> bag, Stack<Integer> commonGoal1TokenStack, Stack<Integer> commonGoal2TokenStack, Map<Integer, String> commonGoal1GivenPlayers, Map<Integer, String> commonGoal2GivenPlayers, Map<String, Tile[][]> playerShelves, Map<String, Integer> playerPoints, int availablePickNumber, String nextPlayer) {
        super(playerName, messageID);
        this.board = board;
        this.endGameToken = endGameToken;
        this.endGameTokenPlayer = endGameTokenPlayer;
        this.bag = bag;
        this.commonGoal1TokenStack = commonGoal1TokenStack;
        this.commonGoal2TokenStack = commonGoal2TokenStack;
        this.commonGoal1GivenPlayers = commonGoal1GivenPlayers;
        this.commonGoal2GivenPlayers = commonGoal2GivenPlayers;
        this.playerShelves = playerShelves;
        this.playerPoints = playerPoints;
        this.availablePickNumber = availablePickNumber;
        this.nextPlayer = nextPlayer;
    }

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

    public String getNextPlayer() {
        return nextPlayer;
    }

    public int getAvailablePickNumber() {
        return availablePickNumber;
    }

    private final int availablePickNumber;

    private final String nextPlayer;

}
