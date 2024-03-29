package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ServerController;
import it.polimi.ingsw.general.interfaces.ServerNetwork;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Receives connection requests from clients, and sends the client interface to the RoomServices.java.
 * When "disconnect" method is called, the connection must be interrupted.
 * Every action done on the ClientController interface must be forwarded to the client either as RMI or Socket calling
 * the method directly in the client on the controller.
 */
public class GameServerNetwork implements ServerNetwork, Serializable {

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
            ss = new ServerSocket(34634);
            running = true;
        } catch (IOException e) {
            running = false;
        }
        waitForClients();
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     * The method creates a registry for the RMI connection on port 1099 and binds the "Connection" name to the server controller.
     * It then continuously accepts incoming client socket connections and creates a new ClientController object for each client.
     * The client's connection is accepted and a message is printed to indicate the connection acceptance.
     * If an I/O exception occurs during connection acceptance, a message is printed indicating the failure.
     * Note: The method runs in its own thread and is executed as long as the server is running.
     *
     * @author Edoardo Margarini
     */

    private void waitForClients() {
        System.out.println("server listening");
        try {
            InetAddress hostIP = InetAddress.getLocalHost();
            System.setProperty("java.rmi.server.hostname", hostIP.getHostAddress());
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("Connection", roomServices);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            System.out.println("Impossible to determinate IP address");
            throw new RuntimeException(e);
        }
        while (running) {
            try {
                Socket client = ss.accept();
                ClientController newClient = new Client(client, roomServices);
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "]" + " Connection accepted: " + client);
            } catch (IOException e) {
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "]" + " Connection not accepted");
            }
        }
    }

    @Override
    public void disconnect(List<String> names) {
    }

}
