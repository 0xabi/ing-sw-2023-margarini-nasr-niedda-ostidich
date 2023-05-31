package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

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
