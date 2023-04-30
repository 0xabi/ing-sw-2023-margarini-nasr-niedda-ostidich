package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ClientNetwork;
import it.polimi.ingsw.resources.interfaces.ServerController;

/**
 * When a server message is received, based on player name and on message ID, the right method is called on the view
 * and waited for return data, to pack and send back.
 * In case of the "justScanChat" method, when called from the server, a dedicated thread is started so method can always be up and
 * running.
 */
public class GameClientNetwork implements ClientNetwork {

    private final String connectionType;

    private ClientController controller;

    private String serverIP;

    private String playerName;

    /**
     * Class constructor.
     *
     * @param connectionType is the type of connection
     * @author Francesco Ostidich
     */
    public GameClientNetwork(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public ServerController connect(String serverIP, String playerName, ClientController controller) {
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.controller = controller;
        return null;
    }

    @Override
    public void joinRoom(String room) {

    }

    @Override
    public void createNewRoom(String newRoomName, int playerNumber) {

    }

    @Override
    public void askForRooms() {

    }

}
