package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class AskForRooms extends Message {

    public AskForRooms(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }

}
