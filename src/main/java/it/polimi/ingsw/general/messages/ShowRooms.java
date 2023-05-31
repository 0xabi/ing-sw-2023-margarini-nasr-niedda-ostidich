package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

import java.util.List;

public class ShowRooms extends Message {

    private final List<GameRoom> gameRooms;

    public ShowRooms(String playerName, MessageID messageID, List<GameRoom> gameRooms) {
        super(playerName, messageID);
        this.gameRooms = gameRooms;
    }

    public List<GameRoom> getGameRooms() {
        return gameRooms;
    }
}
