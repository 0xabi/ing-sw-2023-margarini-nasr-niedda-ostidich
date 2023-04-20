package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ViewActions;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Method are called by the controller to notify or retrieve information from the view. Information from view is directly
 * type of return in controller method, passing by here.
 *
 * @author Francesco Ostidich
 */
public class ViewAccess implements ViewActions {

    private final GameView gameView;

    /**
     * Class constructor.
     *
     * @param gameView is the view core of the client
     * @author Francesco Ostidich
     */
    protected ViewAccess(GameView gameView){
        this.gameView = gameView;
    }

    @Override
    public void endGame() {
        gameView.endGame();
    }

    @Override
    public void setGameParameters(Map<String, Integer> gameParameters) {
        gameView.setGameParameters(gameParameters);
    }

    @Override
    public void turnCycleOrder(List<String> names) {
        gameView.turnCycleOrder(names);
    }

    @Override
    public void updateBoard(Tile[][] board) {
        gameView.updateBoard(board);
    }

    @Override
    public void updateEndGameToken(boolean present) {
        gameView.updateEndGameToken(present);
    }

    @Override
    public void updateBag(Map<Tile, Integer> tilesLeft) {
        gameView.updateBag(tilesLeft);
    }

    @Override
    public void updateCommonGoal1TokenStack(Stack<Integer> tokenStack) {
        gameView.updateCommonGoal1TokenStack(tokenStack);
    }

    @Override
    public void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer) {
        gameView.updateCommonGoal1GivenPlayers(givenPlayer);
    }

    @Override
    public void updateCommonGoal2TokenStack(Stack<Integer> tokenStack) {
        gameView.updateCommonGoal2TokenStack(tokenStack);
    }

    @Override
    public void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer) {
        gameView.updateCommonGoal2GivenPlayers(givenPlayer);
    }

    @Override
    public void updatePlayerShelves(List<Tile[][]> shelves) {
        gameView.updatePlayerShelves(shelves);
    }

    @Override
    public void updatePlayerPoints(List<Integer> points) {
        gameView.updatePlayerPoints(points);
    }

    @Override
    public void start() {
        gameView.start();
    }

    @Override
    public String chooseIPAddress() {
        return gameView.chooseIPAddress();
    }

    @Override
    public String choosePlayerName() {
        return gameView.choosePlayerName();
    }

    @Override
    public String chooseNewOrJoin() {
        return gameView.chooseNewOrJoin();
    }

    @Override
    public String chooseNewGameName() {
        return gameView.chooseNewGameName();
    }

    @Override
    public int chooseNewGamePlayerNumber() {
        return gameView.chooseNewGamePlayerNumber();
    }

    @Override
    public void updateGameRoom(GameRoom gameRoom) {
        gameView.updateGameRoom(gameRoom);
    }

    @Override
    public void updateGameRooms(List<GameRoom> gameRooms) {
        gameView.updateGameRooms(gameRooms);
    }

    @Override
    public void notifyGameHasStared() {
        gameView.notifyGameHasStared();
    }

    @Override
    public String chooseGameRoom(List<GameRoom> rooms) {
        return gameView.chooseGameRoom(rooms);
    }

    @Override
    public void givePersonalGoals(List<Integer> personalGoal) {
        gameView.givePersonalGoals(personalGoal);
    }

    @Override
    public void giveCommonGoals(String commonGoal1, String commonGoal2) {
        gameView.giveCommonGoals(commonGoal1, commonGoal2);
    }

    @Override
    public List<Coordinates> pickTiles(int availablePickNumber) {
        return gameView.pickTiles(availablePickNumber);
    }

    @Override
    public List<Tile> chooseOrder(List<Tile> selection) {
        return gameView.chooseOrder(selection);
    }

    @Override
    public int chooseColumn() {
        return gameView.chooseColumn();
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {
        gameView.assignCommonGoalPoints(playerName, token);
    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {
        gameView.assignPersonalGoalPoints(points);
    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {
        gameView.assignAdjacentGoalPoints(points);
    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {
        gameView.announceWinner(winnerName, points);
    }

    @Override
    public String waitForEndGameAction() {
        return gameView.waitForEndGameAction();
    }

    @Override
    public String justScanChat() {
        return gameView.justScanChat();
    }

    @Override
    public void justPrintChat(String message) {
        gameView.justPrintChat(message);
    }

    /**
     * Getter for game ended boolean.
     *
     * @author Francesco Ostidich
     * @return game ended boolean
     */
    boolean isGameEnded() {
        return gameView.isGameEnded();
    }

}
