package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerModel;
import it.polimi.ingsw.server.model.GameServerModel;
import it.polimi.ingsw.server.serverNetwork.Client;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
    public GameServerController(@NotNull Map<String, Client> clients) {
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
        getClients().keySet().forEach(player -> {
            try {
                getClients().get(player).send(new Message(player, MessageID.NOTIFY_GAME_HAS_STARTED));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void disconnectedPlayer(String playerName) {

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void reconnectedPlayer(String playerName) {

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void pickTilesRequest(@NotNull Message message) {

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void insertTilesRequest(Message message) {

    }


}
