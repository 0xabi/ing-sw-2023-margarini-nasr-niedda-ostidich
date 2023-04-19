package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.interfaces.ModelActions;
import it.polimi.ingsw.server.model.GameModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 * Method are called by the controller to notify or retrieve information from the model. Information from model is directly
 * type of return in controller method, passing by here.
 *
 * @author Francesco Ostidich
 */
public class ModelAccess implements ModelActions {

    private final GameModel gameModel;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param gameModel is the model core of the server
     */
    public ModelAccess(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public Tile[][] getBoard() {
        return gameModel.getBoard();
    }

    @Override
    public Map<Tile, Integer> getBag() {
        return gameModel.getBag();
    }

    @Override
    public Optional<Integer> getEndGameToken() {
        return gameModel.getEndGameToken();
    }

    @Override
    public String getCommonGoal1() {
        return gameModel.getCommonGoal1();
    }

    @Override
    public String getCommonGoal2() {
        return gameModel.getCommonGoal2();
    }

    @Override
    public Stack<Integer> getCommonGoal1Tokens() {
        return gameModel.getCommonGoal1Tokens();
    }

    @Override
    public Stack<Integer> getCommonGoal2Tokens() {
        return gameModel.getCommonGoal2Tokens();
    }

    @Override
    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        return gameModel.getCommonGoal1GivenPlayers();
    }

    @Override
    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        return gameModel.getCommonGoal2GivenPlayers();
    }

    @Override
    public int getPlayerPoints(String playerName) {
        return gameModel.getPlayerPoints(playerName);
    }

    @Override
    public Map<Tile, Coordinates> getPlayerPersonalGoal(String playerName) {
        return gameModel.getPlayerPersonalGoal(playerName);
    }

    @Override
    public int getPlayerPersonalGoalID(String playerName) {
        return 0;
    }

    @Override
    public Tile[][] getPlayerShelf(String playerName) {
        return gameModel.getPlayerShelf(playerName);
    }

    @Override
    public Map<Integer, Integer> getPersonalGoalPoints() {
        return gameModel.getPersonalGoalPoints();
    }

    @Override
    public Map<Integer, Integer> getAdjacentGoalPoints() {
        return gameModel.getAdjacentGoalPoints();
    }

    @Override
    public void assignEndGameTokenPoints(String playerName) {
        gameModel.assignEndGameTokenPoints(playerName);
    }

    @Override
    public void assignAdjacentGoalPoints(String playerName) {
        gameModel.assignAdjacentGoalPoints(playerName);
    }

    @Override
    public void refill() {
        gameModel.refill();
    }

    @Override
    public boolean checkToRefill() {
        return gameModel.checkToRefill();
    }

    @Override
    public List<Tile> selectTilesOnBoard(List<Coordinates> selection) {
        return gameModel.selectTilesOnBoard(selection);
    }

    @Override
    public boolean checkSelection(List<Coordinates> selection) {
        return gameModel.checkSelection(selection);
    }

    @Override
    public boolean checkCommonGoal1(String playerName) {
        return gameModel.checkCommonGoal1(playerName);
    }

    @Override
    public boolean checkCommonGoal2(String playerName) {
        return gameModel.checkCommonGoal2(playerName);
    }

    @Override
    public void assignCommonGoal1Points(String playerName) {
        gameModel.assignCommonGoal1Points(playerName);
    }

    @Override
    public void assignCommonGoal2Points(String playerName) {
        gameModel.assignCommonGoal2Points(playerName);
    }

    @Override
    public void playerInsertTilesInShelf(String playerName, List<Tile> tiles, int column) {
        gameModel.playerInsertTilesInShelf(playerName, tiles, column);
    }

    @Override
    public int checkAvailablePickNumber(String playerName) {
        return 0;
    }

    @Override
    public void assignPersonalGoalPoints(String playerName) {
        gameModel.assignPersonalGoalPoints(playerName);
    }

    @Override
    public boolean checkPlayerShelfIsFull(String playerName) {
        return gameModel.checkPlayerShelfIsFull(playerName);
    }

    @Override
    public List<String> getTurnCycleOrder() {
        return gameModel.getTurnCycleOrder();
    }

}
