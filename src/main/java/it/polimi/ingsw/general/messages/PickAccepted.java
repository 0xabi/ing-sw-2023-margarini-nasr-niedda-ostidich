package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

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
