package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ViewActions;

import java.util.*;

public abstract class GameView implements ViewActions {

    private final Map<String, Integer> gameParameters;

    private final List<String> names;

    private Tile[][] board;

    private boolean endGameToken;

    private Map<Tile, Integer> bag;

    private String commonGoal1;

    private String commonGoal2;

    private Stack<Integer> commonGoal1TokenStack;

    private Stack<Integer> commonGoal2TokenStack;

    private Map<Integer, String> commonGoal1GivenPlayers;

    private Map<Integer, String> commonGoal2GivenPlayers;

    private final List<Tile[][]> playerShelves;

    private final List<Integer> playerPoints;

    private List<Integer> playerPersonalGoals;

    private String IPAddress;

    public GameView() {
        gameParameters = new HashMap<>();
        names = new LinkedList<>();
        endGameToken = true;
        bag = new HashMap<>();
        playerShelves = new LinkedList<>();
        playerPoints = new LinkedList<>();
        playerPersonalGoals = new LinkedList<>();
    }

    public List<String> getNames() {
        return names;
    }

    public Map<String, Integer> getGameParameters() {
        return gameParameters;
    }

    public Map<Tile, Integer> getBag() {
        return bag;
    }

    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        return commonGoal1GivenPlayers;
    }

    public Stack<Integer> getCommonGoal1TokenStack() {
        return commonGoal1TokenStack;
    }

    public List<Tile[][]> getPlayerShelves() {
        return playerShelves;
    }

    public Stack<Integer> getCommonGoal2TokenStack() {
        return commonGoal2TokenStack;
    }

    public String getCommonGoal1() {
        return commonGoal1;
    }

    public String getCommonGoal2() {
        return commonGoal2;
    }

    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        return commonGoal2GivenPlayers;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public List<Integer> getPlayerPersonalGoals() {
        return playerPersonalGoals;
    }

    public List<Integer> getPlayerPoints() {
        return playerPoints;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public boolean getEndGameToken() {
        return endGameToken;
    }

    @Override
    public void setGameParameters(Map<String, Integer> gameParameters) {
        this.gameParameters.putAll(gameParameters);
        board = new Tile[this.gameParameters.get("boardRowLength")][this.gameParameters.get("boardColumnLength")];
    }

    @Override
    public void turnCycleOrder(List<String> names) {
        this.names.addAll(names);
    }

    @Override
    public void updateBoard(Tile[][] board) {
        this.board = board;
    }

    @Override
    public void updateEndGameToken(boolean present) {
        endGameToken = present;
    }

    @Override
    public void updateBag(Map<Tile, Integer> tilesLeft) {
        this.bag = tilesLeft;
    }

    @Override
    public void updateCommonGoal1TokenStack(Stack<Integer> tokenStack) {
        this.commonGoal1TokenStack = tokenStack;
    }

    @Override
    public void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer) {
        this.commonGoal1GivenPlayers = givenPlayer;
    }

    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {
        this.commonGoal1TokenStack = tokenStack;
    }

    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {
        this.commonGoal1GivenPlayers = givenPlayer;
    }

    @Override
    public void updatePlayerShelves(List<Tile[][]> shelves) {
        for(int i = 0; i < names.size(); i++) {
            if(shelves.get(i) != null) {
                playerShelves.set(i, shelves.get(i));
            }
        }
    }

    @Override
    public void updatePlayerPoints(List<Integer> points) {
        for(int i = 0; i < names.size(); i++) {
            if(points.get(i) != null) {
                playerPoints.set(i, points.get(i));
            }
        }
    }

    @Override
    public abstract void start();

    @Override
    public abstract String chooseIPAddress();

    @Override
    public abstract String choosePlayerName();

    @Override
    public abstract String chooseNewOrJoin();

    @Override
    public abstract String chooseNewGameName();

    @Override
    public abstract int chooseNewGamePlayerNumber();

    @Override
    public abstract void updateGameRoom(GameRoom gameRoom);

    @Override
    public abstract void notifyGameHasStared();

    @Override
    public abstract String chooseGameRoom(List<GameRoom> rooms);

    @Override
    public void givePersonalGoals(List<Integer> personalGoals) {
        playerPersonalGoals = personalGoals;
    }

    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {
        this.commonGoal1 = commonGoal1;
        this.commonGoal2 = commonGoal2;
    }

    @Override
    public abstract List<Coordinates> pickTiles(int availablePickNumber);

    @Override
    public abstract List<Tile> chooseOrder(List<Tile> selection);

    @Override
    public abstract int chooseColumn();

    @Override
    public abstract void assignCommonGoalPoints(String playerName, int token);

    @Override
    public abstract void assignPersonalGoalPoints(Map<String, Integer> points);

    @Override
    public abstract void assignAdjacentGoalPoints(Map<String, Integer> points);

    @Override
    public abstract void announceWinner(String winnerName, Map<String, Integer> points);

    @Override
    public abstract String waitForEndGameAction();
}
