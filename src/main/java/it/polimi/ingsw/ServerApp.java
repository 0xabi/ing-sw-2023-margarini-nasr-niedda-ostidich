package it.polimi.ingsw;

import it.polimi.ingsw.server.serverController.RoomServices;
import it.polimi.ingsw.server.serverNetwork.GameServerNetwork;

import java.rmi.RemoteException;

public class ServerApp {

    public static void main(String[] args) {
        try {
            RoomServices.setServerNetwork(new GameServerNetwork(new RoomServices()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
