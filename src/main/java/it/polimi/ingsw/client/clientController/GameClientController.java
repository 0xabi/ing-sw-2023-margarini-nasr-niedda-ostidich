package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.Debugging;
import it.polimi.ingsw.client.clientNetwork.GameClientNetwork;
import it.polimi.ingsw.resources.*;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ClientNetwork;
import it.polimi.ingsw.resources.interfaces.ClientView;
import it.polimi.ingsw.resources.interfaces.ServerController;
import it.polimi.ingsw.resources.messages.*;
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


    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientController(@NotNull ClientView view, String network) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        clientNetwork = new GameClientNetwork(network);
        Debugging.setRoomGeneration(network);
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
                    view.chooseIPAddress();
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
                case CHOOSE_IP_ADDRESS -> {
                    this.server = clientNetwork.connect((String) evt.value(), playerName, this);
                    try {
                        Thread.sleep(100 * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (server == null)
                        System.out.println("room service è null");
                }
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
        //Tile[][] shelf = new Tile[msg.getGameParameters().get("shelfColumnNumber")][msg.getGameParameters().get("shelfRowNumber") ];
        Tile[][] shelf = new Tile[msg.getGameParameters().get("shelfRowLength")][msg.getGameParameters().get("shelfColumnLength")];
        Map<String, Tile[][]> playerShelves = new HashMap<>();
        for (String player : msg.getTurnCycle())
            playerShelves.put(player, shelf);
        view.updatePlayerShelves(playerShelves);
        view.givePersonalGoals(Collections.singletonList(msg.getPersonalGoal()));
        view.giveCommonGoals(msg.getCommonGoal1(), msg.getCommonGoal2());
        view.notifyGameHasStared();
        if (playerName.equals(msg.getTurnCycle().get(0)))
            view.pickTiles(3);
        else
            view.playerIsPlaying(msg.getTurnCycle().get(0));
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void newTurn(@NotNull NewTurn msg) {
        if (!msg.getPlayerName().equals(playerName) ||
                msg.getMessageID() != MessageID.NEW_TURN) return;
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
        if (msg instanceof EndGame) {
            endGame((EndGame) msg);
        }
        if (msg instanceof NextPlayer) {
            if (playerName.equals(((NextPlayer) msg).getNextPlayer()))
                view.pickTiles(((NextPlayer) msg).getAvailablePickNumber());
            else
                view.playerIsPlaying(((NextPlayer) msg).getNextPlayer());
        }
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
