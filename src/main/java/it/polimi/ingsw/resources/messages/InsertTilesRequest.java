package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.Tile;

import java.util.List;

public class InsertTilesRequest extends Message {

    private final List<Tile> chosenTiles;

    public List<Tile> getChosenTiles() {
        return chosenTiles;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    private final int chosenColumn;

    public InsertTilesRequest(String playerName, MessageID messageID, List<Tile> chosenTiles, int chosenColumn) {
        super(playerName, messageID);
        this.chosenTiles = chosenTiles;
        this.chosenColumn = chosenColumn;
    }
}
