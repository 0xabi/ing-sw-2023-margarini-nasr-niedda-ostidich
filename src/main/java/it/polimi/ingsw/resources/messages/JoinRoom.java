package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class JoinRoom extends Message {

    private final String chosenRoom;

    public JoinRoom(String playerName, MessageID messageID, String chosenRoom) {
        super(playerName, messageID);
        this.chosenRoom = chosenRoom;
    }

    public String getChosenRoom() {
        return chosenRoom;
    }

}
