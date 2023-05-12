package it.polimi.ingsw.resources.messages;

import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;

public class ShowPersonalRoom extends Message {

    private final GameRoom gameRoom;

    public ShowPersonalRoom(String playerName, MessageID messageID, GameRoom gameRoom) {
        super(playerName, messageID);
        this.gameRoom = gameRoom;
    }

    public GameRoom getGameRoom() {
        return gameRoom;
    }
}
