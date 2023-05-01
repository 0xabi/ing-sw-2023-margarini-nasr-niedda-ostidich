package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomServices implements ServerController {

    private final Map<String, GameServerController> playerMatch;

    private final Map<String, ClientController> clients;

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
     * @author Francesco Ostidich
     * @param gameServerNetwork is the game server network
     */
    public static void setGameServerNetwork(GameServerNetwork gameServerNetwork) {
        RoomServices.gameServerNetwork = gameServerNetwork;
    }

    /**
     * Getter for clients map
     *
     * @author Francesco Ostidich
     * @return client controller interfaces mapped on players' name string
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
     * @author Francesco Ostidich
     * @param names is the list of players' name
     */
    public void startGame(Map<String, ClientController> names) {
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
        } catch(NullPointerException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void reconnectedPlayer(String playerName) {
        try {
            playerMatch.get(playerName).reconnectedPlayer(playerName);
        } catch(NullPointerException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void pickTilesRequest(@NotNull Message message) {
        try {
            playerMatch.get(message.playerName()).pickTilesRequest(message);
        } catch(NullPointerException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void insertTilesRequest(Message message) {
        try {
            playerMatch.get(message.playerName()).insertTilesRequest(message);
        } catch(NullPointerException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void joinRoom(@NotNull Message msg) {
        //server.joinRoom(new Message(playerName, MessageID.JOIN_ROOM, evt.value()));
        if(msg.messageID() != MessageID.JOIN_ROOM || !(msg.content() instanceof  String) || !clients.containsKey(msg.playerName())) return;
        for(GameRoom gameRoom: gameRooms) {
            if(gameRoom.gameRoomName().equals(msg.content()) &&
                    !gameRoom.enteredPlayers().contains(msg.playerName())) {
                gameRoom.enteredPlayers().add(msg.playerName());
                if(gameRoom.enteredPlayers().size() == gameRoom.totalPlayers()) {
                    Map<String, ClientController> names = new HashMap<>();
                    //noinspection ResultOfMethodCallIgnored
                    gameRoom.enteredPlayers().stream().map(player -> names.put(player, clients.get(player)));
                    startGame(names);
                    gameRooms.remove(gameRoom);
                }
                return;
            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void createNewRoom(@NotNull Message msg) {
        if(msg.messageID() != MessageID.CREATE_NEW_ROOM || !(msg.contents()[0] instanceof String) || !clients.containsKey(msg.playerName())) return;
        for (GameRoom room : gameRooms) {
            if (room.gameRoomName().equals(msg.contents()[0]))
                clients.get(msg.playerName()).roomNameNotAvailable();
        }
        List<String> enteredPlayers = new LinkedList<>();
        enteredPlayers.add(msg.playerName());
        try {
            gameRooms.add(new GameRoom((String) msg.contents()[0], msg.playerName(), (int) msg.contents()[1], enteredPlayers));
        } catch(ClassCastException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void askForRooms(@NotNull Message msg) {
        if(msg.messageID() != MessageID.ASK_FOR_ROOMS || !clients.containsKey(msg.playerName())) return;
        clients.get(msg.playerName()).showRooms(new Message(msg.playerName(), MessageID.SHOW_ROOMS, gameRooms));
    }

}
