package it.polimi.ingsw.model;

import java.util.*;
import java.util.stream.Collectors;

public class GameModel {

    private final Board board;

    private CommonGoal commonGoal1;

    private CommonGoal commonGoal2;

    private final List<Player> players = new LinkedList<>();

    public GameModel(List<String> names) {
        board = new Board(names.size());
        commonGoalConstructor();
        playerListConstructor(names);
    }

    private void commonGoalConstructor() {
        commonGoal1 = CommonGoalFactory.getCommonGoal();
        CommonGoal commonGoalTemp = CommonGoalFactory.getCommonGoal();
        while(commonGoalTemp.equals(commonGoal1)) {
            commonGoalTemp = CommonGoalFactory.getCommonGoal();
        }
        commonGoal2 = commonGoalTemp;
    }

    private void playerListConstructor(List<String> names) {
        Random random = new Random();
        Set<Integer> personalGoalNumbersSet = new HashSet<>();
        while(personalGoalNumbersSet.size() < names.size())
            personalGoalNumbersSet.add(random.nextInt(1, 13));
        Stack<Integer> personalGoalNumbers = new Stack<>();
        Set<Boolean> dummy = personalGoalNumbersSet.stream().map(personalGoalNumbers::add).collect(Collectors.toSet());

        int playerNumber = names.size();
        for(int i = 0; i < playerNumber; i++)
            players.add(new Player(names.get(random.nextInt(0, names.size())), personalGoalNumbers.pop()));
    }

    public boolean turnCycle() {
        List<Coordinates> choiceCoordinates;
        List<Tile> choiceTiles = new LinkedList<>();

        for(Player player: players) {

            choiceCoordinates = player.pickTiles(board);
            for(Coordinates coordinates: choiceCoordinates)
                choiceTiles.add(board.getTileInBoard(coordinates));
            while(!board.selectTiles(choiceCoordinates)) {
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

        return true;
    }

    private boolean checkForPlayerConnection() {
        //makes sure the player is still connected and playing
        return true;
    }

    public Map<String, Integer> calculatePoints() {
        Map<String, Integer> playersPoints = new HashMap<>();
        for(Player player: players) {
            player.getPersonalGoal().assignPoints(player);
            AdjacentTilesGoal.assignPoints(player);
            playersPoints.put(player.getName(), player.getPoints());
        }
        return playersPoints;
    }

    public List<String> getTurnCycleOrder() {
        List<String> namesOrder = new LinkedList<>();
        for(Player player: players) {
            namesOrder.add(player.getName());
        }
        return namesOrder;
    }

}
