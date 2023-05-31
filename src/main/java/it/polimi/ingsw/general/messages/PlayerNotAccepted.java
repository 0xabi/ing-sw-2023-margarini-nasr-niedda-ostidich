package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class PlayerNotAccepted extends Message {
    public PlayerNotAccepted(String playerName, MessageID messageID) {
        super(playerName, messageID);
    }
}
