package it.polimi.ingsw.resources.messages.MessageFromClient;

public class sendInteger extends MessageFromClient{

    protected int content;

    public sendInteger(String PlayerID, int integer) {
        super("send string", PlayerID);
        content=integer;
    }

    public int getContent(){
        return content;
    }

}
