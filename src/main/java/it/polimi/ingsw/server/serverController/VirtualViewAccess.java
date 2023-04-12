package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.interfaces.ControllerCommunication;
import it.polimi.ingsw.interfaces.MessagePackaging;
import it.polimi.ingsw.interfaces.ViewActions;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.TileListMessage;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Tile;

import java.util.List;

public class VirtualViewAccess implements ControllerCommunication, ViewActions, MessagePackaging {

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
