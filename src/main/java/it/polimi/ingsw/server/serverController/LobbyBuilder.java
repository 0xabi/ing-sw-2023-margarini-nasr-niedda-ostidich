package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.interfaces.ViewActions;
import it.polimi.ingsw.server.virtualView.GameVirtualView;
import java.util.Map;
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

    private static GameVirtualView gameVirtualView;

    public static void main(String[] args) {
        System.out.println("Starting server...");
        //noinspection InfiniteLoopStatement
        while(true) {
            generateGame(gameVirtualView.waitForClients());
        }
    }

    private static void generateGame(Map<String, ViewActions> clients) {
        executorService.submit(() -> {
            GameModelController gameModelController = new GameModelController(clients);
            gameModelController.playMatch();
        });
    }

}
