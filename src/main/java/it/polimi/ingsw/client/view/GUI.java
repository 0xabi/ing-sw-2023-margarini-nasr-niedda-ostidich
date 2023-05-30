package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.client.view.handler.WaitPlayersSceneHandler;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;

import java.util.List;
import java.util.Map;
import java.util.Queue;

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
        //SceneHandler.switchScene("wait_players");
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

    }

    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {
        System.out.println("Trigger2");
    }

    @Override
    public void pickTiles(int availablePickNumber) {

    }

    @Override
    public void chooseOrder(List<Tile> selection) {

    }

    @Override
    public void chooseColumn() {

    }

    @Override
    public void playerIsPlaying(String playerName) {

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
    public void justPrintChat(Queue<String> messages) {

    }
}
