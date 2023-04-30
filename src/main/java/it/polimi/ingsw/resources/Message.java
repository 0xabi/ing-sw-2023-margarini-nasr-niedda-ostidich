package it.polimi.ingsw.resources;

import it.polimi.ingsw.resources.exceptions.UnavailableContentSizeException;

/**
 * A message is an object which encapsulates an information to be serialized and sent via a solid connection.
 * It contains the name of the player, the ID of the method to call and the parameter object.
 * Casting is required when trying to get the data.
 *
 * @author Francesco Ostidich
 * @param playerName is the player name of the receiving or sending client
 * @param messageID is the ID of the method to be called or to respond to
 * @param contents is the data to transfer
 */
public record Message(String playerName, MessageID messageID, Object... contents) {

    /**
     * If contents array is sized one, method acts like a getter for the only element in the array.
     *
     * @author Francesco Ostidich
     * @return first element of array
     * @throws UnavailableContentSizeException if contents array has a different size than one
     */
    public Object content() {
        if(contents.length != 1) throw new UnavailableContentSizeException("Message.content() is not callable from a message containing 0 or 2+ objects");
        return contents[0];
    }

}