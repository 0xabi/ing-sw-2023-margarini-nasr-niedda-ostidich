package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.Message;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameViewController {

    private final NetworkHandlerAccess networkHandlerAccess;

    private final ViewAccess viewAccess;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameViewController() {
        networkHandlerAccess = new NetworkHandlerAccess(this);
        viewAccess = new ViewAccess(new GameView());
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
