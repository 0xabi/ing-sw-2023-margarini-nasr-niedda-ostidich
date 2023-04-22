package it.polimi.ingsw.resources.messages.MessageFromClient;

public class sendString extends MessageFromClient{

    protected String content;

    public sendString(String PlayerID, String bodyOfMessage) {
        super("send string", PlayerID);
        content=bodyOfMessage;
    }

    public String getContent(){
        return content;
    }

}
