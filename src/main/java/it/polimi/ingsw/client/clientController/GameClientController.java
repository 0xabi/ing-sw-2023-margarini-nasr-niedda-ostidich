package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.clientNetwork.GameClientNetwork;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.interfaces.ClientNetworkToController;

import java.util.Objects;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameClientController implements ClientNetworkToController {

    public static final String NETWORK = "RMI";

    public static final String INTERFACE = "CLI";

    private static final GameView gameView = initializeGameView();

    private static GameClientNetwork gameClientNetwork;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientController() {}

    /**
     * Based on constant, game view is initialized as GUI or CLI.
     *
     * @author Francesco Ostidich
     * @return CLI or GUI
     */
    private static GameView initializeGameView() {
        if(Objects.equals(INTERFACE, "GUI")) return new GUI();
        else return new CLI();
    }

    public static void main(String[] args) {
        GameClientController gameClientController = new GameClientController();
        //noinspection ConstantConditions
        if(NETWORK.equals("RMI"))
            //noinspection InfiniteLoopStatement
            do {
                lobbyActions();
                gameClientNetwork.connectionRMIToServer();
                gameClientController.waitForEndGame();
            } while (true);
        else
            //noinspection InfiniteLoopStatement
            do {
                lobbyActions();
                gameClientNetwork.connectionSocketToServer();
                gameClientController.waitForEndGame();
            } while (true);
    }

    /**
     * While game is playing controller is waiting for the game to end.
     *
     * @author Francesco Ostidich
     */
    private void waitForEndGame() {
        synchronized (gameView) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Defines all actions needed from the player before connecting to the server.
     *
     * @author Francesco Ostidich
     */
    private static void lobbyActions() {
        gameView.start();
        String IP = gameView.chooseIPAddress();
        String playerName = gameView.choosePlayerName();
        gameClientNetwork = new GameClientNetwork(IP, playerName, gameView);
    }

    @Override
    public synchronized void endGame() {
        this.notifyAll();
    }

}
