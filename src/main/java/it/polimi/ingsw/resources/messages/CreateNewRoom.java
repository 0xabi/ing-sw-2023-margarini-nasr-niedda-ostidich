package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class CreateNewRoom extends Message {

    private final String roomName;

    private final int roomPlayerNumber;

    public CreateNewRoom(String playerName, MessageID messageID, String roomName, int roomPlayerNumber) {
        super(playerName, messageID);
        this.roomName = roomName;
        this.roomPlayerNumber = roomPlayerNumber;
    }

    public int getRoomPlayerNumber() {
        return roomPlayerNumber;
    }

    public String getRoomName() {
        return roomName;
    }

}
