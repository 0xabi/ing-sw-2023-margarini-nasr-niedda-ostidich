package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.messages.TileListMessage;
import it.polimi.ingsw.resources.Tile;

import java.util.List;

/**
 * Based on the event and on the type of information to send, there should be methods to wrap data in a message object accordingly.
 * For example, every class that implements this interface, should in theory have different message IDs.
 * Methods defined in this class automatically adds player's ID and message's ID to the envelope.
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
