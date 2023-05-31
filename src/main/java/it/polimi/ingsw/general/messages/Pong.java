package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class Pong extends Message {
    public Pong(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
