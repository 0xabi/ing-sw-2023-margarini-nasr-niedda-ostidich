package it.polimi.ingsw;

import it.polimi.ingsw.server.serverController.RoomServices;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        RoomServices.setGameServerNetwork(new GameServerNetwork(new RoomServices()));
    }

}
