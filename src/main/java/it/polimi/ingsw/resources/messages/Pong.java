package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class Pong extends Message {
    public Pong(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
