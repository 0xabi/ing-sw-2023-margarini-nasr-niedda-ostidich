package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomServices implements ServerController {

    private final Map<String, GameServerController> playerMatch;

    private final ExecutorService executorService;

    protected static GameServerNetwork gameServerNetwork;

    public RoomServices() {
        playerMatch = new HashMap<>();
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
     * @author Francesco Ostidich
     */
    @Override
    public void startGame(Map<String, ClientController> names) {
        executorService.submit(() -> {
            GameServerController gsc = new GameServerController(names);
            names.keySet().forEach(player -> playerMatch.put(player, gsc));
        });
    }

    @Override
    public void disconnectedPlayer(String playerName) {
        try {
            playerMatch.get(playerName).disconnectedPlayer(playerName);
        } catch(NullPointerException ignored) {}
    }

    @Override
    public void pickTilesRequest(@NotNull Message message) {
        try {
            playerMatch.get(message.playerName()).pickTilesRequest(message);
        } catch(NullPointerException ignored) {}
    }

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
    public void joinRoom(String room) {

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void createNewRoom(String newRoomName, int playerNumber) {

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void askForRooms() {

    }

}
