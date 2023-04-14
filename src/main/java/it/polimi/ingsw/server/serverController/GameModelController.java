package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.server.model.GameModel;

import java.util.List;

/**
 * The game model controller is to decide actions to make based on game state and messages received.
 * It accesses the model via the model access class and talks to the virtual view as it was the real view, not knowing
 * there's a connection with other clients.
 *
 * @author Francesco Ostidich
 */
public class GameModelController {

    private final VirtualViewAccess virtualViewAccess;

    private final ModelAccess modelAccess;

    private List<String> names;

    /**
     * Class constructor.
     *
     * @param virtualViewAccess is the bridge between server controller and virtual view
     * @param modelAccess       is the bridge between server controller and model
     * @author Francesco Ostidich
     */
    public GameModelController(VirtualViewAccess virtualViewAccess, ModelAccess modelAccess) {
        waitForPlayers();
        this.virtualViewAccess = new VirtualViewAccess(this);
        this.modelAccess = new ModelAccess(new GameModel(names));
    }

    /**
     * Waits for players to enter the lobby. Writes their names on the names list.
     *
     * @author Francesco Ostidich
     */
    private void waitForPlayers() {

    }

    /**
     * Switches between actions to do based on the content of the message.
     *
     * @author Francesco Ostidich
     * @param message contains information to be processed
     */
    protected void processMessage(Message message) {

    }

}
