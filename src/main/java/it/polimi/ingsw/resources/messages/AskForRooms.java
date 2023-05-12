package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class AskForRooms extends Message {

    public AskForRooms(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }

}
