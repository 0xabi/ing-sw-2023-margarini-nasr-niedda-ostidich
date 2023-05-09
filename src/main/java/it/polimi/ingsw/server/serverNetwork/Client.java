package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.server.serverController.RoomServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Client{

    private boolean alive;
    private String connectionType = null;
    private final Socket clientSocket;
    private ObjectOutputStream MessageToClient;
    private final ObjectInputStream MessageFromClient;
    private Queue<Message> messageQueue;

    private Queue<Message> pingQueue;
    private final ServerController roomServices;

    private String playerName;

    public Client(Socket clientSocket, ServerController roomServices) throws IOException {

        this.clientSocket = clientSocket;
        MessageFromClient = new ObjectInputStream(clientSocket.getInputStream());
        MessageToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        this.roomServices = roomServices;
        messageQueue = new LinkedList<Message>();

        new Thread(() -> {
            try {
                Sorter();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(this::ClientSocketListener).start();
    }

    public boolean PlayerIDisAvailable(Message message){
        return roomServices.onlinePlayers().contains(message.playerName());
    }
    public void send(Message message) throws IOException {
        if (connectionType == "Socket") {
            MessageToClient.writeObject(message);
        }
    }

    public void ClientSocketListener(){
        Message clientMessage;
        while (alive) {
            try {
                clientMessage = (Message) MessageFromClient.readObject();
            } catch (IOException | ClassNotFoundException e) {
                break;
            }
            if(clientMessage.messageID()==MessageID.PONG)
                pingQueue.remove();
            else
                messageQueue.add(clientMessage);
        }
    }

    public void Sorter() throws IOException {
        while(alive){
            if(messageQueue.size() > 0){
               Message msg = messageQueue.remove();
                switch(msg.messageID()) {
                    case JOIN_ROOM -> {
                       roomServices.joinRoom(msg);
                    }
                    case INSERT_TILE_REQUEST -> {
                        roomServices.insertTilesRequest(msg);
                    }
                    case ASK_FOR_ROOMS -> {
                        roomServices.askForRooms(msg);
                    }
                    case CREATE_NEW_ROOM -> {
                        roomServices.createNewRoom(msg);
                    }
                    case PICK_TILES_REQUEST -> {
                        roomServices.pickTilesRequest(msg);
                    }
                    case HELLO -> {
                        if(PlayerIDisAvailable(msg)) {
                            send(new Message(msg.playerName(), MessageID.PLAYER_ACCEPTED));
                            playerName = msg.playerName();
                            roomServices.playerConnected(msg.playerName(), this);
                            new Thread(() -> {
                                try {
                                    CheckDisconnection();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                        }
                        else send(new Message(msg.playerName(), MessageID.PLAYER_NOT_ACCEPTED));
                    }
                }

            }
        }
    }


    public void CheckDisconnection() throws IOException, InterruptedException {
        Message ping = new Message(playerName, MessageID.PING);

        while(alive){
            Thread.sleep(10*1000);

            send(ping);
            pingQueue.add(ping);
            if(pingQueue.size()>2)
                //vuol dire che si stanno accumulando troppi ping senza risposta,
                // devo notificare il server che questo client si è disconnesso
                alive = false;
        }
    }


}


