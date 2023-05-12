package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.server.serverController.RoomServices;
import it.polimi.ingsw.server.serverNetwork.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Queue;

/**
 * Asks for connection to the server, and wants the ServerController interface to call methods on it.
 * When "connect" method is called, the connection must be built.
 * Every action done on the ServerController interface must be forwarded to the server either as RMI or Socket calling
 * the method directly in the server on the controller.
 */
public class GameClientNetwork{

    private String connectionType = null;

    private Socket socket;

    private ObjectOutputStream MessageToServer;
    private ObjectInputStream MessageFromServer;

    private Queue<Message> messageQueue;

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


    public void connect(String serverIP, String playerName, ClientController controller) throws Exception {
        this.serverIP = serverIP;
        this.playerName = playerName;
        this.controller = controller;
          if (connectionType == "Socket") {
            try (Socket socket = new Socket(serverIP, 8080);) {
            } catch (SocketException e) {
                //connessione non stabilita
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.socket = socket;

            this.MessageToServer = new ObjectOutputStream(socket.getOutputStream());
            this.MessageFromServer = new ObjectInputStream(socket.getInputStream());
            new Thread(() -> {
                try {
                    Sorter();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
            new Thread(this::ServerSocketListener).start();

            send(new Message(playerName, MessageID.HELLO));

            //mando un messaggio con nome del player cosi che posso aggiungerlo lato server alla mia lista
              // se c'è gia un player con lo stesso nome allora sono obbligato a ""restartare"" il tutto.
        }


    }

    public void send(Message message) throws IOException {
        if (connectionType == "Socket") {
            MessageToServer.writeObject(message);
        }
    }

    public void ServerSocketListener(){
        Message serverMessage;
        while (true) { //sistemare e mettere while "client is alive"
            try {
                serverMessage = (Message) MessageFromServer.readObject();
            } catch (IOException | ClassNotFoundException e) {
                //vuol dire che mi sono disconnesso dal server
                //devo avvisare il client
                break;
            }
            messageQueue.add(serverMessage);
        }
    }

    public void Sorter() throws Exception {
        while(true){ //sistemare e mettere while "client is alive"
            if(messageQueue.size() > 0){
                Message msg = messageQueue.remove();
                switch(msg.messageID()) {
                    case NEW_TURN -> {
                        controller.newTurn(msg);
                    }
                    case NOTIFY_GAME_HAS_STARTED -> {
                        controller.notifyGameHasStarted(msg);
                    }
                    case PICK_ACCEPTED -> {
                        controller.pickAccepted(msg);
                    }
                    case SHOW_ROOMS -> {
                        controller.showRooms(msg);
                    }
                    case SHOW_PERSONAL_ROOM -> {
                        controller.showPersonalRoom(msg);
                    }
                    case DISCONNECT_PLAYERS -> {
                        //gestire disconnessione!
                    }
                    case PLAYER_ACCEPTED -> {

                    }
                    case PLAYER_NOT_ACCEPTED -> {
                        //restarto la view
                    }
                    case PING -> {
                        send(new Message(playerName,MessageID.PONG));
                    }
                }

            }
        }
    }

}
