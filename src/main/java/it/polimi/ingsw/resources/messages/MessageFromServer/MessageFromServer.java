package it.polimi.ingsw.resources.messages.MessageFromServer;

import it.polimi.ingsw.resources.messages.Message;
import it.polimi.ingsw.client.view.GameView;
public abstract class MessageFromServer extends Message{

    public MessageFromServer(String msgID){
        super(msgID);
    }
    public abstract void doAction(GameView CLIorGUI);

}

