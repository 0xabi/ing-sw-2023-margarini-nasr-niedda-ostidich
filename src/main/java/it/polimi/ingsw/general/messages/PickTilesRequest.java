package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

import java.io.Serializable;
import java.util.List;

public class PickTilesRequest extends Message implements Serializable {

    private final List<Coordinates> chosenCoordinates;

    public PickTilesRequest(String playerName, MessageID messageID, List<Coordinates> chosenCoordinates) {
        super(playerName, messageID);
        this.chosenCoordinates = chosenCoordinates;
    }

    public List<Coordinates> getChosenCoordinates() {
        return chosenCoordinates;
    }

}
