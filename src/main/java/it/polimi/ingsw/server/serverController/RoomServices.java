package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ServerController;
import it.polimi.ingsw.general.interfaces.ServerNetwork;
import it.polimi.ingsw.general.messages.*;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomServices extends UnicastRemoteObject implements ServerController {

    private final Map<String, GameServerController> playerMatch;

    private final Map<String, ClientController> clients;

    private final List<GameRoom> gameRooms;

    private final ExecutorService executorService;

    private static ServerNetwork serverNetwork;

    public RoomServices() throws RemoteException {
        super();
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
        System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "] " + playerName + " added to online clients");
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
        executorService.execute(() -> {
            try {
                GameServerController gsc = new GameServerController(names);
                names.keySet().forEach(player -> playerMatch.put(player, gsc));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
        if (msg.getMessageID() != MessageID.JOIN_ROOM || !clients.containsKey(msg.getPlayerName())) {
            return;
        }
        for (GameRoom gameRoom : gameRooms) {
            if (gameRoom.gameRoomName().equals(msg.getChosenRoom()) &&
                    !gameRoom.enteredPlayers().contains(msg.getPlayerName())) {
                gameRoom.enteredPlayers().add(msg.getPlayerName());
                if (gameRoom.enteredPlayers().size() == gameRoom.totalPlayers()) {
                    Map<String, ClientController> names = new HashMap<>();
                    for (String player : gameRoom.enteredPlayers()) {
                        names.put(player, clients.get(player));
                    }
                    startGame(names);
                    gameRooms.remove(gameRoom);
                    return;
                }
                executorService.execute(() -> {
                            try {
                                clients.get(msg.getPlayerName()).showPersonalRoom(new ShowPersonalRoom(msg.getPlayerName(), MessageID.SHOW_PERSONAL_ROOM, gameRoom));
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
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
                executorService.execute(() ->
                {
                    try {
                        clients.get(msg.getPlayerName()).roomNameNotAvailable(new RoomNameNotAvailable(msg.getPlayerName(), MessageID.ROOM_NAME_NOT_AVAILABLE));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
                return;
            }
        }
        List<String> enteredPlayers = new LinkedList<>();
        enteredPlayers.add(msg.getPlayerName());
        GameRoom newRoom = new GameRoom(msg.getRoomName(), msg.getPlayerName(), msg.getRoomPlayerNumber(), enteredPlayers);
        gameRooms.add(newRoom);
        executorService.execute(() ->
        {
            try {
                clients.get(msg.getPlayerName()).showPersonalRoom(new ShowPersonalRoom(msg.getPlayerName(), MessageID.SHOW_PERSONAL_ROOM, newRoom));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void askForRooms(@NotNull AskForRooms msg) {
        if (msg.getMessageID() != MessageID.ASK_FOR_ROOMS || !clients.containsKey(msg.getPlayerName())) return;
        executorService.execute(() ->
                {
                    try {
                        clients.get(msg.getPlayerName()).showRooms(new ShowRooms(msg.getPlayerName(), MessageID.SHOW_ROOMS, gameRooms));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    /**
     * When game has to end, close connections and deletes game
     *
     * @param names is the players' game names list
     * @author Francesco Ostidich
     * @author Francesco Ostidich
     */
    protected void closeGame(@NotNull List<String> names) {
        serverNetwork.disconnect(names);
        names.forEach(player -> {
            clients.remove(player);
            playerMatch.remove(player);
        });
        System.gc();
    }

    public boolean PlayerIDisAvailable(@NotNull Hello message) {
        return !onlinePlayers().contains(message.getPlayerName());
    }

    @Override
    public Object getGSC(String playerName) {
        return playerMatch.get(playerName);
    }

    @Override
    public void chatMessage(@NotNull Chat message) throws RemoteException {
        try {
            playerMatch.get(message.getPlayerName()).chatMessage(message);
        } catch (NullPointerException ignored) {
        }
    }

}
