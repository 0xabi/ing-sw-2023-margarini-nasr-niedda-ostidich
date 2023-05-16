package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.interfaces.ServerNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Receives connection requests from clients, and sends the client interface to the RoomServices.java.
 * When "disconnect" method is called, the connection must be interrupted.
 * Every action done on the ClientController interface must be forwarded to the client either as RMI or Socket calling
 * the method directly in the client on the controller.
 */
public class GameServerNetwork implements ServerNetwork {

    /**
     * Specifies if the server is running.
     */
    private boolean running;

    private final ServerController roomServices;

    private ServerSocket ss;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameServerNetwork(ServerController roomServices) {
        this.roomServices = roomServices;
        try {
            ss = new ServerSocket(8000);
            running = true;
        } catch (IOException e) {
            running = false;
        }
        waitForClients();
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     */
    private void waitForClients() {
        System.out.println("Server listening");
        while (running) {
            try {
                Socket client = ss.accept();
                new Client(client, roomServices);
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "]" + "Connection accepted:" + client);
            } catch (IOException e) {
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "]" + "Connection not accepted");
                System.out.println();
            }
        }
        System.out.println("\nServer Offline");
    }

    @Override
    public void disconnect(List<String> names) {
    }

}
