package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;

import java.util.List;

/**
 * The game model controller is to decide actions to make based on game state and messages received.
 * It accesses the model via the model access class and talks to the virtual view as it was the real view, not knowing
 * there's a connection with other clients.
 *
 * @author Francesco Ostidich
 */
public class GameServerController {

    private final List<String> names;

    private final GameModel gameModel;

    private static final GameServerNetwork gameServerNetwork = new GameServerNetwork(new LobbyBuilder());

    /**
     * Class constructor.
     *
     * @param names is the players' names string list
     * @author Francesco Ostidich
     */
    public GameServerController(List<String> names) {
        gameModel = new GameModel(names);
        this.names = gameModel.getTurnCycleOrder();
    }

    public static void main(String[] args) {
    }

    /**
     * Starts and plays until the end a match between players provided.
     *
     * @author Francesco Ostidich
     */
    public void playMatch() {

    }

}
