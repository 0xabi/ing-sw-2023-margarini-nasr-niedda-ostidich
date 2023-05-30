package it.polimi.ingsw.server.serverNetwork;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.MessageID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.messages.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Client implements ClientController {

    private boolean alive;

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    private String connectionType = null;

    private final Socket clientSocket;

    private final ObjectOutputStream MessageToClient;

    private final ObjectInputStream MessageFromClient;

    private final Queue<Message> messageQueue = new LinkedList<>();

    private final Queue<Message> pingQueue = new LinkedList<>();

    private final ServerController roomServices;

    private String playerName;

    public Client(@NotNull Socket clientSocket, ServerController roomServices) throws IOException {
        this.clientSocket = clientSocket;
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

    public void send(Message message) throws IOException {
        synchronized (MessageToClient) {
            if (Objects.equals(connectionType, "Socket")) {
                MessageToClient.writeObject(message);
                MessageToClient.reset();
                MessageToClient.flush();
            }
        }
    }

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

    public void Sorter() throws InterruptedException {
        while (alive) {
            //noinspection BusyWait
            Thread.sleep(1000);
            if (messageQueue.size() > 0) {
                Message msg = messageQueue.remove();
                try {
                    switch (msg.getMessageID()) {
                        case JOIN_ROOM -> roomServices.joinRoom((JoinRoom) msg);
                        case INSERT_TILES_REQUEST -> roomServices.insertTilesRequest((InsertTilesRequest) msg);
                        case ASK_FOR_ROOMS -> roomServices.askForRooms((AskForRooms) msg);
                        case CREATE_NEW_ROOM -> roomServices.createNewRoom((CreateNewRoom) msg);
                        case PICK_TILES_REQUEST -> roomServices.pickTilesRequest((PickTilesRequest) msg);
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

    public void CheckDisconnection() throws IOException, InterruptedException {
        Message ping = new Ping(playerName, MessageID.PING);
        while (alive) {
            //noinspection BusyWait
            Thread.sleep(10 * 1000);
            pingQueue.add(ping);
            send(ping);
            if (pingQueue.size() > 2) {
                System.out.println("[" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "] " + playerName + " quit the lobby");
                alive = false;
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

}


