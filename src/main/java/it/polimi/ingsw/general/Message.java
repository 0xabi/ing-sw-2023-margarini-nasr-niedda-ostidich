package it.polimi.ingsw.general;

import java.io.Serializable;

/**
 * A message is an object which encapsulates an information to be serialized and sent via a solid connection.
 * It contains the name of the player, the ID of the method to call and the parameter object.
 * Casting is required when trying to get the data.
 *
 * @author Francesco Ostidich
 */
public abstract class Message implements Serializable {

    private final String playerName;

    private final MessageID messageID;

    public Message(String playerName, MessageID messageID) {
        this.playerName = playerName;
        this.messageID = messageID;
    }

    public MessageID getMessageID() {
        return messageID;
    }

    public String getPlayerName() {
        return playerName;
    }

}