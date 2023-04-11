package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GameModel must bee the only class which, via an interface, talks to the controller.
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
public class GameModel {

    private final Board board;

    private CommonGoal commonGoal1;

    private CommonGoal commonGoal2;

    private final List<Player> players = new LinkedList<>();

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
     * Class constructor.
     *
     * @author Francesco Ostidich
     * @param names is the list with the players names got from the controller, used for construct the player objects
     */
    public GameModel(@NotNull List<String> names, int playerNumber) {
        board = new Board(names.size());
        commonGoalConstructor(playerNumber);
        playerListConstructor(names);
    }

    /**
     * Constructs all the player, sorting out different numbers in order to get different personal goals for each one.
     *
     * @author Francesco Ostidich
     * @param names is the list with the players names got from the controller, used for construct the player objects
     */
    private void playerListConstructor(@NotNull List<String> names) {
        Random random = new Random();
        Set<Integer> personalGoalNumbersSet = new HashSet<>();
        while(personalGoalNumbersSet.size() < names.size())
            personalGoalNumbersSet.add(random.nextInt(1, CommonGoalFactory.getCommonGoalNumber()+1));
        Stack<Integer> personalGoalNumbers = new Stack<>();
        @SuppressWarnings("unused") Set<Boolean> dummy = personalGoalNumbersSet.stream().map(personalGoalNumbers::add).collect(Collectors.toSet());

        int playerNumber = names.size();
        for(int i = 0; i < playerNumber; i++)
            players.add(new Player(names.get(random.nextInt(0, playerNumber)), personalGoalNumbers.pop()));
    }

    /**
     * <p>For each player in the game, the method lets him play a single turn.</p>
     * <p>Order of a turn actions: <br>
     * - player picks tiles <br>
     * - player choose order <br>
     * - player insert tiles in his shelf <br>
     * - common goals checked <br>
     * - common goals points assigned <br>
     * - check if the board is to be filled <br>
     * - if the optional in Board.java is present:<br>
     * &#32&#32&#32- checks if the player's shelf is filled<br>
     * &#32&#32&#32- assigns points of the end game token if its filled</p>
     *
     * @author Francesco Ostidich
     */
    public void turnCycle() {
        List<Coordinates> choiceCoordinates;
        List<Tile> choiceTiles = new LinkedList<>();

        for(Player player: players) {

            choiceCoordinates = player.pickTiles(board);
            for(Coordinates coordinates: choiceCoordinates)
                choiceTiles.add(board.getTileInBoard(coordinates));
            while(board.selectTiles(choiceCoordinates).isEmpty()) {
                choiceCoordinates = player.pickTiles(board);
                choiceTiles.clear();
                for(Coordinates coordinates: choiceCoordinates)
                    choiceTiles.add(board.getTileInBoard(coordinates));
            }

            player.insertTiles(choiceTiles);

            if(!commonGoal1.getGivenPlayers().containsValue(player) &&
                    commonGoal1.check(player.getShelf())) {
                commonGoal1.assignPoints(player);
            }
            if(!commonGoal2.getGivenPlayers().containsValue(player) &&
                    commonGoal2.check(player.getShelf())) {
                commonGoal2.assignPoints(player);
            }

            board.checkToRefill();

            if(player.getShelf().isFull()) {
                board.getEndGameToken().ifPresent(endGameToken -> {
                    endGameToken.assignPoints(player);
                    board.setEndGameToken(Optional.empty());
                });
            }
        }
    }

    /**
     * Checks for a players connection in order to continue the turn cycle. Otherwise, the turn should be played randomly,
     * or skipped if no action has been taken yet.
     *
     * @param player is the player to check connection of
     * @return boolean value true if the player is still connected and playing
     */
    //TODO method code is to be written when socket and RMI are implemented
    @SuppressWarnings("SameReturnValue")
    private boolean checkForPlayerConnection(Player player) {
        return true;
    }

    /**
     * <p>When the turn cycle is finished, and at least a shelf is filled, points are being processed and sent to the controller
     * in a map.</p>
     * <p>Order of actions to calculate points:<br>
     * - personal goals are calculated<br>
     * - adjacent tiles goals are calculated</p>
     * <p>Controller should call method GameModel.getTurnCycleOrder() if there's a tie scenario, in order to
     * choose a winner</p>
     * @author Francesco Ostidich
     * @return the map with players' names as keys and respective points as values
     */
    public Map<String, Integer> calculatePoints() {
        Map<String, Integer> playersPoints = new HashMap<>();
        for(Player player: players) {
            player.getPersonalGoal().assignPoints(player);
            AdjacentTilesGoal.assignPoints(player);
            playersPoints.put(player.getName(), player.getPoints());
        }
        return playersPoints;
    }

    /**
     * <p>Used in case of a tie</p>
     * <p>Returns the list of players' names based on the turn cycle order</p>
     *
     * @author Francesco Ostidich
     * @return the list of players' names sorted as the turn cycle was played
     */
    public List<String> getTurnCycleOrder() {
        List<String> namesOrder = new LinkedList<>();
        for(Player player: players) {
            namesOrder.add(player.getName());
        }
        return namesOrder;
    }

}
