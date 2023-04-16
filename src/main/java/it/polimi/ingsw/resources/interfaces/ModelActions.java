package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 * Defines all the actions doable on the model by the controller.
 *
 * @author Francesco Ostidich
 */
public interface ModelActions {

    /**
     * Getter for board.
     *
     * @return board matrix
     * @author Francesco Ostidich
     */
    Tile[][] getBoard();

    /**
     * Getter for bag.
     *
     * @author Francesco Ostidich
     * @return bag map
     */
    Map<Tile, Integer> getBag();

    /**
     * Getter for end game token.
     *
     * @author Francesco Ostidich
     * @return end game token optional
     */
    Optional<Integer> getEndGameToken();

    /**
     * Getter for common goal 1.
     *
     * @author Francesco Ostidich
     * @return common goal 1 string for name
     */
    String getCommonGoal1();

    /**
     * Getter for common goal 2.
     *
     * @author Francesco Ostidich
     * @return common goal 2 string for name
     */
    String getCommonGoal2();

    /**
     * Getter for common goal 1's tokens.
     *
     * @author Francesco Ostidich
     * @return tokens stack
     */
    Stack<Integer> getCommonGoal1Tokens();

    /**
     * Getter for common goal 2's tokens.
     *
     * @author Francesco Ostidich
     * @return tokens stack
     */
    Stack<Integer> getCommonGoal2Tokens();

    /**
     * Getter for common goal 1's given players.
     *
     * @author Francesco Ostidich
     * @return given players map
     */
    Map<Integer, String> getCommonGoal1GivenPlayers();

    /**
     * Getter for common goal 2's given players.
     *
     * @author Francesco Ostidich
     * @return given players map
     */
    Map<Integer, String> getCommonGoal2GivenPlayers();

    /**
     * Getter for player's points.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return player's points number
     */
    int getPlayerPoints(String playerName);

    /**
     * Getter for player's personal goal.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return personal goal's matches map
     */
    Map<Tile, Coordinates> getPlayerPersonalGoal(String playerName);

    /**
     * Getter for player's personal goal ID number.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return personal goal's ID number
     */
    int getPlayerPersonalGoalID(String playerName);

    /**
     * Getter for player's shelf.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return player's shelf matrix
     */
    Tile[][] getPlayerShelf(String playerName);

    /**
     * Getter for personal goal matches points.
     *
     * @author Francesco Ostidich
     * @return personal goal matches points map
     */
    Map<Integer, Integer> getPersonalGoalPoints();

    /**
     * Getter for adjacent goal groups points.
     *
     * @author Francesco Ostidich
     * @return adjacent goal groups points map
     */
    Map<Integer, Integer> getAdjacentGoalPoints();

    /**
     * Assigns end game token points to player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     */
    void assignEndGameTokenPoints(String playerName);

    /**
     * Assigns adjacent goal groups points to player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     */
    void assignAdjacentGoalPoints(String playerName);

    /**
     * Refills board.
     *
     * @author Francesco Ostidich
     */
    void refill();

    /**
     * Checks whether the board is to be refilled.
     *
     * @author Francesco Ostidich
     * @return check's outcome
     */
    boolean checkToRefill();

    /**
     * Removes tiles in the provided coordinates.
     *
     * @author Francesco Ostidich
     * @param selection is the picked coordinates by the player
     * @return the respective tiles of the coordinates
     */
    List<Tile> selectTilesOnBoard(List<Coordinates> selection);

    /**
     * Checks whether the coordinates selected are valid.
     *
     * @author Francesco Ostidich
     * @param selection is the picked coordinates by the player
     * @return check's outcome
     */
    boolean checkSelection(List<Coordinates> selection);

    /**
     * Checks whether common goal 2 has been fulfilled for the player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return checks outcome
     */
    boolean checkCommonGoal1(String playerName);

    /**
     * Checks whether common goal 1 has been fulfilled for the player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return checks outcome
     */
    boolean checkCommonGoal2(String playerName);

    /**
     * Assigns common goal 1's upper token to the player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     */
    void assignCommonGoal1Points(String playerName);

    /**
     * Assigns common goal 2's upper token to the player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     */
    void assignCommonGoal2Points(String playerName);

    /**
     * Inserts selected tiles in player's shelf, in the chosen column.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @param tiles is the selected tiles list
     * @param column is the chosen column number
     */
    void playerInsertTilesInShelf(String playerName, List<Tile> tiles, int column);

    /**
     * Check's what the available tile's number to pick is.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return check's outcome
     */
    int checkAvailablePickNumber(String playerName);

    /**
     * Assigns personal goal matches points to player.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     */
    void assignPersonalGoalPoints(String playerName);

    /**
     * Checks whether player's shelf is filled.
     *
     * @author Francesco Ostidich
     * @param playerName is the player's name string
     * @return check's outcome
     */
    boolean checkPlayerShelfIsFull(String playerName);

    /**
     * Returns players' names sorted as the turn cycle order.
     *
     * @author Francesco Ostidich
     * @return players' names list sorted as the turn cycle is played
     */
    List<String> getTurnCycleOrder();

}
