package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.handler.ChooseGameRoomSceneHandler;
import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.client.view.handler.WaitPlayersSceneHandler;
import it.polimi.ingsw.client.view.handler.match.MatchSceneHandler;
import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Tile;
import javafx.application.Platform;
import java.util.List;
import java.util.Map;
public class GUI extends GameClientView {

    public GUI() {
        super();
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
        SceneHandler.switchScene("player_name");
    }

    @Override
    public void chooseNewOrJoin() {

        SceneHandler.switchScene("new_or_join");

    }

    @Override
    public void chooseNewGameName() {

        System.out.println("choosenewgame");


    }

    @Override
    public void chooseNewGamePlayerNumber() {

        SceneHandler.switchScene("game_ip_connection");

    }

    @Override
    public void showUpdatedGameRoom(String roomName) {
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


    }

    @Override
    public void notifyGameHasStared() {

        SceneHandler.switchScene("match");
        MatchSceneHandler.setNumOfPlayers(getNames().size());
        MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
        Platform.runLater(msh::initOpponentPlayers);

    }

    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {
        ChooseGameRoomSceneHandler.setGameRooms(rooms);
        SceneHandler.switchScene("choose_game_room");
    }

    @Override
    public void pickTiles(int availablePickNumber) {

        MatchSceneHandler.setPickPhase(true);
        MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
        MatchSceneHandler.setAvailableTiles(availablePickNumber);

        Platform.runLater(() ->{
            msh.checkSelection();
            msh.updateBoard();

            msh.updateOpponentBoards();
        });


    }

    @Override
    public void chooseOrder(List<Tile> selection) {

        MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();

        Platform.runLater(msh::chooseOrder);


    }

    @Override
    public void chooseColumn() {

        MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
        Platform.runLater(() ->{
            msh.changeAdviseMsg("Choose column");
            msh.enableColumnsCursor();
            msh.checkColumnSelection(true);
        });


    }

    @Override
    public void playerIsPlaying(String playerName) {

        MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
        MatchSceneHandler.setPickPhase(false);

        Platform.runLater(() -> {
            msh.checkColumnSelection(false);
            msh.changeAdviseMsg(playerName + " is currently playing his turn");
            msh.updateBoard();
            msh.updateOpponentBoards();
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

    @Override
    public void justScanChat() {

    }

    @Override
    public void justPrintChat() {

    }
}
