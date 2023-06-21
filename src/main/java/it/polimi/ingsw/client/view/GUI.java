package it.polimi.ingsw.client.view;

import it.polimi.ingsw.Debugging;
import it.polimi.ingsw.client.view.handler.ChooseGameRoomSceneHandler;
import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.client.view.handler.WaitPlayersSceneHandler;
import it.polimi.ingsw.client.view.handler.match.MatchSceneHandler;
import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Tile;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
public class GUI extends GameClientView {

    private String chatMessage;

    public GUI() {
        super();
        chatMessage = null;
    }

    @Override
    public void start() {
        SceneHandler.setClientController(getClientController());

    }

    @Override
    public void chooseIPAddress() {


    }

    @Override
    public void choosePlayerName() {
        //SceneHandler.switchScene("match");

        Platform.runLater(() ->
        {
            SceneHandler.switchScene("player_name");
        });

    }

    @Override
    public void chooseNewOrJoin() {


        Platform.runLater(() -> {
            SceneHandler.switchScene("new_or_join");
        });


    }

    @Override
    public void chooseNewGameName() {

        Platform.runLater(() ->
        {
            System.out.println("choosenewgame");
        });



    }

    @Override
    public void chooseNewGamePlayerNumber() {

        Platform.runLater(() ->
        {
            SceneHandler.switchScene("game_ip_connection");

        });

    }

    @Override
    public void showUpdatedGameRoom(String roomName) {


        Platform.runLater(() ->
        {
            GameRoom room = null;
            for (GameRoom gameRoom : getGameRooms()) {
                if (gameRoom.gameRoomName().equals(roomName)) {
                    room = gameRoom;
                    break;
                }
            }
            if (room == null) throw new RuntimeException("no such room name present while printing personal room!");
            WaitPlayersSceneHandler.setRoom(room);

            SceneHandler.switchScene("wait_players");
        });



    }

    @Override
    public void notifyGameHasStared() {



        Platform.runLater(() ->{

            SceneHandler.switchScene("match");
            MatchSceneHandler.setNumOfPlayers(getNames().size());
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();

            msh.initPersonalGoal(getPlayerPersonalGoals().get(0));
            msh.initOpponentPlayers();
            msh.setImageCommonGoals(getCommonGoal1(),getCommonGoal2());
            msh.initChair();
            msh.initScoringTokens();

            Thread chatThread = new Thread(this::justScanChat);
            chatThread.start();
        });
    }

    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {

        Platform.runLater(() ->
        {
            ChooseGameRoomSceneHandler.setGameRooms(rooms);
            SceneHandler.switchScene("choose_game_room");
        });

    }

    @Override
    public void pickTiles(int availablePickNumber) {

        Platform.runLater(() ->{

            MatchSceneHandler.setPickPhase(true);
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            MatchSceneHandler.setAvailableTiles(availablePickNumber);

            msh.checkSelection();
            msh.updateBoard();
            msh.updateOpponentBoards();
            msh.updateCommonGoals(getCommonGoal1GivenPlayers(),getCommonGoal2GivenPlayers());
        });


    }

    @Override
    public void chooseOrder(List<Tile> selection) {

        Platform.runLater(() ->{
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.chooseOrder();
        });


    }

    @Override
    public void chooseColumn() {


        Platform.runLater(() ->{
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.changeAdviseMsg("Choose column");
            msh.enableColumnsCursor();
            msh.checkColumnSelection(true);
        });


    }

    @Override
    public void playerIsPlaying(String playerName) {


        Platform.runLater(() -> {

            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            MatchSceneHandler.setPickPhase(false);
            Debugging.Watch.temp.stop();

            msh.checkColumnSelection(false);
            msh.changeAdviseMsg(playerName + " is currently playing his turn");
            msh.updateBoard();
            msh.updateOpponentBoards();
            msh.updateCommonGoals(getCommonGoal1GivenPlayers(),getCommonGoal2GivenPlayers());
        });


    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {

    }

    @Override
    public void chooseRMIorSocket() {
        SceneHandler.switchScene("connection");
    }


    @Override
    public void disconnected() {

    }


    public void updateChatMessage(String message){chatMessage = message;}

    @Override
    public void justScanChat() {}

    @Override
    public void justPrintChat() {
        Platform.runLater(() -> {
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.updateChat(getChatMessages());
        });
    }
}