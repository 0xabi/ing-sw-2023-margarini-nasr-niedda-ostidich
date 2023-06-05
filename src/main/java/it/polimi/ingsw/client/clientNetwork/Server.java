package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ServerController;
import it.polimi.ingsw.general.messages.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Set;

public class Server implements ServerController {

    private final GameClientNetwork gameClientNetwork;

    public Server(GameClientNetwork gameClientNetwork) {
        this.gameClientNetwork = gameClientNetwork;
    }

    @Override
    public Set<String> onlinePlayers() {
        throw new RuntimeException("client cannot call onlinePlayers");
    }

    @Override
    public void playerConnected(String playerName, ClientController client) {
        throw new RuntimeException("client cannot call playerConnected");
    }

    @Override
    public void disconnectedPlayer(String playerName) {
        throw new RuntimeException("client cannot call playerDisconnected");
    }

    @Override
    public void reconnectedPlayer(String playerName) {
        throw new RuntimeException("client cannot call playerReconnected");
    }

    @Override
    public void pickTilesRequest(PickTilesRequest message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void insertTilesRequest(InsertTilesRequest message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void joinRoom(JoinRoom message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void createNewRoom(CreateNewRoom message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    @Override
    public void askForRooms(AskForRooms message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public boolean PlayerIDisAvailable(Hello msg) {
        throw new RuntimeException("client cannot call PlayerIDisAvailable");
    }

    @Override
    public ServerController getGSC(String playerName) {
        throw new RuntimeException("client cannot call getGSC");
    }

    @Override
    public void chatMessage(Chat message) throws RemoteException {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {
        }
    }

}
