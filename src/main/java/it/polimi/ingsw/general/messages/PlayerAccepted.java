package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class PlayerAccepted extends Message {
    public PlayerAccepted(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
