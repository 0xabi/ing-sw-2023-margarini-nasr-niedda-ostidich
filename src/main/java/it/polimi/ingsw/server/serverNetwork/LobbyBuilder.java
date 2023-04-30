package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.server.serverController.GameServerController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * On a unique thread, the server is always waiting for players to connect.
 * Game rooms are treated here.
 * When a game is ready to start it is passed to a GameModelController on its own thread, which plays all the game by itself.
 * When game ends method finishes and client is updated.
 *
 * @author Francesco Ostidich
 */
public class LobbyBuilder {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * When a game room is full, match is to be started.
     *
     * @author Francesco Ostidich
     * @param names is the list of players' name
     */
    public static void startGame(List<String> names) {
        executorService.submit(() -> {
            GameServerController gameServerController = new GameServerController(names);
            gameServerController.playMatch();
        });
    }

}
