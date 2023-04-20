package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ViewActions;

import java.util.*;

/**
 * Controller is to call action on the game view, which can be implemented with a GUI or a CLI.
 * This class contains all the attributes the UI is to print or that should be able to show when requested.
 * All the abstract actions requires something to be printed and something to be scanned, and returned after
 * being checked if legal.
 *
 * @author Francesco Ostidich
 */
public abstract class GameView implements ViewActions {

    private static final int TIMEOUT = 20;

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

    private List<GameRoom> gameRooms;

    private boolean gameEnded;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameView() {
        gameParameters = new HashMap<>();
        names = new LinkedList<>();
        endGameToken = true;
        bag = new HashMap<>();
        playerShelves = new LinkedList<>();
        playerPoints = new LinkedList<>();
        playerPersonalGoals = new LinkedList<>();
        gameRooms = new ArrayList<>();
        gameEnded = false;
    }

    public void endGame() {
        this.gameEnded = false;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Getter for names.
     *
     * @author Francesco Ostidich
     * @return names list
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * Getter for game parameters.
     *
     * @author Francesco Ostidich
     * @return game parameters map
     */
    public Map<String, Integer> getGameParameters() {
        return gameParameters;
    }

    /**
     * Getter for bag.
     *
     * @author Francesco Ostidich
     * @return bag map
     */
    public Map<Tile, Integer> getBag() {
        return bag;
    }

    /**
     * Getter for common goal 1 give players.
     *
     * @author Francesco Ostidich
     * @return given players map
     */
    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        return commonGoal1GivenPlayers;
    }

    /**
     * Getter for common goal 1 token stack.
     *
     * @author Francesco Ostidich
     * @return tokens stack
     */
    public Stack<Integer> getCommonGoal1TokenStack() {
        return commonGoal1TokenStack;
    }

    /**
     * Getter for players' shelves.
     *
     * @author Francesco Ostidich
     * @return shelves list
     */
    public List<Tile[][]> getPlayerShelves() {
        return playerShelves;
    }

    /**
     * Getter for common goal 2 token stack.
     *
     * @author Francesco Ostidich
     * @return tokens stack
     */
    public Stack<Integer> getCommonGoal2TokenStack() {
        return commonGoal2TokenStack;
    }

    /**
     * Getter for common goal 1.
     *
     * @author Francesco Ostidich
     * @return common goal 1 ID string
     */
    public String getCommonGoal1() {
        return commonGoal1;
    }

    /**
     * Getter for common goal 2.
     *
     * @author Francesco Ostidich
     * @return common goal 2 ID string
     */
    public String getCommonGoal2() {
        return commonGoal2;
    }

    /**
     * Getter for common goal 2 given players.
     *
     * @author Francesco Ostidich
     * @return given players map
     */
    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        return commonGoal2GivenPlayers;
    }

    /**
     * Getter for board.
     *
     * @author Francesco Ostidich
     * @return board matrix
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Getter for players' personal goals.
     *
     * @author Francesco Ostidich
     * @return personal goals IDs list
     */
    public List<Integer> getPlayerPersonalGoals() {
        return playerPersonalGoals;
    }

    /**
     * Getter for players' points.
     *
     * @author Francesco Ostidich
     * @return points number
     */
    public List<Integer> getPlayerPoints() {
        return playerPoints;
    }

    /**
     * Getter for server's IP address.
     *
     * @author Francesco Ostidich
     * @return IP address string
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Getter for game rooms.
     *
     * @author Francesco Ostidich
     * @return game rooms list
     */
    public List<GameRoom> getGameRooms() {
        return gameRooms;
    }

    /**
     * Getter for end game token.
     *
     * @author Francesco Ostidich
     * @return end game token boolean
     */
    public boolean getEndGameToken() {
        return endGameToken;
    }

    /**
     * Getter for timeout.
     *
     * @author Francesco Ostidich
     * @return time out seconds
     */
    public static int getTimeout() {
        return TIMEOUT;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void setGameParameters(Map<String, Integer> gameParameters) {
        this.gameParameters.putAll(gameParameters);
        board = new Tile[this.gameParameters.get("boardRowLength")][this.gameParameters.get("boardColumnLength")];
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void turnCycleOrder(List<String> names) {
        this.names.addAll(names);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateBoard(Tile[][] board) {
        this.board = board;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateEndGameToken(boolean present) {
        endGameToken = present;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateBag(Map<Tile, Integer> tilesLeft) {
        this.bag = tilesLeft;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateGameRooms(List<GameRoom> gameRooms) {
        this.gameRooms = gameRooms;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateGameRoom(GameRoom gameRoom) {
        for(int i = 0; i < gameRooms.size(); i++) {
            if(gameRooms.get(i).gameRoomName().equals(gameRoom.gameRoomName())) {
                gameRooms.set(i, gameRoom);
                return;
            }
        }
        gameRooms.add(gameRoom);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal1TokenStack(Stack<Integer> tokenStack) {
        this.commonGoal1TokenStack = tokenStack;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer) {
        this.commonGoal1GivenPlayers = givenPlayer;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {
        this.commonGoal1TokenStack = tokenStack;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {
        this.commonGoal1GivenPlayers = givenPlayer;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updatePlayerShelves(List<Tile[][]> shelves) {
        for(int i = 0; i < names.size(); i++) {
            if(shelves.get(i) != null) {
                playerShelves.set(i, shelves.get(i));
            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updatePlayerPoints(List<Integer> points) {
        for(int i = 0; i < names.size(); i++) {
            if(points.get(i) != null) {
                playerPoints.set(i, points.get(i));
            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void givePersonalGoals(List<Integer> personalGoals) {
        playerPersonalGoals = personalGoals;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {
        this.commonGoal1 = commonGoal1;
        this.commonGoal2 = commonGoal2;
    }

}
