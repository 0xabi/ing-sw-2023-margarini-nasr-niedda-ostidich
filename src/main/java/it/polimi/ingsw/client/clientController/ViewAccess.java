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
}
