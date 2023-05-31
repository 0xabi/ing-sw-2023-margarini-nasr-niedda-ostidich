package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.clientNetwork.GameClientNetwork;
import it.polimi.ingsw.general.*;
import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ClientNetwork;
import it.polimi.ingsw.general.interfaces.ClientView;
import it.polimi.ingsw.general.interfaces.ServerController;
import it.polimi.ingsw.general.messages.*;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameClientController extends UnicastRemoteObject implements ClientController {

    private ServerController server;

    private final ClientView view;

    private static ClientNetwork clientNetwork;

    private String playerName;

    private int newRoomPlayerNumber;

    private String newRoomName;

    private List<Tile> orderChosen;

    private final ExecutorService executorService;

    private String network;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientController(@NotNull ClientView view) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        this.view = view;
    }

    /**
     * Used to pass method request answer to the controller from the view.
     * It contains switch cases based on event type.
     *
     * @param evt is the event information
     * @author Francesco Ostidich
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(@NotNull Event evt) {
        switch (evt.eventName()) {
            case START -> view.choosePlayerName();
            case CHOOSE_PLAYER_NAME -> {
                playerName = (String) evt.value();
                view.chooseRMIorSocket();
            }
            case CHOOSE_RMI_OR_SOCKET -> {
                network = (String) evt.value();
                clientNetwork = new GameClientNetwork(network);
                view.chooseIPAddress();
            }
            case CHOOSE_CONNECTION_AND_IP -> {
                String network = ((String) evt.value()).split(",")[0];
                String ip = ((String) evt.value()).split(",")[1];
                clientNetwork = new GameClientNetwork(network);
                this.server = clientNetwork.connect(ip, playerName, this);
            }
            case CHOOSE_NEW_OR_JOIN -> {
                if (evt.value().equals("new")) view.chooseNewGamePlayerNumber();
                else executorService.execute(() ->
                {
                    try {
                        server.askForRooms(new AskForRooms(playerName, MessageID.ASK_FOR_ROOMS));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            case CHOOSE_IP_ADDRESS -> this.server = clientNetwork.connect((String) evt.value(), playerName, this);
            case CHOOSE_NEW_GAME_PLAYER_NUMBER -> {
                newRoomPlayerNumber = (int) evt.value();
                view.chooseNewGameName();
            }
            case CHOOSE_NEW_GAME_NAME -> {
                if (evt.value().equals("back")) view.chooseNewOrJoin();
                else {
                    newRoomName = (String) evt.value();
                    executorService.execute(() ->
                    {
                        try {
                            server.createNewRoom(new CreateNewRoom(playerName, MessageID.CREATE_NEW_ROOM, newRoomName, newRoomPlayerNumber));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            case CREATE_ROOM_NAME_NUMBER -> {
                if (evt.value().equals("back")) view.chooseNewOrJoin();
                else {
                    Object[] data = (Object[]) evt.value();
                    newRoomName = (String) data[0];
                    newRoomPlayerNumber = (int) data[1];
                    executorService.execute(() ->
                    {
                        try {
                            server.createNewRoom(new CreateNewRoom(playerName, MessageID.CREATE_NEW_ROOM, newRoomName, newRoomPlayerNumber));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            case CHOOSE_GAME_ROOM -> {
                if (evt.value().equals("refresh"))
                    executorService.execute(() ->
                    {
                        try {
                            server.askForRooms(new AskForRooms(playerName, MessageID.ASK_FOR_ROOMS));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                else if (evt.value().equals("back")) view.chooseNewOrJoin();
                else {
                    executorService.execute(() ->
                    {
                        try {
                            server.joinRoom(new JoinRoom(playerName, MessageID.JOIN_ROOM, (String) evt.value()));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            case PICK_TILES -> executorService.execute(() ->
            {
                try {
                    server.pickTilesRequest(new PickTilesRequest(playerName, MessageID.PICK_TILES_REQUEST, (List<Coordinates>) evt.value()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
            case CHOOSE_ORDER -> {
                orderChosen = (List<Tile>) evt.value();
                view.chooseColumn();
            }
            case CHOOSE_COLUMN -> executorService.execute(() ->
            {
                try {
                    server.insertTilesRequest(new InsertTilesRequest(playerName, MessageID.INSERT_TILES_REQUEST, orderChosen, (int) evt.value()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void restart() {
        view.start();
    }

    @Override
    public void serverConnected() {
        view.chooseNewOrJoin();
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void roomNameNotAvailable(@NotNull RoomNameNotAvailable msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.ROOM_NAME_NOT_AVAILABLE) return;
        view.chooseNewGameName();
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void disconnectedFromServer() {
        view.disconnected();
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void showRooms(@NotNull ShowRooms msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.SHOW_ROOMS) return;
        view.chooseGameRoom(msg.getGameRooms());
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void showPersonalRoom(@NotNull ShowPersonalRoom msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.SHOW_PERSONAL_ROOM) return;
        view.updateGameRoom(msg.getGameRoom());
        view.showUpdatedGameRoom(newRoomName);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void notifyGameHasStarted(@NotNull NotifyGameHasStarted msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.NOTIFY_GAME_HAS_STARTED) return;
        view.setGameParameters(msg.getGameParameters());
        view.turnCycleOrder(msg.getTurnCycle());
        view.updateBoard(msg.getBoard());
        view.updateBag(msg.getBag());
        view.updateCommonGoal1TokenStack(msg.getCommonGoal1TokenStack());
        view.updateCommonGoal2TokenStack(msg.getCommonGoal2TokenStack());
        Map<String, Integer> playerPoints = new HashMap<>();
        for (String player : msg.getTurnCycle())
            playerPoints.put(player, 0);
        view.updatePlayerPoints(playerPoints);
        view.updatePlayerShelves(msg.getShelves());
        view.givePersonalGoals(Collections.singletonList(msg.getPersonalGoal()));
        view.giveCommonGoals(msg.getCommonGoal1(), msg.getCommonGoal2());
        view.notifyGameHasStared();
        if (playerName.equals(msg.getTurnCycle().get(0)))
            view.pickTiles(3);
        else
            view.playerIsPlaying(msg.getTurnCycle().get(0));
    }

    /**
     * When new turn message is received, regardless of the subtype, initial updates are made.
     *
     * @param msg is the message with needed info
     * @author Francesco Ostidich
     */
    public void newTurnInitializing(@NotNull NewTurn msg) {
        view.updateBoard(msg.getBoard());
        if (!msg.getEndGameToken()) view.updateEndGameToken(false);
        view.updateBag(msg.getBag());
        view.updateCommonGoal1TokenStack(msg.getCommonGoal1TokenStack());
        view.updateCommonGoal1GivenPlayers(msg.getCommonGoal1GivenPlayers());
        for (int token : msg.getCommonGoal1GivenPlayers().keySet())
            view.assignCommonGoalPoints(msg.getCommonGoal1GivenPlayers().get(token), token);
        view.updateCommonGoal2TokenStack(msg.getCommonGoal2TokenStack());
        view.updateCommonGoal2GivenPlayers(msg.getCommonGoal2GivenPlayers());
        for (int token : msg.getCommonGoal2GivenPlayers().keySet())
            view.assignCommonGoalPoints(msg.getCommonGoal2GivenPlayers().get(token), token);
        view.updatePlayerShelves(msg.getPlayerShelves());
        view.updatePlayerPoints(msg.getPlayerPoints());
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void newTurn(@NotNull EndGame msg) {
        if (msg.getMessageID() != MessageID.NEW_TURN_END_GAME) return;
        newTurnInitializing(msg);
        endGame(msg);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void newTurn(@NotNull NextPlayer msg) {
        if (msg.getMessageID() != MessageID.NEW_TURN_NEXT_PLAYER) return;
        newTurnInitializing(msg);
        if (playerName.equals(msg.getNextPlayer()))
            view.pickTiles(msg.getAvailablePickNumber());
        else
            view.playerIsPlaying(msg.getNextPlayer());
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void pickAccepted(@NotNull PickAccepted msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.PICK_ACCEPTED) return;
        view.chooseOrder(msg.getPickedTiles());
    }

    /**
     * If it is the last turn, end game actions are to be made.
     *
     * @param msg is the data from the last server message
     * @author Francesco Ostidich
     */
    @Override
    public void endGame(@NotNull EndGame msg) {
        view.givePersonalGoals(msg.getPersonalGoals());
        view.assignPersonalGoalPoints(msg.getPersonalGoalPoints());
        view.assignAdjacentGoalPoints(msg.getAdjacentGoalPoints());
        view.announceWinner(msg.getWinner(), msg.getPlayerPoints());
    }

}
