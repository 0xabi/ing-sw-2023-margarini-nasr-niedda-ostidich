package it.polimi.ingsw.resources.messages;

import java.io.Serializable;

public class Message implements Serializable {

    protected String MessageID;

    public Message(String msgID){
        MessageID=msgID;
    }
    public String getID(){
        return MessageID;
    }
}
