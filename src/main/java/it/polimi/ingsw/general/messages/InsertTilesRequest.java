package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;

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
