package it.polimi.ingsw.messages;

import it.polimi.ingsw.server.model.Tile;

import java.util.List;

/**
 * This message contains a list of tiles.
 *
 * @author Francesco Ostidich
 */
public class TileListMessage extends Message {

    private final List<Tile> tiles;

    /**
     * Class constructor.
     *
     * @param senderID is the ID of the sender
     * @param tiles is the list of tiles picked
     * @author Francesco Ostidich
     */
    public TileListMessage(String senderID, String messageID, List<Tile> tiles) {
        super(senderID, messageID);
        this.tiles = tiles;
    }

    /**
     * Getter for tiles.
     *
     * @return the list of tiles
     * @author Francesco Ostidich
     */
    public List<Tile> getTiles() {
        return tiles;
    }

}