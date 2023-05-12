package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.Tile;

import java.util.List;

public class PickAccepted extends Message {

    private final List<Tile> pickedTiles;

    public PickAccepted(String playerName, MessageID messageID, List<Tile> pickedTiles) {
        super(playerName, messageID);
        this.pickedTiles = pickedTiles;
    }

    public List<Tile> getPickedTiles() {
        return pickedTiles;
    }
}
