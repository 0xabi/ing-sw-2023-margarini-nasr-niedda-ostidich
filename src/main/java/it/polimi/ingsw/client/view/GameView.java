package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ViewActions;

import java.util.*;

public class GameView implements ViewActions {

    private final Map<String, Integer> gameParameters;

    private final List<String> names;

    private Tile[][] board;

    private boolean endGameToken;

    private final Map<Tile, Integer> bag;

    private String commonGoal1;

    private String commonGoal2;

    private Stack<Integer> commonGoal1TokenStack;

    private Stack<Integer> commonGoal2TokenStack;

    private Map<Integer, String> commonGoal1GivenPlayers;

    private Map<Integer, String> commonGoal2GivenPlayers;

    private final List<Tile[][]> playerShelves;

    private final List<Integer> playerPoints;

    private final List<Integer> playerPersonalGoals;

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

    @Override
    public void setGameParameters(Map<String, Integer> gameParameters) {
        this.gameParameters.putAll(gameParameters);
        board = new Tile[this.gameParameters.get("boardRowLength")][this.gameParameters.get("boardColumnLength")];
    }

    @Override
    public void turnCycleOrder(List<String> names) {

    }

    @Override
    public void updateBoard(Tile[][] board) {

    }

    @Override
    public void updateEndGameToken(boolean present) {
        endGameToken = present;
    }

    @Override
    public void updateBag(Map<Tile, Integer> tilesLeft) {

    }

    @Override
    public void updateCommonGoal1TokenStack(Stack<Integer> tokenStack) {

    }

    @Override
    public void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer) {

    }

    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {

    }

    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {

    }

    @Override
    public void updatePlayerShelves(List<Tile[][]> shelves) {

    }

    @Override
    public void updatePlayerPoints(List<Integer> points) {

    }

    @Override
    public void start() {

    }

    @Override
    public String chooseIPAddress() {
        return null;
    }

    @Override
    public String choosePlayerName() {
        return null;
    }

    @Override
    public String chooseNewOrJoin() {
        return null;
    }

    @Override
    public String chooseNewGameName() {
        return null;
    }

    @Override
    public int chooseNewGamePlayerNumber() {
        return 0;
    }

    @Override
    public void updateGameRoom() {

    }

    @Override
    public void notifyGameHasStared() {

    }

    @Override
    public String chooseGameRoom(List<GameRoom> rooms) {
        return null;
    }

    @Override
    public void givePersonalGoals(int personalGoal) {

    }

    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {

    }

    @Override
    public List<Coordinates> pickTiles(int availablePickNumber) {
        return null;
    }

    @Override
    public List<Tile> chooseOrder(List<Coordinates> selection) {
        return null;
    }

    @Override
    public int chooseColumn() {
        return 0;
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {

    }

    @Override
    public String waitForEndGameAction() {
        return null;
    }
}
