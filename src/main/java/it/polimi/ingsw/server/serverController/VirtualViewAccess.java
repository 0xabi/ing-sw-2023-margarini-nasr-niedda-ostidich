package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.interfaces.ControllerCommunication;
import it.polimi.ingsw.interfaces.MessagePackaging;
import it.polimi.ingsw.interfaces.ViewActions;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.TileListMessage;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Tile;
import it.polimi.ingsw.server.virtualView.GameVirtualView;

import java.util.List;

/**
 * Methods are called from the controller to wrap information and to send it on the connection bridge, to be processed
 * by the clients. When messages are received them forwards them to the controller.
 *
 * @author Francesco Ostidich
 */
public class VirtualViewAccess implements ControllerCommunication, ViewActions, MessagePackaging {

    private final GameVirtualView gameVirtualView;

    private final GameModelController gameModelController;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param gameModelController processes events and data
     */
    public VirtualViewAccess(GameModelController gameModelController) {
        this.gameVirtualView = new GameVirtualView(this);
        this.gameModelController = gameModelController;
    }

    @Override
    public void sendToController(Message message) {

    }

    @Override
    public TileListMessage tileToInsertOrdered(List<Tile> tiles) {
        return null;
    }

    @Override
    public void displayBoard(Board board) {

    }

}
