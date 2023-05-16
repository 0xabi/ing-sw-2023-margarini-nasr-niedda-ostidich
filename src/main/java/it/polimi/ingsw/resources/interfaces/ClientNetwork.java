package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Message;

import java.io.IOException;

public interface ClientNetwork {


    ServerController connect(String serverIP, String playerName, ClientController controller);

    void send(Message message) throws IOException;
}
