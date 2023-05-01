package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerModel;
import it.polimi.ingsw.server.model.GameServerModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * The game model controller is to decide actions to make based on game state and messages received.
 * It accesses the model via the model access class and talks to the virtual view as it was the real view, not knowing
 * there's a connection with other clients.
 *
 * @author Francesco Ostidich
 */
public class GameServerController extends RoomServices {

    private String playerTurn;

    private String playerAction;

    private final List<String> names;

    private final ServerModel model;

    /**
     * Class constructor.
     *
     * @param clients is the players' client interfaces map
     * @author Francesco Ostidich
     */
    public GameServerController(@NotNull Map<String, ClientController> clients) {
        model = new GameServerModel(clients.keySet());
        this.names = model.getTurnCycleOrder();
        playMatch();
    }

    /**
     * Starts and plays until the end a match between players provided.
     *
     * @author Francesco Ostidich
     */
    public void playMatch() {

    }

    @Override
    public void disconnectedPlayer(String playerName) {

    }

    @Override
    public void pickTilesRequest(@NotNull Message message) {

    }

    @Override
    public void insertTilesRequest(Message message) {

    }

}
