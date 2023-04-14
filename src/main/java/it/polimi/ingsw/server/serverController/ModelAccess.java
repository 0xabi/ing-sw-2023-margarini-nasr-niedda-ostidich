package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.interfaces.ModelActions;
import it.polimi.ingsw.server.model.GameModel;

import java.util.List;
import java.util.Map;

/**
 * Method are called by the controller to notify or retrieve information from the model. Information from model is directly
 * type of return in controller method, passing by here.
 *
 * @author Francesco Ostidich
 */
public class ModelAccess implements ModelActions {

    private final GameModel gameModel;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param gameModel is the model core of the server
     */
    public ModelAccess(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public Map<String, Integer> calculatePoints() {
        return null;
    }

    @Override
    public List<String> getTurnCycleOrder() {
        return null;
    }

}
