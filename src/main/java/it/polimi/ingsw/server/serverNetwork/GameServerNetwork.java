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
    public GameServerNetwork(ServerController roomServices){
        this.roomServices = roomServices;
        try {
            ss = new ServerSocket(8080);
            running = true;
        } catch (IOException e){
            running = false;
        }

        waitForClients();
    }

    /**
     * Activate the gameVirtualView, so it can be listening for clients requests.
     * Method has its own thread, and it is up for all server run time.
     */
    private void waitForClients() {


        while (running == true) {
            try {
                Socket client = ss.accept();
                new Thread(() -> {
                    try {
                        new Client(client, roomServices);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                System.out.println("["+LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)+"]");
                System.out.println("Connection accepted:"+ client.toString());
                } catch (IOException e) {
                System.out.println("["+LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)+"]");
                System.out.println("Connection not accepted");
                }
            }
        System.out.println("\nServer Offline");
    }


    @Override
    public void disconnect(List<String> names) {

    }
}
