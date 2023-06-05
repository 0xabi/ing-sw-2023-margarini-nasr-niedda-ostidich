package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

public class Chat extends Message {

    private final String message;

    public Chat(String playerName, MessageID messageID, String message) {
        super(playerName, messageID);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
