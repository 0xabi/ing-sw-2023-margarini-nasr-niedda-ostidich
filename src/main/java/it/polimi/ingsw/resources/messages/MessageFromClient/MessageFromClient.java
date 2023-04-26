package it.polimi.ingsw.resources.messages.MessageFromClient;

import it.polimi.ingsw.resources.messages.Message;
public class MessageFromClient extends Message{

    protected final String playerID;

    public MessageFromClient(String msgID, String PlayerID){
        super(msgID);
        playerID=PlayerID;
    }

    public String getPlayerID(){
        return playerID;
    }


}