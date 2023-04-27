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
public interface ViewActions {

      /**
       * Lets the view know all the game parameter constants (the ones contained in gameParameter.json).
       * This should be called immediately so the game view knows dimensions to build objects.
       *
       * @author Francesco Ostidich
       * @param gameParameters is the map with game parameters
       */
      void setGameParameters(Map<String, Integer> gameParameters);

      /**
       * Gives the view knowledge of the turn cycle order by player name.
       * The first player (already randomly chosen) is the one with the chair.
       * In a tie scenario it's helpful to have a single winner pop out.
       *
       * @author Francesco Ostidich
       * @param names is the players' name list
       */
      void turnCycleOrder(List<String> names);

      /**
       * The board stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param board is the board matrix
       */
      void updateBoard(Tile[][] board);

      /**
       * The end game token stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param present is the end game token boolean
       */
      void updateEndGameToken(boolean present);

      /**
       * The bag stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param tilesLeft is the bag map
       */
      void updateBag(Map<Tile, Integer> tilesLeft);

      /**
       * The common goal scoring token stack stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param tokenStack is the scoring token stack
       */
      void updateCommonGoal1TokenStack(Stack<Integer> tokenStack);

      /**
       * The common goal given players map stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param givenPlayer is the given player map
       */
      void updateCommonGoal1GivenPlayers(Map<Integer, String> givenPlayer);

      /**
       * The common goal scoring token stack stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param tokenStack is the scoring token stack
       */
      void updateCommonGoal2TokenStack(Stack<Integer> tokenStack);

      /**
       * The common goal given players map stored in the view is updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param givenPlayer is the given player map
       */
      void updateCommonGoal2GivenPlayers(Map<Integer, String> givenPlayer);

      /**
       * The players' shelves stored in the view are updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param shelves are the players' shelves
       */
      void updatePlayerShelves(List<Tile[][]> shelves);

      /**
       * The players' points stored in the view are updated and printed if needed.
       *
       * @author Francesco Ostidich
       * @param points are the players' points
       */
      void updatePlayerPoints(List<Integer> points);

      /**
       * When game is initially loaded, requires a start button to be pressed in order to get first options to choose.
       *
       * @author Francesco Ostidich
       */
      void start();

      /**
       * When player wants to connect to server is required to write the server's IP address.
       * "localhost" is accepted.
       *
       * @author Francesco Ostidich
       * @return IP address string written by the player ("x.x.x.x")
       */
      String chooseIPAddress();

      /**
       * When player wants to enter games is required to write his nickname.
       *
       * @author Francesco Ostidich
       * @return nickname written by the player
       */
      String choosePlayerName();

      /**
       * When player wants to play is required to choose between creating a new game room or enter one already opened.
       *
       * @author Francesco Ostidich
       * @return player's selection ("new" or "join")
       */
      String chooseNewOrJoin();

      /**
       * After deciding to create new game room, player has to set a name for it.
       *
       * @author Francesco Ostidich
       * @return game room name written by the player
       */
      String chooseNewGameName();

      /**
       * After deciding to create new game room, player has to set a player number for it.
       *
       * @author Francesco Ostidich
       * @return game room player number written by the player
       */
      int chooseNewGamePlayerNumber();

      /**
       * When a player is in a game room, and its information changes (for example a player enters),
       * it needs to be updated in view.
       *
       * @param gameRoom is game room to update
       * @author Francesco Ostidich
       */
      void updateGameRoom(GameRoom gameRoom);

      /**
       * After player connection to server, when he wants to join a room, the list of all rooms is passed to client.
       *
       * @author Francesco Ostidich
       * @param gameRooms are game rooms to update
       */
      void updateGameRooms(List<GameRoom> gameRooms);

      /**
       * When game room has reached player number set, it notifies all the clients connected that game is started
       * and GUI has to change, showing initial game graphic.
       *
       * @author Francesco Ostidich
       */
      void notifyGameHasStared();

