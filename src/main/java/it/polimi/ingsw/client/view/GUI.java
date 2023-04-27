package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;

import java.util.List;
import java.util.Map;

public class GUI extends GameView {

    public GUI() {
        super();
    }

    @Override
    public void start() {

    }

    @Override
    public String chooseIPAddress() {
        return null;
    }

    @Override
    public String choosePlayerName() {
        return null;
    }

    @Override
    public String chooseNewOrJoin() {
        return null;
    }

    @Override
    public String chooseNewGameName() {
        return null;
    }

    @Override
    public int chooseNewGamePlayerNumber() {
        return 0;
    }

    @Override
    public void updateGameRoom(GameRoom gameRoom) {

    }

    @Override
    public void notifyGameHasStared() {

    }

    @Override
    public String chooseGameRoom(List<GameRoom> rooms) {
        return null;
    }

    @Override
    public List<Coordinates> pickTiles(int availablePickNumber) {
        return null;
    }

    @Override
    public List<Tile> chooseOrder(List<Tile> selection) {
        return null;
    }

    @Override
    public int chooseColumn() {
        return 0;
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
    public String justScanChat() {
        return null;
    }

    @Override
    public void justPrintChat(String message) {

    }

}
