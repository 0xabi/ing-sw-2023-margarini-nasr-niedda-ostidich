package it.polimi.ingsw.interfaces;

import it.polimi.ingsw.messages.TileListMessage;
import it.polimi.ingsw.server.model.Tile;

import java.util.List;

/**
 * Based on the event and on the type of information to send, there should be methods to wrap data in a message object accordingly.
 * For example, every class that implements this interface, should in theory have different message IDs.
 *
 * @author Francesco Ostidich
 */
public interface MessagePackaging {

    /**
     * Generate a message with a list of tiles.
     *
     * @param tiles is the list of tiles
     * @return the message with the tiles list
     * @author Francesco Ostidich
     */
    TileListMessage tileToInsertOrdered(List<Tile> tiles);

}