      /**
       * When player wants to join a game, he has to select a room between the ones provided by the server.
       * "refresh" or "back" strings calls different actions.
       *
       * @author Francesco Ostidich
       * @param rooms is the game rooms availability list
       * @return game room selected string
       */
      String chooseGameRoom(List<GameRoom> rooms);

      /**
       * When game ends, personal goals must be forwarded to game view in order to be printed.
       * In CLI, in order to print personal goal, personalGoalMatches.json is to be read.
       *
       * @author Francesco Ostidich
       * @param personalGoals is the personal goal ID number list
       */
      void givePersonalGoals(List<Integer> personalGoals);

      /**
       * When game starts, player's personal goal is sent.
       *
       * @author Francesco Ostidich
       * @param personalGoal is the personal goal ID number
       */
      void givePersonalGoal(int personalGoal);

      /**
       * When game starts, common goals must be forwarded to game view in order to be printed.
       * In CLI, in order to print common goal, commonGoalDescription.json is to be read.
       *
       * @author Francesco Ostidich
       * @param commonGoal1 is the common goal 1 ID string
       * @param commonGoal2 is the common goal 2 ID string
       */
      void giveCommonGoals(String commonGoal1, String commonGoal2);

      /**
       * When player is called to pick on the board, an available max tiles number to be picked is passed.
       * Coordinates picked are returned.
       *
       * @author Francesco Ostidich
       * @param availablePickNumber is the max number of tiles to be picked
       * @return Coordinates of boards picked
       */
      List<Coordinates> pickTiles(int availablePickNumber);

      /**
       * After picked coordinates are checked if legals, player is asked to choose insertion order of the tiles.
       *
       * @author Francesco Ostidich
       * @param selection is the picked tile list
       * @return picked tile list ordered for shelf's insertion
       */
      List<Tile> chooseOrder(List<Tile> selection);

      /**
       * After picked tiles order is defined, player is asked to choose a shelf column to insert the tiles.
       *
       * @author Francesco Ostidich
       * @return column number chosen for shelf's insertion
       */
      int chooseColumn();

      /**
       * When another player is picking tiles, choosing order and choosing column, the view is telling
       * the name of the player playing.
       *
       * @author Francesco Ostidich
       * @param playerName is the player currently playing
       */
      void playerIsPlaying(String playerName);

      /**
       * After player's turn, common goal points are checked if to be given, and if that's the case, pops token to the player.
       * View is to print this process.
       *
       * @author Francesco Ostidich
       * @param playerName is player's name to pop the token to
       * @param token is point token popped
       */
      void assignCommonGoalPoints(String playerName, int token);

      /**
       * When game's last turn is played, personal goal are calculated and forwarded to the view.
       * Only personal goal points are communicated, in order to print them, players points aren't updated here.
       *
       * @author Francesco Ostidich
       * @param points is the personal goal assigned points map
       */
      void assignPersonalGoalPoints(Map<String, Integer> points);

      /**
       * When game's last turn is played, adjacent goal are calculated and forwarded to the view.
       * Only adjacent goal points are communicated, in order to print them, players points aren't updated here.
       *
       * @author Francesco Ostidich
       * @param points is the adjacent goal assigned points map
       */
      void assignAdjacentGoalPoints(Map<String, Integer> points);

      /**
       * When game is finished, winner name is told and all players' points are forwarded to be printed.
       *
       * @author Francesco Ostidich
       * @param winnerName is the winner player's name string
       * @param points is the players' end game points map
       */
      void announceWinner(String winnerName, Map<String, Integer> points);

      /**
       * Method is always opened on other thread, only chat messages are received.
       *
       * @author Francesco Ostidich
       * @return message string
       */
      String justScanChat();

      /**
       * Method is always opened on other thread, only chat messages are showed.
       *
       * @author Francesco Ostidich
       * @param message message string
       */
      void justPrintChat(String message);

}
