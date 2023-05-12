package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class PlayerNotAccepted extends Message {
    public PlayerNotAccepted(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
