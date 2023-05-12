package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class Hello extends Message {
    public Hello(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
