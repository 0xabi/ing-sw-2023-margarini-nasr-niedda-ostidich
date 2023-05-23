package it.polimi.ingsw.client.clientNetwork;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ClientNetwork;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.messages.*;
import it.polimi.ingsw.server.serverController.RoomServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

/**
 * Asks for connection to the server, and wants the ServerController interface to call methods on it.
 * When "connect" method is called, the connection must be built.
 * Every action done on the ServerController interface must be forwarded to the server either as RMI or Socket calling
 * the method directly in the server on the controller.
 */
public class GameClientNetwork implements ClientNetwork {

    private final String connectionType;

    private Socket socket;

    private ObjectOutputStream MessageToServer;

    private ObjectInputStream MessageFromServer;

    private Queue<Message> messageQueue;

    private ClientController controller;

    private String serverIP;

    private String playerName;

    private ServerController roomServices;

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
        boolean connected = false;
        if(Objects.equals(connectionType, "Socket"))
        while (!connected) {
            try {
                //FIXME: Socket without try catch with resources
                //noinspection resource
                socket = new Socket(serverIP, 8000);
                connected = true;
                messageQueue= new LinkedList<>();
                this.MessageToServer = new ObjectOutputStream(socket.getOutputStream());
                this.MessageFromServer = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                System.out.println("connection problems!");
            }
            new Thread(() -> {
                try {
                    Sorter();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
            new Thread(this::ServerSocketListener).start();
            try {
                send(new Hello(playerName, MessageID.HELLO));
            } catch (IOException ignored) {
            }
        }

        if(Objects.equals(connectionType, "RMI"))
        while(!connected){
            try {
                Registry registry = LocateRegistry.getRegistry();
                ServerController server = (ServerController) registry.lookup("Connection");
                roomServices = server;
                connected = true;
                if(roomServices.PlayerIDisAvailable(new Hello(playerName, MessageID.HELLO))) {
                    new Thread(()->{
                        try {
                            roomServices.playerConnected(playerName, controller);
                        controller.serverConnected();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }}).start();
                }else controller.restart();
                return roomServices;


            }catch (Exception e) {
                System.out.println("[System] Server failed: " + e);
                break;
            }

        }

        return new Server(this);
    }

    @Override
    public void send(Message message) throws IOException {
        if (Objects.equals(connectionType, "Socket")) {
            try {
                MessageToServer.writeObject(message);
            } catch (IOException e) {
                System.out.println("IO Exception: no message is sent");
                throw new IOException(e);
            }
        }
    }

    public void ServerSocketListener() {
        Message serverMessage;
        while (true) { //FIXME: add while "client is alive"
            try {
                serverMessage = (Message) MessageFromServer.readObject();
            } catch (IOException | ClassNotFoundException e) {
                //TODO: client is to be advised that connection with server has fallen
                break;
            }
            if (serverMessage.getMessageID() == MessageID.PING) {
                try {
                    send(new Pong(playerName, MessageID.PONG));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else
                messageQueue.add(serverMessage);
        }
    }

    public void Sorter() throws Exception {
        //noinspection InfiniteLoopStatement
        while (true) { //FIXME: and put while "client is alive"
            //noinspection BusyWait
            Thread.sleep(1000);
            if (messageQueue.size() > 0) {
                try {
                    Message msg = messageQueue.remove();
                    switch (msg.getMessageID()) {
                        case NEW_TURN -> controller.newTurn((NewTurn) msg);
                        case NOTIFY_GAME_HAS_STARTED -> controller.notifyGameHasStarted((NotifyGameHasStarted) msg);
                        case PICK_ACCEPTED -> controller.pickAccepted((PickAccepted) msg);
                        case SHOW_ROOMS -> controller.showRooms((ShowRooms) msg);
                        case SHOW_PERSONAL_ROOM -> controller.showPersonalRoom((ShowPersonalRoom) msg);
                        case DISCONNECT_PLAYERS -> {
                            //TODO: manage disconnection
                        }
                        case PLAYER_ACCEPTED -> controller.serverConnected();
                        case PLAYER_NOT_ACCEPTED -> controller.restart();
                        case PING -> send(new Pong(playerName, MessageID.PONG));
                    }
                } catch (ClassCastException ignored) {
                }
            }
        }
    }



}
