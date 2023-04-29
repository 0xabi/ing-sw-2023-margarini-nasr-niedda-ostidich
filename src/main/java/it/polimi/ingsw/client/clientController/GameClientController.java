package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.clientNetwork.GameClientNetwork;
import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.EventID;
import it.polimi.ingsw.resources.interfaces.ClientNetworkToController;
import org.jetbrains.annotations.NotNull;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameClientController implements ClientNetworkToController {

    private final GameView gameView;

    private static GameClientNetwork gameClientNetwork;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientController(GameView gameView, String network) {
        this.gameView = gameView;
        if(network == null) gameClientNetwork.connectionSocketToServer();
        else if(network.equals("RMI")) gameClientNetwork.connectionRMIToServer();
        else gameClientNetwork.connectionSocketToServer();
        gameView.start();
    }

    /**
     * Used to pass method request answer to the controller from the view.
     * It contains switch cases based on event type.
     *
     * @author Francesco Ostidich
     * @param evt is the event information
     */
    public void update(@NotNull Event evt) {
        if(evt.eventName() == EventID.SET_GAME_PARAMETER)
            System.out.println("Eureka!");
        else System.out.println("Maybe not Eureka!");
    }

}
