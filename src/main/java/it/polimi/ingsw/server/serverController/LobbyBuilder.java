package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.interfaces.ServerNetworkToController;

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
public class LobbyBuilder implements ServerNetworkToController {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void startGame(List<String> names) {
        executorService.submit(() -> {
            GameServerController gameServerController = new GameServerController(names);
            gameServerController.playMatch();
        });
    }

    @Override
    public void doAction(Message message) {

    }

    @Override
    public void disconnected(String playerName) {

    }

}
