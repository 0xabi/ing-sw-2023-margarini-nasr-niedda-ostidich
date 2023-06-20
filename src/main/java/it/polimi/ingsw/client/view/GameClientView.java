package it.polimi.ingsw.client.view;

import com.google.gson.*;
import it.polimi.ingsw.client.clientController.GameClientController;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ClientView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Controller is to call action on the game view, which can be implemented with a GUI or a CLI.
 * This class contains all the attributes the UI is to print or that should be able to show when requested.
 * All the abstract actions requires something to be printed and something to be scanned, and returned after
 * being checked if legal.
 *
 * @author Francesco Ostidich
 */
public abstract class GameClientView implements ClientView {

    private final ClientController clientController;

    private static final int TIMEOUT = 20;

    private final Map<String, Integer> gameParameters;

    private String playerName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    private final List<String> names;

    private Tile[][] board;

    private boolean endGameToken;

    private Map<Tile, Integer> bag;

    private String commonGoal1;

    private String commonGoal2;

    private Stack<Integer> commonGoal1TokenStack;

    private Stack<Integer> commonGoal2TokenStack;

    private final Map<Integer, String> commonGoal1GivenPlayers;

    private final Map<Integer, String> commonGoal2GivenPlayers;

    private final Map<String, Tile[][]> playerShelves;

    private final Map<String, Integer> playerPoints;

    private List<Integer> playerPersonalGoals;

    private String IPAddress;

    private final Map<Coordinates, Tile> mapPersonalGoal;

    private List<GameRoom> gameRooms;

