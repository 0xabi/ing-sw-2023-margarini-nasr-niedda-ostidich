package it.polimi.ingsw.general.messages;

import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;

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
