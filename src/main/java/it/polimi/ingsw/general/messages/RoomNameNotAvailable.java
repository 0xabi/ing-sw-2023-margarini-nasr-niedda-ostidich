package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class RoomNameNotAvailable extends Message {

    public RoomNameNotAvailable(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
