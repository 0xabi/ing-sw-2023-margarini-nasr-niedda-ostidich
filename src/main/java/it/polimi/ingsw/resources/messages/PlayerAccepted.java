package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class PlayerAccepted extends Message {
    public PlayerAccepted(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
