package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.Message;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ServerController;
import it.polimi.ingsw.general.messages.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * The Client class represents a client connected to the server.
 * It handles communication with the client, message sending, and message processing.
 *
 * @author Edoardo Margarini
 */
public class Client implements ClientController {

    private boolean alive;

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    private String connectionType = null;

    private final ObjectOutputStream MessageToClient;

    private final ObjectInputStream MessageFromClient;

    private final Queue<Message> messageQueue = new LinkedList<>();

    private final Queue<Message> pingQueue = new LinkedList<>();

    private final ServerController roomServices;

    private String playerName;

    /**
     * Constructs a new Client object.
     *
     * @param clientSocket the client socket for communication
     * @param roomServices the server controller for handling room services
     * @throws IOException if an I/O error occurs during the construction
     * @author Edoardo Margarini
     */
    public Client(@NotNull Socket clientSocket, ServerController roomServices) throws IOException {
        MessageFromClient = new ObjectInputStream(clientSocket.getInputStream());
        MessageToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        this.roomServices = roomServices;
        alive = true;
        setConnectionType("Socket");
        new Thread(() -> {
            try {
                Sorter();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(this::ClientSocketListener).start();
    }

    /**
     * Sends a message to the client.
     * The method synchronizes the send operation to ensure thread safety.
     * If the connection type is "Socket", it writes the message object to the client output stream,
     * flushes the stream, and resets it for future use.
     *
     * @param message the message to be sent to the client
     * @throws IOException if an I/O error occurs during the send operation
     * @author Francesco Ostidich
     */

    public synchronized void send(Message message) throws IOException{{
            if (Objects.equals(connectionType, "Socket")) {
                try {
                    MessageToClient.writeObject(message);
                    MessageToClient.flush();
                    MessageToClient.reset();
                } catch (IOException e) {
                    try {
                        roomServices.disconnectedPlayer(playerName);

                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    /**
     * Listens for incoming messages from the client socket and adds them to the message queue.
     * The method continuously reads objects from the client socket and checks their message ID.
     * If the received message is a PONG message, it removes a corresponding message from the ping queue.
     * For other message types, they are added to the message queue for further processing.
     * If any I/O or class not found exception occurs during message receiving, the method breaks the loop and exits.
     *
     * @author Edoardo Margarini
     */

    public void ClientSocketListener() {
        Message clientMessage;
        while (alive) {
            try {
                clientMessage = (Message) MessageFromClient.readObject();
            } catch (IOException | ClassNotFoundException e) {
                break;
            }
            if (clientMessage.getMessageID() == MessageID.PONG)
                pingQueue.remove();
            else {
                messageQueue.add(clientMessage);
            }
        }
    }

    /**
     * Sorts and handles incoming messages from the message queue.
     * The method continuously checks the message queue and processes the messages if available.
     * Each message is handled based on its message ID by invoking the corresponding method in the 'roomServices' object.
     * If an unexpected message type is encountered, it is ignored.
     * If any exception occurs during message handling, a RuntimeException is thrown.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     *
     * @author Edoardo Margarini
     */

    public void Sorter() throws InterruptedException {
        while (alive) {
            //noinspection BusyWait
            Thread.sleep(100);
            if (messageQueue.size() > 0) {
                Message msg = messageQueue.remove();
                try {
                    switch (msg.getMessageID()) {
                        case JOIN_ROOM -> roomServices.joinRoom((JoinRoom) msg);
                        case INSERT_TILES_REQUEST -> roomServices.insertTilesRequest((InsertTilesRequest) msg);
                        case ASK_FOR_ROOMS -> roomServices.askForRooms((AskForRooms) msg);
                        case CREATE_NEW_ROOM -> roomServices.createNewRoom((CreateNewRoom) msg);
                        case PICK_TILES_REQUEST -> roomServices.pickTilesRequest((PickTilesRequest) msg);
                        case CHAT_MESSAGE -> roomServices.chatMessage((Chat) msg);
                        case HELLO -> {
                            if (roomServices.PlayerIDisAvailable((Hello) msg)) {
                                send(new PlayerAccepted(msg.getPlayerName(), MessageID.PLAYER_ACCEPTED));
                                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "] " + msg.getPlayerName() + " joined the lobby");
                                playerName = msg.getPlayerName();
                                roomServices.playerConnected(playerName, this);
                                new Thread(() -> {
                                    try {
                                        CheckDisconnection();
                                    } catch (Exception ignored) {
                                    }
                                }).start();
                            } else send(new PlayerNotAccepted(msg.getPlayerName(), MessageID.PLAYER_NOT_ACCEPTED));
                        }
                    }
                } catch (ClassCastException ignored) {
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Checks the disconnection status of the player by sending periodic ping messages.
     * If the player fails to respond to multiple consecutive pings, it is considered disconnected.
     * The method sends a ping message to the player every 10 seconds and checks the size of the ping queue.
     * If the size exceeds 2 (indicating that the player has not responded to two consecutive pings),
     * the player is considered disconnected and the 'alive' flag is set to false.
     *
     * @throws IOException            if an I/O error occurs while sending the ping message
     * @throws InterruptedException if the thread sleep is interrupted
     *
     * @author Edoardo Margarini
     */

    public void CheckDisconnection() throws IOException, InterruptedException {
        Message ping = new Ping(playerName, MessageID.PING);
        while (alive) {
            //noinspection BusyWait
            Thread.sleep(10 * 1000);
            pingQueue.add(ping);
            try {
                send(ping);
            }catch(IOException e){
                return;
            }

            if (pingQueue.size() > 2) {
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "] " + playerName + " quit the lobby");
                roomServices.disconnectedPlayer(playerName);
                return;
            }
        }
    }


    @Override
    public void update(Event event) {
        throw new RuntimeException("server cannot call update");
    }

    @Override
    public void restart() {
        throw new RuntimeException("server cannot call restart");
    }

    @Override
    public void disconnectedFromServer() {
        throw new RuntimeException("server cannot call disconnectedFromServer");
    }

    @Override
    public void serverConnected() {
        throw new RuntimeException("server cannot call serverConnected");
    }

    @Override
    public void roomNameNotAvailable(RoomNameNotAvailable message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void showRooms(ShowRooms message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void showPersonalRoom(ShowPersonalRoom message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void notifyGameHasStarted(NotifyGameHasStarted message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void newTurn(NextPlayer message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void newTurn(EndGame message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void pickAccepted(PickAccepted message) {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void endGame(@NotNull EndGame msg) {
        throw new RuntimeException("server cannot call endgame");
    }

    @Override
    public void chatMessage(Chat message) throws RemoteException {
        try {
            send(message);
        } catch (IOException ignored) {
        }
    }

}


