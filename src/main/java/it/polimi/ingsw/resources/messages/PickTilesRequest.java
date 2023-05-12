package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

import java.util.List;

public class PickTilesRequest extends Message {

    private final List<Coordinates> chosenCoordinates;

    public PickTilesRequest(String playerName, MessageID messageID, List<Coordinates> chosenCoordinates) {
        super(playerName, messageID);
        this.chosenCoordinates = chosenCoordinates;
    }

    public List<Coordinates> getChosenCoordinates() {
        return chosenCoordinates;
    }

}
