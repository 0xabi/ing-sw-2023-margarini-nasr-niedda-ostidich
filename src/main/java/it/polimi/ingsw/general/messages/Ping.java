package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class Ping extends Message {
    public Ping(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
