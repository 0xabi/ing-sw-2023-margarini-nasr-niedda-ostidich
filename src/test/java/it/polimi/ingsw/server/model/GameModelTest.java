package it.polimi.ingsw.server.model;

import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class GameModelTest {

    private final Set<String> names = new HashSet<>(Arrays.asList("kek", "abi", "edo", "pie"));

    private final GameServerModel model = new GameServerModel(names);

    @Test
    public void constructor_notNull() {
        Assert.assertNotNull(model);
        Assert.assertNotNull(model.getBoard());
        Assert.assertNotNull(model.getBag());
        Assert.assertNotNull(model.getCommonGoal1());
        Assert.assertNotNull(model.getCommonGoal2());
        Assert.assertNotNull(model.getCommonGoal1Tokens());
        Assert.assertNotNull(model.getCommonGoal2Tokens());
        Assert.assertNotNull(model.getCommonGoal1GivenPlayers());
        Assert.assertNotNull(model.getCommonGoal2GivenPlayers());
        Assert.assertNotNull(model.getGameParameters());
        Assert.assertNotNull(model.getEndGameToken());
        Assert.assertNotNull(model.getPersonalGoalPoints());
        Assert.assertNotNull(model.getAdjacentGoalPoints());
        Assert.assertNotNull(model.getTurnCycleOrder());
        for (String player : names) {
            Assert.assertNotNull(model.getPlayerPersonalGoal(player));
            Assert.assertNotNull(model.getPlayerShelf(player));
        }
    }

    @Test
    public void constructor_values() {
        Set<Integer> IDs = new HashSet<>();
        Set<Map<Tile, Coordinates>> personalGoals = new HashSet<>();
        Set<Tile[][]> shelves = new HashSet<>();
        Assert.assertNotEquals(model.getCommonGoal1(), model.getCommonGoal2());
        for (String player : names) {
            IDs.add(model.getPlayerPersonalGoalID(player));
            personalGoals.add(model.getPlayerPersonalGoal(player));
            shelves.add(model.getPlayerShelf(player));
            Assert.assertTrue(model.getPlayerPersonalGoalID(player) <= PersonalGoal.getPersonalGoalNumber());
            Assert.assertTrue(model.getPlayerPersonalGoalID(player) > 0);
            Assert.assertTrue(model.getPlayerPoints(player) >= 0);
        }
        Assert.assertEquals(IDs.size(), names.size());
        Assert.assertEquals(personalGoals.size(), names.size());
        Assert.assertEquals(shelves.size(), names.size());
        Assert.assertEquals(model.getPersonalGoalPoints().size(), 7);
        Assert.assertEquals(model.getAdjacentGoalPoints().size(), 4);
    }

    @Test
    public void assigningPoints_addedToPlayer() {
        String player = "kek";
        model.assignEndGameTokenPoints(player);
        Assert.assertEquals(model.getPlayerPoints(player), 1);
        List<Tile> inserting = new LinkedList<>(Arrays.asList(Tile.PLANTS, Tile.PLANTS, Tile.PLANTS));
        model.playerInsertTilesInShelf(player, inserting, 1);
        model.assignAdjacentGoalPoints(player);
        Assert.assertEquals(model.getPlayerPoints(player), 3);
    }

    @Test
    public void board_refill() {
        int row = model.getGameParameters().get("boardRowLength");
        int column = model.getGameParameters().get("boardColumnLength");
        Assert.assertFalse(model.checkToRefill());
        for (int i = 0; i < row; ++i)
            for (int j = 0; j < column; j++)
                if (model.getBoard()[i][j] != null)
                    model.getBoard()[i][j] = Tile.EMPTY;
        model.getBoard()[4][7] = Tile.CATS;
        Assert.assertTrue(model.checkToRefill());
        model.refill();
        Assert.assertFalse(model.checkToRefill());
    }

    @Test
    public void board_selection() {
        List<Coordinates> selection = new LinkedList<>(Arrays.asList(new Coordinates(0, 1), new Coordinates(0, 2)));
        Assert.assertFalse(model.checkSelection(selection));
        selection = new LinkedList<>(Arrays.asList(new Coordinates(4, 5), new Coordinates(5, 3)));
        Assert.assertFalse(model.checkSelection(selection));
        selection = new LinkedList<>(Arrays.asList(new Coordinates(0, 5), new Coordinates(0, 4)));
        Assert.assertTrue(model.checkSelection(selection));
        List<Tile> selected = model.selectTilesOnBoard(selection);
        Assert.assertNotNull(selected);
        Assert.assertEquals(selection.size(), selected.size());
    }

    @Test
    public void checkCommonGoals_emptyShelf() {
        String player = "edo";
        Assert.assertFalse(model.checkCommonGoal1(player));
        Assert.assertFalse(model.checkCommonGoal2(player));
        model.assignCommonGoal1Points(player);
        model.assignCommonGoal2Points(player);
        Assert.assertEquals(model.getPlayerPoints(player), 16);
    }

    @Test
    public void checks_onPlayerShelf() {
        String player = "abi";
        Assert.assertEquals(3, model.checkAvailablePickNumber(player));
        Assert.assertFalse(model.checkPlayerShelfIsFull(player));
        for (int i = 0; i < Shelf.getRowLength(); i++)
            for (int j = 0 ; j < Shelf.getColumnLength(); j++)
                model.getPlayerShelf(player)[i][j] = Tile.GAMES;
        Assert.assertTrue(model.checkPlayerShelfIsFull(player));
        Assert.assertEquals(0, model.checkAvailablePickNumber(player));
    }

    @Test
    public void personalGoal() {
        String player = "pie";
        for (int i = 0; i < Shelf.getRowLength(); i++)
            for (int j = 0 ; j < Shelf.getColumnLength(); j++)
                model.getPlayerShelf(player)[i][j] = Tile.GAMES;
        model.assignPersonalGoalPoints(player);
        Assert.assertEquals(1, model.getPlayerPoints(player));
    }


}