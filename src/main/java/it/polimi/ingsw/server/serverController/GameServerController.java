package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.Debugging;
import it.polimi.ingsw.general.MessageID;
import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.general.exceptions.AllPlayerDisconnectedException;
import it.polimi.ingsw.general.exceptions.UnavailableInsertionException;
import it.polimi.ingsw.general.interfaces.ClientController;
import it.polimi.ingsw.general.interfaces.ServerModel;
import it.polimi.ingsw.general.messages.*;
import it.polimi.ingsw.server.model.GameServerModel;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The game model controller is to decide actions to make based on game state and messages received.
 * It accesses the model via the model access class and talks to the virtual view as it was the real view, not knowing
 * there's a connection with other clients.
 *
 * @author Francesco Ostidich
 */
public class GameServerController extends RoomServices {

    private enum Phase {
        INSERT,
        PICK
    }

    private String playerTurn;

    private Phase playerPhase;

    private List<Tile> lastPicked;

    private final List<String> names;

    private final Set<String> disconnected;

    private final ServerModel model;

    private final Map<String, ClientController> matchClients;

    private final ExecutorService executorService;

    /**
     * Class constructor.
     *
     * @param clients is the players' client interfaces map
     * @author Francesco Ostidich
     */
    public GameServerController(@NotNull Map<String, ClientController> clients) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        matchClients = clients;
        playerPhase = Phase.PICK;
        disconnected = new HashSet<>();
        model = new GameServerModel(clients.keySet());
        this.names = model.getTurnCycleOrder();
        playerTurn = names.get(0);
        playMatch();
    }

    /**
     * Starts and plays until the end a match between players provided.
     *
     * @author Francesco Ostidich
     */
    public void playMatch() {
        System.out.println("playing match with names " + matchClients.keySet() + ", and with clients " + matchClients.values());
        Map<String, Tile[][]> shelves = new HashMap<>();
        names.forEach(player -> shelves.put(player ,model.getPlayerShelf(player)));
        names.forEach(player -> executorService.execute(() ->
        {
            try {
                if (Debugging.isDebugging()) Debugging.calculateTime();
                matchClients.get(player).notifyGameHasStarted(new NotifyGameHasStarted(
                        player,
                        MessageID.NOTIFY_GAME_HAS_STARTED,
                        model.getGameParameters(),
                        model.getTurnCycleOrder(),
                        model.getBoard(),
                        model.getBag(),
                        model.getCommonGoal1Tokens(),
                        model.getCommonGoal2Tokens(),
                        model.getPlayerPersonalGoalID(player),
                        model.getCommonGoal1(),
                        model.getCommonGoal2(),
                        shelves));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }));

    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void disconnectedPlayer(String playerName) {
        disconnected.add(playerName);
        if (disconnected.size() == names.size()) closeGame(names);
        if (playerTurn.equals(playerName)) {
            if (playerPhase == Phase.INSERT) {
                Random random = new Random();
                insertTilesRequest(new InsertTilesRequest(playerName, MessageID.INSERT_TILES_REQUEST, lastPicked, random.nextInt(model.getGameParameters().get("shelfColumnNumber"))));
            }
            nextTurn(true);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void reconnectedPlayer(String playerName) {
        disconnected.remove(playerName);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    synchronized public void pickTilesRequest(@NotNull PickTilesRequest message) {
        if (!message.getPlayerName().equals(playerTurn) ||
                message.getMessageID() != MessageID.PICK_TILES_REQUEST) return;
        if (model.checkSelection(message.getChosenCoordinates())) {
            lastPicked = model.selectTilesOnBoard(message.getChosenCoordinates());
            playerPhase = Phase.INSERT;
            executorService.execute(() ->
            {
                try {
                    matchClients.get(message.getPlayerName()).pickAccepted(new PickAccepted(message.getPlayerName(), MessageID.PICK_ACCEPTED, lastPicked));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            nextTurn(false);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    synchronized public void insertTilesRequest(@NotNull InsertTilesRequest message) {
        if (!message.getPlayerName().equals(playerTurn) ||
                message.getMessageID() != MessageID.INSERT_TILES_REQUEST) return;
        try {
            model.playerInsertTilesInShelf(message.getPlayerName(), message.getChosenTiles(), message.getChosenColumn());
            playerPhase = Phase.PICK;
            endOfTurnChecks(message.getPlayerName());
            nextTurn(true);
        } catch (UnavailableInsertionException e) {
            executorService.execute(() ->
            {
                try {
                    matchClients.get(message.getPlayerName()).pickAccepted(new PickAccepted(message.getPlayerName(), MessageID.PICK_ACCEPTED, lastPicked));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
           );
        }
    }

    /**
     * After player has inserted the tiles he selected, many checks are done in order
     * to have points assigned.
     *
     * @param playerName is the player's name string
     * @author Francesco Ostidich
     */
    private void endOfTurnChecks(String playerName) {
        if (!model.getCommonGoal1GivenPlayers().containsValue(playerName) &&
                model.checkCommonGoal1(playerName)) {
            model.assignCommonGoal1Points(playerName);
        }
        if (!model.getCommonGoal2GivenPlayers().containsValue(playerName) &&
                model.checkCommonGoal2(playerName)) {
            model.assignCommonGoal2Points(playerName);
        }
        if (model.checkToRefill()) model.refill();
        if (model.checkPlayerShelfIsFull(playerName) &&
                model.getEndGameToken().isPresent())
            model.assignEndGameTokenPoints(playerName);
    }

    /**
     * Generates messages based on game status (ending, next turn).
     *
     * @param goOn defines if player to play remains the same or the next is to be elected
     * @author Francesco Ostidich
     */
    private void nextTurn(boolean goOn) {
        Map<String, Tile[][]> shelves = new HashMap<>();
        Map<String, Integer> points = new HashMap<>();
        names.forEach(player -> {
            shelves.put(player, Tile.clone(model.getPlayerShelf(player)));
            points.put(player, model.getPlayerPoints(player));
        });
        if (model.getEndGameToken().isEmpty() && playerTurn.equals(names.get(names.size() - 1))) {
            List<Integer> personalGoals = new LinkedList<>();
            Map<String, Integer> adjacentGoalPoints = new HashMap<>();
            Map<String, Integer> personalGoalPoints = new HashMap<>();
            final String[] winner = {names.get(0)};
            names.forEach(player -> {
                personalGoals.add(model.getPlayerPersonalGoalID(player));
                adjacentGoalPoints.put(player, model.assignAdjacentGoalPoints(player));
                personalGoalPoints.put(player, model.assignPersonalGoalPoints(player));
                points.replace(player, model.getPlayerPoints(player));
                if (model.getPlayerPoints(player) > model.getPlayerPoints(winner[0])) {
                    winner[0] = player;
                }
            });
            names.forEach(client -> executorService.execute(() ->
            {
                try {
                    matchClients.get(client).newTurn(new EndGame(
                            client,
                            MessageID.NEW_TURN_END_GAME,
                            model.getBoard(),
                            model.getEndGameToken().isPresent(),
                            model.getBag(),
                            model.getCommonGoal1Tokens(),
                            model.getCommonGoal2Tokens(),
                            model.getCommonGoal1GivenPlayers(),
                            model.getCommonGoal2GivenPlayers(),
                            shelves,
                            points,
                            personalGoals,
                            personalGoalPoints,
                            adjacentGoalPoints,
                            winner[0]));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }));
            return;
        }
        if (goOn) {
            try {
                playerTurnProceed();
            } catch (AllPlayerDisconnectedException ignored) {
                closeGame(names);
            }
        }
        names.forEach(client -> executorService.execute(() ->
        {
            try {
                matchClients.get(client).newTurn(new NextPlayer(
                        client,
                        MessageID.NEW_TURN_NEXT_PLAYER,
                        model.getBoard(),
                        model.getEndGameToken().isPresent(),
                        model.getBag(),
                        model.getCommonGoal1Tokens(),
                        model.getCommonGoal2Tokens(),
                        model.getCommonGoal1GivenPlayers(),
                        model.getCommonGoal2GivenPlayers(),
                        new HashMap<>(shelves),
                        points,
                        model.checkAvailablePickNumber(playerTurn),
                        playerTurn));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    /**
     * Set player turn string to the next player.
     *
     * @author Francesco Ostidich
     */
    private void playerTurnProceed() throws AllPlayerDisconnectedException {
        int i = 0;
        do {
            try {
                playerTurn = names.get(names.indexOf(playerTurn) + 1);
            } catch (IndexOutOfBoundsException e) {
                playerTurn = names.get(0);
            }
            ++i;
        } while (i < names.size() && disconnected.contains(playerTurn));
        if (i >= names.size()) {
            throw new AllPlayerDisconnectedException("all player are disconnected");
        }
    }

    @Override
    public void chatMessage(@NotNull Chat message) {
        if (message.getMessageID() != MessageID.CHAT_MESSAGE) return;
        names.forEach(client -> executorService.execute(() ->
        {
            try {
                matchClients.get(client).chatMessage(new Chat(client, MessageID.CHAT_MESSAGE, message.getMessage()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }));
    }

}
