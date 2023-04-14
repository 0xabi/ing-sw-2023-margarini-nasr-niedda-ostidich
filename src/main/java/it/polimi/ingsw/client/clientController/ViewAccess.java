package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.interfaces.ViewActions;
import it.polimi.ingsw.server.model.Board;

/**
 * Method are called by the controller to notify or retrieve information from the view. Information from view is directly
 * type of return in controller method, passing by here.
 *
 * @author Francesco Ostidich
 */
public class ViewAccess implements ViewActions {

    private final GameView gameView;

    /**
     * Class constructor.
     *
     * @param gameView is the view core of the client
     * @author Francesco Ostidich
     */
    protected ViewAccess(GameView gameView){
        this.gameView = gameView;
    }

    @Override
    public void displayBoard(Board board) {

    }

}
