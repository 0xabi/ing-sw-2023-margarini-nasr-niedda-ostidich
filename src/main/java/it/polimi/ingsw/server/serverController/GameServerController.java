package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.interfaces.ServerModel;
import it.polimi.ingsw.server.model.GameServerModel;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;

import java.util.List;

/**
 * The game model controller is to decide actions to make based on game state and messages received.
 * It accesses the model via the model access class and talks to the virtual view as it was the real view, not knowing
 * there's a connection with other clients.
 *
 * @author Francesco Ostidich
 */
public class GameServerController implements ServerController {

    private String playerTurn;

    private String playerAction;

    private final List<String> names;

    private final ServerModel model;

    private static final GameServerNetwork gameServerNetwork = new GameServerNetwork();

    /**
     * Class constructor.
     *
     * @param names is the players' names string list
     * @author Francesco Ostidich
     */
    public GameServerController(List<String> names) {
        model = new GameServerModel(names);
        this.names = model.getTurnCycleOrder();
    }

    /**
     * Starts and plays until the end a match between players provided.
     *
     * @author Francesco Ostidich
     */
    public void playMatch() {

    }

    @Override
    public void disconnected(String playerName) {

    }

    @Override
    public void pickTilesRequest(Message message) {

    }

    @Override
    public void insertTilesRequest(Message message) {

    }

}
