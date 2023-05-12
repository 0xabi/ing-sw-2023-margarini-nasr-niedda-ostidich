package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.messages.*;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;
import it.polimi.ingsw.server.serverNetwork.Client;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomServices implements ServerController {

    private final Map<String, GameServerController> playerMatch;

    private final Map<String, Client> clients;

    private final List<GameRoom> gameRooms;

    private final ExecutorService executorService;

    private static GameServerNetwork gameServerNetwork;

    public RoomServices() {
        playerMatch = new HashMap<>();
        clients = new HashMap<>();
        gameRooms = new LinkedList<>();
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * Setter for game server network.
     *
     * @param gameServerNetwork is the game server network
     * @author Francesco Ostidich
     */
    public static void setGameServerNetwork(GameServerNetwork gameServerNetwork) {
        RoomServices.gameServerNetwork = gameServerNetwork;
    }

    /**
     * Getter for clients map
     *
     * @return client controller interfaces mapped on players' name string
     * @author Francesco Ostidich
     */
    public Map<String, Client> getClients() {
        return clients;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void playerConnected(String playerName, Client client) {
        clients.put(playerName, client);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public Set<String> onlinePlayers() {
        return playerMatch.keySet();
    }

    /**
     * When a game room is full, match is to be started.
     *
     * @param names is the list of players' name
     * @author Francesco Ostidich
     */
    public void startGame(Map<String, Client> names) {
        executorService.submit(() -> {
            GameServerController gsc = new GameServerController(names);
            names.keySet().forEach(player -> playerMatch.put(player, gsc));
        });
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void disconnectedPlayer(String playerName) {
        try {
            playerMatch.get(playerName).disconnectedPlayer(playerName);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void reconnectedPlayer(String playerName) {
        try {
            playerMatch.get(playerName).reconnectedPlayer(playerName);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void pickTilesRequest(@NotNull PickTilesRequest message) {
        try {
            playerMatch.get(message.getPlayerName()).pickTilesRequest(message);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void insertTilesRequest(InsertTilesRequest message) {
        try {
            playerMatch.get(message.getPlayerName()).insertTilesRequest(message);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void joinRoom(@NotNull JoinRoom msg) {
        if (msg.getMessageID() != MessageID.JOIN_ROOM || !clients.containsKey(msg.getPlayerName())) return;
        for (GameRoom gameRoom : gameRooms) {
            if (gameRoom.gameRoomName().equals(msg.getChosenRoom()) &&
                    !gameRoom.enteredPlayers().contains(msg.getPlayerName())) {
                gameRoom.enteredPlayers().add(msg.getPlayerName());
                if (gameRoom.enteredPlayers().size() == gameRoom.totalPlayers()) {
                    Map<String, ClientController> names = new HashMap<>();
                    //noinspection ResultOfMethodCallIgnored
                    gameRoom.enteredPlayers().stream().map(player -> names.put(player, clients.get(player)));
                    startGame(names);
                    gameRooms.remove(gameRoom);
                    return;
                }
                clients.get(msg.getPlayerName()).showPersonalRoom(new ShowPersonalRoom(msg.getPlayerName(), MessageID.SHOW_PERSONAL_ROOM, gameRoom));
                return;
            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void createNewRoom(@NotNull CreateNewRoom msg) {
        if (msg.getMessageID() != MessageID.CREATE_NEW_ROOM || !clients.containsKey(msg.getPlayerName())) return;
        for (GameRoom room : gameRooms) {
            if (room.gameRoomName().equals(msg.getRoomName())) {
                clients.get(msg.getPlayerName()).roomNameNotAvailable(new RoomNameNotAvailable(msg.getPlayerName(), MessageID.ROOM_NAME_NOT_AVAILABLE));
                return;
            }
        }
        List<String> enteredPlayers = new LinkedList<>();
        enteredPlayers.add(msg.getPlayerName());
        GameRoom newRoom = new GameRoom(msg.getRoomName(), msg.getPlayerName(), msg.getRoomPlayerNumber(), enteredPlayers);
        gameRooms.add(newRoom);
        clients.get(msg.getPlayerName()).showPersonalRoom(new ShowPersonalRoom(msg.getPlayerName(), MessageID.SHOW_PERSONAL_ROOM, newRoom));
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void askForRooms(@NotNull AskForRooms msg) {
        if (msg.getMessageID() != MessageID.ASK_FOR_ROOMS || !clients.containsKey(msg.getPlayerName())) return;
        clients.get(msg.getPlayerName()).showRooms(new ShowRooms(msg.getPlayerName(), MessageID.SHOW_ROOMS, gameRooms));
    }

    /**
     * When game has to end, close connections and deletes game
     *
     * @author Francesco Ostidich
     * @param names is the players' game names list
     */
    protected void closeGame(@NotNull List<String> names) {
        gameServerNetwork.disconnect(names);
        names.forEach(player -> {
            clients.remove(player);
            playerMatch.remove(player);
        });
        System.gc();
    }

}
