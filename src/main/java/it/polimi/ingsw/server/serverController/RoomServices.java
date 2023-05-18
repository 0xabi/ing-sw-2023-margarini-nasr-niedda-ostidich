package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.interfaces.ServerNetwork;
import it.polimi.ingsw.resources.messages.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomServices implements ServerController {

    private final Map<String, GameServerController> playerMatch;

    private final Map<String, ClientController> clients;

    private final List<GameRoom> gameRooms;

    private final ExecutorService executorService;

    private static ServerNetwork serverNetwork;

    public RoomServices() {
        playerMatch = new HashMap<>();
        clients = new HashMap<>();
        gameRooms = new LinkedList<>();
        executorService = Executors.newCachedThreadPool();
    }

    /**
     * Setter for game server network.
     *
     * @param serverNetwork is the game server network
     * @author Francesco Ostidich
     */
    public static void setServerNetwork(ServerNetwork serverNetwork) {
        RoomServices.serverNetwork = serverNetwork;
    }

    /**
     * Getter for clients map
     *
     * @return client controller interfaces mapped on players' name string
     * @author Francesco Ostidich
     */
    public Map<String, ClientController> getClients() {
        return clients;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void playerConnected(String playerName, ClientController client) {
        clients.put(playerName, client);
        System.out.println(playerName + " added to online clients");
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public Set<String> onlinePlayers() {
        return clients.keySet();
    }

    /**
     * When a game room is full, match is to be started.
     *
     * @param names is the list of players' name
     * @author Francesco Ostidich
     */
    public void startGame(Map<String, ClientController> names) {
        System.out.println("started game with players: " + names);
        executorService.execute(() -> {
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
        System.out.println("someone joining room");
        if (msg.getMessageID() != MessageID.JOIN_ROOM || !clients.containsKey(msg.getPlayerName())) {
            System.out.println("join room message discarded");
            return;
        }
        for (GameRoom gameRoom : gameRooms) {
            System.out.println(gameRoom.gameRoomName() + " should equal " + msg.getChosenRoom());
            System.out.println(gameRoom.enteredPlayers() + " should not contain " + msg.getPlayerName());
            if (gameRoom.gameRoomName().equals(msg.getChosenRoom()) &&
                    !gameRoom.enteredPlayers().contains(msg.getPlayerName())) {
                gameRoom.enteredPlayers().add(msg.getPlayerName());
                System.out.println("player " + msg.getPlayerName() + " added to room " + gameRoom.gameRoomName());
                if (gameRoom.enteredPlayers().size() == gameRoom.totalPlayers()) {
                    System.out.println("filled room: " + gameRoom.gameRoomName());
                    Map<String, ClientController> names = new HashMap<>();
                    for (String player: gameRoom.enteredPlayers()) {
                        names.put(player, clients.get(player));
                    }
                    startGame(names);
                    gameRooms.remove(gameRoom);
                    return;
                }
                clients.get(msg.getPlayerName()).showPersonalRoom(new ShowPersonalRoom(msg.getPlayerName(), MessageID.SHOW_PERSONAL_ROOM, gameRoom));
                return;
            }
        }
        System.out.println("no room found");
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void createNewRoom(@NotNull CreateNewRoom msg) {
        System.out.println("create new room message received!");
        if (msg.getMessageID() != MessageID.CREATE_NEW_ROOM || !clients.containsKey(msg.getPlayerName())) return;
        System.out.println("create new room message accepted!");
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
        System.out.println("create new room message processed!");
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
        serverNetwork.disconnect(names);
        names.forEach(player -> {
            clients.remove(player);
            playerMatch.remove(player);
        });
        System.gc();
    }

}
