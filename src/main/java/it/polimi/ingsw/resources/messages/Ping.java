package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class Ping extends Message {
    public Ping(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
