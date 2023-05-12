package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class RoomNameNotAvailable extends Message {

    public RoomNameNotAvailable(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
