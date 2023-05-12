package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

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
