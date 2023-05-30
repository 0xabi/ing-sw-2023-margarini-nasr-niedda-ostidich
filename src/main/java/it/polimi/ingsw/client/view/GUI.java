package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.handler.SceneHandler;
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
        SceneHandler.switchScene("player_name");
    }

    @Override
    public void chooseNewOrJoin() {
    }

    @Override
    public void chooseNewGameName() {
    }

    @Override
    public void chooseNewGamePlayerNumber() {
    }

    @Override
    public void showUpdatedGameRoom(String roomName) {
    }

    @Override
    public void notifyGameHasStared() {
    }

    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {
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
