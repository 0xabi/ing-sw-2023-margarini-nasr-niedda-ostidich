package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.interfaces.ViewActions;
import it.polimi.ingsw.server.model.GameModel;
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
public class GameModelController {

    private final Map<String, ViewActions> clientViews;

    private final List<String> turnCycleOrder;

    private final ModelAccess modelAccess;

    /**
     * Class constructor.
     *
     * @param clientViews is the map referencing view objects of respective players
     * @author Francesco Ostidich
     */
    public GameModelController(@NotNull Map<String, ViewActions> clientViews) {
        this.clientViews = clientViews;
        this.modelAccess = new ModelAccess(new GameModel(clientViews.keySet().stream().toList()));
        turnCycleOrder = modelAccess.getTurnCycleOrder();
    }

    /**
     * Starts and plays until the end a match between players provided.
     *
     * @author Francesco Ostidich
     */
    public void playMatch() {

    }

}
