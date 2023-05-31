package it.polimi.ingsw.general.interfaces;

import it.polimi.ingsw.general.Message;

import java.io.IOException;

public interface ClientNetwork {


    ServerController connect(String serverIP, String playerName, ClientController controller);

    void send(Message message) throws IOException;
}
