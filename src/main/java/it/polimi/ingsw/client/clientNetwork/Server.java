package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.messages.*;

import java.io.IOException;
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
        } catch (IOException ignored) {}
    }

    @Override
    public void insertTilesRequest(InsertTilesRequest message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {}
    }

    @Override
    public void joinRoom(JoinRoom message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {}
    }

    @Override
    public void createNewRoom(CreateNewRoom message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {}
    }

    @Override
    public void askForRooms(AskForRooms message) {
        try {
            gameClientNetwork.send(message);
        } catch (IOException ignored) {}
    }
}
