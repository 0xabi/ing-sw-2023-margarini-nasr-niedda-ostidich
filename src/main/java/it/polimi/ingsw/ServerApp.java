package it.polimi.ingsw;

import it.polimi.ingsw.server.serverController.RoomServices;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;

public class ServerApp {

    public static void main(String[] args) {
        RoomServices.setGameServerNetwork(new GameServerNetwork(new RoomServices()));
    }

}
