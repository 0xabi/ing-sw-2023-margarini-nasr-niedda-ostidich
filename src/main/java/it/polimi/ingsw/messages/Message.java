package it.polimi.ingsw.messages;

/**
 * A message is an object which encapsulates an information to be serialized and sent via a solid connection.
 *
 * @author Francesco Ostidich
 */
public abstract class Message {

    private final String senderID;

    private final String messageID;

    /**
     * Class constructor.
     *
     * @param senderID  is the ID of the player or of the server game
     * @param messageID is the ID of the message
     * @author Francesco Ostidich
     */
    public Message(String senderID, String messageID) {
        this.senderID = senderID;
        this.messageID = messageID;
    }

    /**
     * Getter for senderID.
     *
     * @return the ID of the sender
     * @author Francesco Ostidich
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Getter for messageID.
     *
     * @return the ID of the message
     * @author Francesco Ostidich
     */
    public String getMessageID() {
        return messageID;
    }
}