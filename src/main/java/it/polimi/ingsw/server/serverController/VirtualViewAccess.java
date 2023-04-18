package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.interfaces.MessagePackaging;
import it.polimi.ingsw.resources.interfaces.ViewActions;
import it.polimi.ingsw.resources.messages.TileListMessage;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.virtualView.GameVirtualView;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Methods are called from the controller to wrap information and to send it on the connection bridge, to be processed
 * by the clients. When messages are received them forwards them to the controller.
 *
 * @author Francesco Ostidich
 */
public class VirtualViewAccess implements ViewActions, MessagePackaging {

    private final GameVirtualView gameVirtualView;

    private final GameModelController gameModelController;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param gameModelController processes events and data
     */
    public VirtualViewAccess(GameModelController gameModelController) {
        this.gameVirtualView = new GameVirtualView(this);
        this.gameModelController = gameModelController;
    }

    @Override
    public TileListMessage tileToInsertOrdered(List<Tile> tiles) {
        return null;
    }

    @Override
    public void setGameParameters(Map<String, Integer> gameParameters) {

    }

    @Override
    public void turnCycleOrder(List<String> names) {

    }

    @Override
    public void updateBoard(Tile[][] board) {

    }

    @Override
    public void updateEndGameToken(boolean present) {

    }

    @Override
    public void updateBag(Map<Tile, Integer> tilesLeft) {

    }

    @Override
    public void updateCommonGoal1TokenStack(Stack<Integer> tokenStack) {

    }

    @Override
    public void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer) {

    }

    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {

    }

    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {

    }


    @Override
    public void updatePlayerShelves(List<Tile[][]> shelves) {

    }

    @Override
    public void updatePlayerPoints(List<Integer> points) {

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
    public void givePersonalGoals(List<Integer> personalGoal) {

    }

    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {

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
    public String waitForEndGameAction() {
        return null;
    }

    @Override
    public String justScanChat() {
        return null;
    }

    @Override
    public void justPrintChat(String message) {

    }
}