    private final List<String> chatMessages;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientView() {
        chatMessages = new LinkedList<>();
        gameParameters = new HashMap<>();
        names = new LinkedList<>();
        endGameToken = true;
        bag = new HashMap<>();
        commonGoal1GivenPlayers = new HashMap<>();
        commonGoal2GivenPlayers = new HashMap<>();
        playerShelves = new HashMap<>();
        playerPoints = new HashMap<>();
        playerPersonalGoals = new LinkedList<>();
        gameRooms = new ArrayList<>();
        mapPersonalGoal = new HashMap<>();
        try {
            clientController = new GameClientController(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter for chat messages.
     *
     * @author Francesco Ostidich
     * @return chat messages list
     */
    public List<String> getChatMessages() {
        return chatMessages;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateChat(String message) {
        chatMessages.add(message);
        if (chatMessages.size() > 7) {
            chatMessages.remove(0);
        }
    }

    /**
     * Getter for game client controller.
     *
     * @return game client controller
     * @author Francesco Ostidich
     */
    public ClientController getClientController() {
        return clientController;
    }

    /**
     * Getter for names.
     *
     * @return names list
     * @author Francesco Ostidich
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * Getter for game parameters.
     *
     * @return game parameters map
     * @author Francesco Ostidich
     */
    public Map<String, Integer> getGameParameters() {
        return gameParameters;
    }

    /**
     * Getter for bag.
     *
     * @return bag map
     * @author Francesco Ostidich
     */
    public Map<Tile, Integer> getBag() {
        return bag;
    }

    /**
     * Getter for common goal 1 give players.
     *
     * @return given players map
     * @author Francesco Ostidich
     */
    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        return commonGoal1GivenPlayers;
    }

    /**
     * Getter for common goal 1 token stack.
     *
     * @return tokens stack
     * @author Francesco Ostidich
     */
    public Stack<Integer> getCommonGoal1TokenStack() {
        return commonGoal1TokenStack;
    }

    /**
     * Getter for players' shelves.
     *
     * @return shelves list
     * @author Francesco Ostidich
     */
    public Map<String, Tile[][]> getPlayerShelves() {
        return playerShelves;
    }

    /**
     * Getter for common goal 2 token stack.
     *
     * @return tokens stack
     * @author Francesco Ostidich
     */
    public Stack<Integer> getCommonGoal2TokenStack() {
        return commonGoal2TokenStack;
    }

    /**
     * Getter for common goal 1.
     *
     * @return common goal 1 ID string
     * @author Francesco Ostidich
     */
    public String getCommonGoal1() {
        return commonGoal1;
    }

    /**
     * Getter for common goal 2.
     *
     * @return common goal 2 ID string
     * @author Francesco Ostidich
     */
    public String getCommonGoal2() {
        return commonGoal2;
    }

    /**
     * Getter for common goal 2 given players.
     *
     * @return given players map
     * @author Francesco Ostidich
     */
    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        return commonGoal2GivenPlayers;
    }

    /**
     * Getter for board.
     *
     * @return board matrix
     * @author Francesco Ostidich
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Getter for players' personal goals.
     *
     * @return personal goals IDs list
     * @author Francesco Ostidich
     */
    public List<Integer> getPlayerPersonalGoals() {
        return playerPersonalGoals;
    }

    /**
     * Getter for players' points.
     *
     * @return points number
     * @author Francesco Ostidich
     */
    public Map<String, Integer> getPlayerPoints() {
        return playerPoints;
    }

    /**
     * Getter for server's IP address.
     *
     * @return IP address string
     * @author Francesco Ostidich
     */
    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Getter for game rooms.
     *
     * @return game rooms list
     * @author Francesco Ostidich
     */
    public List<GameRoom> getGameRooms() {
        return gameRooms;
    }

    /**
     * Getter for end game token.
     *
     * @return end game token boolean
     * @author Francesco Ostidich
     */
    public boolean getEndGameToken() {
        return endGameToken;
    }

    /**
     * Getter for timeout.
     *
     * @return time out seconds
     * @author Francesco Ostidich
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
        for (int i = 0; i < gameRooms.size(); i++) {
            if (gameRooms.get(i).gameRoomName().equals(gameRoom.gameRoomName())) {
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
        if (givenPlayer != null) {
            for (int token : givenPlayer.keySet()) {
                if(!commonGoal1GivenPlayers.containsKey(token))
                    this.commonGoal1GivenPlayers.put(token, givenPlayer.get(token));

            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {
        this.commonGoal2TokenStack = tokenStack;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {
        if (givenPlayer != null) {
            for (int token : givenPlayer.keySet()) {
                if(commonGoal2GivenPlayers.containsKey(token))
                    this.commonGoal2GivenPlayers.put(token, givenPlayer.get(token));

            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updatePlayerShelves(@NotNull Map<String, Tile[][]> shelves) {
        for (String player : shelves.keySet()) {
            if (playerShelves.containsKey(player))
                playerShelves.replace(player, shelves.get(player));
            else
                playerShelves.put(player, shelves.get(player));
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void updatePlayerPoints(@NotNull Map<String, Integer> points) {
        for (String player : points.keySet()) {
            playerPoints.replace(player, points.get(player));
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void givePersonalGoals(@NotNull List<Integer> personalGoals) {
        playerPersonalGoals = personalGoals;
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/personalGoalMatchesMap.json")));
        JsonElement matchesElements = JsonParser.parseReader(reader);
        JsonObject matchesObject = matchesElements.getAsJsonObject();
        JsonArray jsonCoordinates;
        for (Tile tile : Tile.values()) {
            if (tile != Tile.EMPTY) {
                jsonCoordinates = matchesObject.
                        get(String.valueOf(personalGoals.get(0))).
                        getAsJsonObject().
                        get(tile.toString().toUpperCase()).
                        getAsJsonArray();
                mapPersonalGoal.put(new Coordinates(jsonCoordinates.get(0).getAsInt(), jsonCoordinates.get(1).getAsInt()), tile);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {
        this.commonGoal1 = commonGoal1;
        this.commonGoal2 = commonGoal2;
    }

    public Map<Coordinates, Tile> getMapPersonalGoal() {
        return mapPersonalGoal;
    }

}
