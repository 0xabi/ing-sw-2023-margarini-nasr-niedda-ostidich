package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.networkHandler.GameNetworkHandler;
import it.polimi.ingsw.resources.interfaces.MessagePackaging;
import it.polimi.ingsw.resources.messages.TileListMessage;
import it.polimi.ingsw.resources.Tile;

import java.util.List;

/**
 * Methods are called from the controller to wrap information and to send it on the connection bridge, to be processed
 * by the server. When messages are received it forwards it to the controller.
 *
 * @author Francesco Ostidich
 */
public class NetworkHandlerAccess implements MessagePackaging {

    private final GameNetworkHandler gameNetworkHandler;

    private final GameViewController gameViewController;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param gameViewController processes events and data
     */
    protected NetworkHandlerAccess(GameViewController gameViewController) {
        this.gameNetworkHandler = new GameNetworkHandler(this);
        this.gameViewController = gameViewController;
    }

    @Override
    public TileListMessage tileToInsertOrdered(List<Tile> tiles) {
        return null;
    }

}
