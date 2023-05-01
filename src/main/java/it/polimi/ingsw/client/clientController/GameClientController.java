package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.clientNetwork.GameClientNetwork;
import it.polimi.ingsw.resources.*;
import it.polimi.ingsw.resources.interfaces.ClientController;
import it.polimi.ingsw.resources.interfaces.ClientNetwork;
import it.polimi.ingsw.resources.interfaces.ClientView;
import it.polimi.ingsw.resources.interfaces.ServerController;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameClientController implements ClientController {

    private ServerController server;

    private final ClientView view;

    private static ClientNetwork clientNetwork;

    private String playerName;

    private int newRoomPlayerNumber;

    private List<Tile> orderChosen;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public GameClientController(@NotNull ClientView view, String network) {
        clientNetwork = new GameClientNetwork(network);
        this.view = view;
        view.start();
    }

    /**
     * Used to pass method request answer to the controller from the view.
     * It contains switch cases based on event type.
     *
     * @author Francesco Ostidich
     * @param evt is the event information
     */
    @SuppressWarnings("unchecked")
    public void update(@NotNull Event evt) {
        switch(evt.eventName()) {
            case START -> view.choosePlayerName();
            case CHOOSE_PLAYER_NAME -> {
                this.playerName = (String) evt.value();
                view.chooseIPAddress();
            }
            case CHOOSE_IP_ADDRESS -> {
                server = clientNetwork.connect((String) evt.value(), playerName, this);
                view.chooseNewOrJoin();
            }
            case CHOOSE_NEW_OR_JOIN -> {
                if(evt.value().equals("new")) view.chooseNewGamePlayerNumber();
                else server.askForRooms(new Message(playerName, MessageID.ASK_FOR_ROOMS));
            }
            case CHOOSE_NEW_GAME_PLAYER_NUMBER -> {
                newRoomPlayerNumber = (int) evt.value();
                view.chooseNewGameName();
            }
            case CHOOSE_NEW_GAME_NAME -> {
                if(evt.value().equals("back")) view.chooseNewOrJoin();
                server.createNewRoom(new Message(playerName, MessageID.CREATE_NEW_ROOM, evt.value(), newRoomPlayerNumber));
            }
            case CHOOSE_GAME_ROOM -> {
                if(evt.value().equals("refresh")) server.askForRooms(new Message(playerName, MessageID.ASK_FOR_ROOMS));
                else if(evt.value().equals("back")) view.chooseNewOrJoin();
                else server.joinRoom(new Message(playerName, MessageID.JOIN_ROOM, evt.value()));
            }
            case PICK_TILES -> server.pickTilesRequest(new Message(playerName, MessageID.PICK_TILES_REQUEST, evt.value()));
            case CHOOSE_ORDER -> {
                orderChosen = (List<Tile>) evt.value();
                view.chooseColumn();
            }
            case CHOOSE_COLUMN -> server.insertTilesRequest(new Message(playerName, MessageID.INSERT_TILE_REQUEST, orderChosen, evt.value()));
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void restart() {
        view.start();
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void roomNameNotAvailable() {
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
    @SuppressWarnings({"unchecked", "StatementWithEmptyBody"})
    public void showRooms(@NotNull Message msg) {
        if(!msg.playerName().equals(playerName) ||
                msg.messageID() != MessageID.SHOW_ROOMS ||
                !(msg.content() instanceof List<?>)) return;
        for(Object gameRoom: (List<?>) msg.content()) {}
        try{
            view.chooseGameRoom((List<GameRoom>) msg.content());
        } catch (ClassCastException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void showPersonalRoom(@NotNull Message msg) {
        if(!msg.playerName().equals(playerName) ||
                msg.messageID() != MessageID.SHOW_PERSONAL_ROOM ||
                !(msg.content() instanceof GameRoom)) return;
        view.updateGameRoom((GameRoom) msg.content());
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode", "StatementWithEmptyBody"})
    public void notifyGameHasStarted(@NotNull Message msg) {
        if(!msg.playerName().equals(playerName) ||
                msg.messageID() != MessageID.NOTIFY_GAME_HAS_STARTED) return;
        Object[] data = msg.contents();
        try {
            for(Object string: ((Map<String, Integer>) data[0]).keySet()) {}
            for(Object integer: ((Map<String, Integer>) data[0]).values()) {}
            view.setGameParameters((Map<String, Integer>) data[0]);
            for(Object string: (List<String>) data[1]) {}
            view.turnCycleOrder((List<String>) data[1]);
            for(Object tile: (Tile[][]) data[2]) {}
            view.updateBoard((Tile[][]) data[2]);
            for(Object tile: ((Map<Tile, Integer>) data[3]).keySet()) {}
            for(Object integer: ((Map<Tile, Integer>) data[3]).values()) {}
            view.updateBag((Map<Tile, Integer>) data[3]);
            for(Object integer: (Stack<Integer>) data[4]) {}
            view.updateCommonGoal1TokenStack((Stack<Integer>) data[4]);
            view.updateCommonGoal2TokenStack((Stack<Integer>) data[4]);
            Map<String, Integer> playerPoints = new HashMap<>();
            for(String player: (List<String>)data[1])
                playerPoints.put(player, 0);
            view.updatePlayerPoints(playerPoints);
            Tile[][] shelf = new Tile[((Map<String, Integer>) data[0]).get("shelfColumnNumber")][((Map<String, Integer>) data[0]).get("shelfRowNumber")];
            Map<String, Tile[][]> playerShelves = new HashMap<>();
            for(String player: (List<String>)data[1])
                playerShelves.put(player, shelf);
            view.updatePlayerShelves(playerShelves);
            List<Integer> personalGoal = new LinkedList<>();
            personalGoal.add((Integer) data[5]);
            view.givePersonalGoals(personalGoal);
            view.giveCommonGoals((String) data[6], (String) data[7]);
            view.notifyGameHasStared();
            if(playerName.equals(((List<String>) data[1]).get(0)))
                view.pickTiles(3);
            else
                view.playerIsPlaying(((List<String>) data[1]).get(0));
        } catch(ClassCastException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode", "StatementWithEmptyBody"})
    public void newTurn(@NotNull Message msg) {
        if(!msg.playerName().equals(playerName) ||
                msg.messageID() != MessageID.NEW_TURN) return;
        Object[] data = msg.contents();
        try {
            view.updateBoard((Tile[][]) data[0]);
            if(!(boolean) data[1]) view.updateEndGameToken(false);
            for(Object tile: ((Map<Tile, Integer>) data[3]).keySet()) {}
            for(Object integer: ((Map<Tile, Integer>) data[3]).values()) {}
            view.updateBag((Map<Tile, Integer>) data[3]);
            for(Object integer: (Stack<Integer>) data[4]) {}
            view.updateCommonGoal1TokenStack((Stack<Integer>) data[4]);
            if(data[5] != null) {
                for(Object integer: ((Map<Integer, String>) data[5]).keySet()) {}
                for(Object string: ((Map<Integer, String>) data[5]).values()) {}
                view.updateCommonGoal1GivenPlayers((Map<Integer, String>) data[5]);
                for(int token: ((Map<Integer, String>) data[5]).keySet())
                    view.assignCommonGoalPoints(((Map<Integer, String>) data[5]).get(token), token);
            }
            for(Object integer: (Stack<Integer>) data[6]) {}
            view.updateCommonGoal2TokenStack((Stack<Integer>) data[6]);
            if(data[7] != null) {
                for(Object integer: ((Map<Integer, String>) data[7]).keySet()) {}
                for(Object string: ((Map<Integer, String>) data[7]).values()) {}
                view.updateCommonGoal2GivenPlayers((Map<Integer, String>) data[7]);
                for(int token: ((Map<Integer, String>) data[7]).keySet())
                    view.assignCommonGoalPoints(((Map<Integer, String>) data[7]).get(token), token);
            }
            view.updatePlayerShelves((Map<String, Tile[][]>) data[8]);
            if(data[9] != null) view.updatePlayerPoints((Map<String, Integer>) data[9]);
            if(data.length == 15) {
                Object[] endGameStuff = {data[10], data[11], data[12], data[13], data[14]};
                endGame(endGameStuff);
            }
            if(playerName.equals(data[10]))
                view.pickTiles((int) data[11]);
            else
                view.playerIsPlaying((String) data[10]);
        } catch (ClassCastException ignored) {}
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    @SuppressWarnings("unchecked")
    public void pickAccepted(@NotNull Message msg) {
        if(!msg.playerName().equals(playerName) ||
                msg.messageID() != MessageID.PICK_ACCEPTED ||
                !(msg.content() instanceof List<?>)) return;
        for(Object tile: (List<?>) msg.content())
            if(!(tile instanceof Tile)) return;
        view.chooseOrder((List<Tile>) msg.content());
    }

    /**
     * If it is the last turn, end game actions are to be made.
     *
     * @author Francesco Ostidich
     * @param data is the data from the last server message
     */
    @SuppressWarnings({"unchecked", "StatementWithEmptyBody"})
    private void endGame(Object @NotNull [] data) {
        try {
            if(data[0] != null) {
                for(Integer points: (List<Integer>) data[0]) {}
                view.givePersonalGoals((List<Integer>) data[0]);
            }
        } catch(ClassCastException ignored) {}
        try {
            if(data[1] != null) {
                for(String player: ((Map<String, Integer>) data[1]).keySet()) {}
                for(Integer points: ((Map<String, Integer>) data[1]).values()) {}
                view.assignPersonalGoalPoints((Map<String, Integer>) data[1]);
            }
        } catch(ClassCastException ignored) {}
        try {
            if(data[2] != null) {
                for(String player: ((Map<String, Integer>) data[2]).keySet()) {}
                for(Integer points: ((Map<String, Integer>) data[2]).values()) {}
                view.assignAdjacentGoalPoints((Map<String, Integer>) data[2]);
            }
        } catch(ClassCastException ignored) {}
        try {
            if(data[3] != null && data[4] instanceof String) {
                for(String player: ((Map<String, Integer>) data[3]).keySet()) {}
                for(Integer points: ((Map<String, Integer>) data[3]).values()) {}
                view.announceWinner((String) data[4], (Map<String, Integer>) data[3]);
            }
        } catch(ClassCastException ignored) {}
    }

}
