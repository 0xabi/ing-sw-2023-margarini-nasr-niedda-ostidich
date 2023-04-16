package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Defines all the actions doable on the view.
 * If values in data structures are null, they do not need to update respective attribute.
 *
 * @author Francesco Ostidich
 */
public interface ViewActions { //TODO java doc to be written

      void setGameParameters(Map<String, Integer> gameParameters);

      void turnCycleOrder(List<String> names);

      void updateBoard(Tile[][] board);

      void updateEndGameToken(boolean present);

      void updateBag(Map<Tile, Integer> tilesLeft);

      void updateCommonGoal1TokenStack(Stack<Integer> tokenStack);

      void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer);

      void updateCommonGoal2TokenStack(Stack<Integer> tokenStack);

      void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer);

      void updatePlayerShelves(List<Tile[][]> shelves);

      void updatePlayerPoints(List<Integer> points);

      void start();

      String chooseIPAddress();

      String choosePlayerName();

      String chooseNewOrJoin();

      String chooseNewGameName();

      int chooseNewGamePlayerNumber();

      void updateGameRoom();

      void notifyGameHasStared();

      String chooseGameRoom(List<GameRoom> rooms);

      void givePersonalGoals(int personalGoal);

      void giveCommonGoals(String commonGoal1, String commonGoal2);

      List<Coordinates> pickTiles(int availablePickNumber);

      List<Tile> chooseOrder(List<Coordinates> selection);

      int chooseColumn();

      void assignCommonGoalPoints(String playerName, int token);

      void assignPersonalGoalPoints(Map<String, Integer> points);

      void assignAdjacentGoalPoints(Map<String, Integer> points);

      void announceWinner(String winnerName, Map<String, Integer> points);

      String waitForEndGameAction();

}
