package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.exceptions.PlayerNotFoundException;
import it.polimi.ingsw.resources.interfaces.ServerModel;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * GameModel must be the only class which, via an interface, talks to the controller.
 * It collects all the method from another classes the composes a player turn, besides generation and conclusion of a game match.
 * GameModel.java is itself enough to have the match keeping going.
 * In the game generation phase the GameModel constructor constructs directly or indirectly the necessary objects
 * (it also makes sure to have different common and personal goals); eventually it starts a turns cycle, repeatedly until
 * a shelf is full (optional value in Board.java). At the end points are calculated and sent to the controller: points
 * are mapped with players' names (in the scenario of a tie game, the controller is able to get a list of player's name
 * to get knowledge of the turns progression and consequently to announce a winner).
 *
 * @author Francesco Ostidich
 */
public class GameServerModel implements ServerModel {

    private final Board board;

    private CommonGoal commonGoal1;

    private CommonGoal commonGoal2;

    private final List<Player> players = new LinkedList<>();

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param names is the list with the players names got from the controller, used for construct the player objects
     */
    public GameServerModel(@NotNull Set<String> names) {
        board = new Board(names.size());
        commonGoalConstructor(names.size());
        playerListConstructor(names);
    }

    /**
     * Constructs both the common goals, while being sure they are different.
     *
     * @author Francesco Ostidich
     */
    private void commonGoalConstructor(int playerNumber) {
        commonGoal1 = CommonGoalFactory.getCommonGoal(playerNumber);
        CommonGoal commonGoalTemp = CommonGoalFactory.getCommonGoal(playerNumber);
        while(commonGoalTemp.equals(commonGoal1)) {
            commonGoalTemp = CommonGoalFactory.getCommonGoal(playerNumber);
        }
        commonGoal2 = commonGoalTemp;
    }

    /**
     * Constructs all the player, sorting out different numbers in order to get different personal goals for each one.
     *
     * @author Francesco Ostidich
     * @param names is the list with the players names got from the controller, used for construct the player objects
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void playerListConstructor(@NotNull Set<String> names) {
        Random random = new Random();
        Set<Integer> personalGoalNumberSet = new HashSet<>();
        while(personalGoalNumberSet.size() < names.size())
            personalGoalNumberSet.add(random.nextInt(1, PersonalGoal.getPersonalGoalNumber()+1));
        Stack<Integer> personalGoalNumberStack = new Stack<>();
        personalGoalNumberSet.stream().map(personalGoalNumberStack::add);

        Set<Player> playerSet = new HashSet<>();
        for(String playerName: names) {
            playerSet.add(new Player(playerName, personalGoalNumberStack.pop()));
        }
        playerSet.stream().map(players::add);
    }

    @Override
    public Tile[][] getBoard() {
        return board.getSpaces();
    }

    @Override
    public Map<Tile, Integer> getBag() {
        return board.getBag().getTilesLeft();
    }

    @Override
    public Optional<Integer> getEndGameToken() {
        return board.getEndGameToken().map(EndGameToken::getEndGamePoints);
    }

    @Override
    public String getCommonGoal1() {
        return commonGoal1.getCommonGoalName();
    }

    @Override
    public String getCommonGoal2() {
        return commonGoal2.getCommonGoalName();
    }

    @Override
    public Stack<Integer> getCommonGoal1Tokens() {
        return commonGoal1.getTokens().getTokenStack();
    }

    @Override
    public Stack<Integer> getCommonGoal2Tokens() {
        return commonGoal2.getTokens().getTokenStack();
    }

    @Override
    public Map<Integer, String> getCommonGoal1GivenPlayers() {
        Map<Integer, String> stringMap = new HashMap<>();
        for(int token: commonGoal1.getGivenPlayers().keySet()) {
            stringMap.put(token, commonGoal1.getGivenPlayers().get(token).getName());
        }
        return stringMap;
    }

    @Override
    public Map<Integer, String> getCommonGoal2GivenPlayers() {
        Map<Integer, String> stringMap = new HashMap<>();
        for(int token: commonGoal2.getGivenPlayers().keySet()) {
            stringMap.put(token, commonGoal2.getGivenPlayers().get(token).getName());
        }
        return stringMap;
    }

    @Override
    public int getPlayerPoints(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.getPoints();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public Map<Tile, Coordinates> getPlayerPersonalGoal(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.getPersonalGoal().getMatches();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public int getPlayerPersonalGoalID(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.getPersonalGoal().getPersonalGoalID();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public Tile[][] getPlayerShelf(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.getShelf().getPositions();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public Map<Integer, Integer> getPersonalGoalPoints() {
        return PersonalGoal.getPoints();
    }

    @Override
    public Map<Integer, Integer> getAdjacentGoalPoints() {
        return AdjacentTilesGoal.getGroupPoints();
    }

    @Override
    public void assignEndGameTokenPoints(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                EndGameToken.assignPoints(player);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void assignAdjacentGoalPoints(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                AdjacentTilesGoal.assignPoints(player);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void refill() {
        board.refill();
    }

    @Override
    public boolean checkToRefill() {
        return board.checkToRefill();
    }

    @Override
    public List<Tile> selectTilesOnBoard(List<Coordinates> selection) {
        return board.selectTiles(selection);
    }

    @Override
    public boolean checkSelection(List<Coordinates> selection) {
        return board.checkSelection(selection);
    }

    @Override
    public boolean checkCommonGoal1(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return commonGoal1.check(player.getShelf());
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public boolean checkCommonGoal2(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return commonGoal2.check(player.getShelf());
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void assignCommonGoal1Points(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                commonGoal1.assignPoints(player);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void assignCommonGoal2Points(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                commonGoal2.assignPoints(player);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void playerInsertTilesInShelf(String playerName, List<Tile> tiles, int column) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                player.insertTiles(tiles, column);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public int checkAvailablePickNumber(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.checkAvailablePickNumber();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public void assignPersonalGoalPoints(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                player.getPersonalGoal().assignPoints(player);
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    @Override
    public boolean checkPlayerShelfIsFull(String playerName) {
        for(Player player: players) {
            if(playerName.equals(player.getName()))
                return player.getShelf().isFull();
        }
        throw new PlayerNotFoundException("name string not found in any player of the match");
    }

    public List<String> getTurnCycleOrder() {
        List<String> namesOrder = new LinkedList<>();
        for(Player player: players) {
            namesOrder.add(player.getName());
        }
        return namesOrder;
    }

}
