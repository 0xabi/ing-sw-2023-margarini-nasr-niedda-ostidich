package it.polimi.ingsw.server.model;

import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PersonalGoalTest {

    private Player player;

    @Before
    public void init() {
        player = new Player("test", 1);
    }

    @Test
    public void assignPoints_shouldNotAddPlayersPoints() {
        System.out.println(PersonalGoal.getPoints());
        Integer shared = player.getPersonalGoal().matchesShared(player.getShelf());
        System.out.println(shared);
        player.getPersonalGoal().assignPoints(player);
        int point = PersonalGoal.getPoints().get(0);
        Assert.assertEquals(point, player.getPoints());
    }

    @Test
    public void assignPoints_shouldAddOnePlayersPoints() {
        System.out.println(player.getPersonalGoal().getMatches());
        List<Tile> tiles = new LinkedList<>(Arrays.asList(Tile.PLANTS, Tile.PLANTS, Tile.PLANTS, Tile.PLANTS, Tile.CATS));
        System.out.println(player.getShelf().insertInColumn(tiles, 4));
        System.out.println(player.getShelf().getPosition(new Coordinates(4, 1)));
        System.out.println(PersonalGoal.getPoints());
        System.out.println(Arrays.deepToString(player.getShelf().getPositions()));
        Integer shared = player.getPersonalGoal().matchesShared(player.getShelf());
        System.out.println(shared);
        int point = PersonalGoal.getPoints().get(1);
        Assert.assertEquals(player.getPersonalGoal().assignPoints(player), point);
        Assert.assertEquals(point, player.getPoints());
    }

    @Test
    public void getter() {
        Assert.assertEquals(player.getPersonalGoal().getMatches().keySet().size(), Tile.values().length - 1);
        Assert.assertEquals(player.getPersonalGoal().getPersonalGoalID(), 1);
        Assert.assertEquals(PersonalGoal.getPoints().size() - 1, player.getPersonalGoal().getMatches().keySet().size());
        Assert.assertEquals(PersonalGoal.getPersonalGoalNumber(), 12);
    }

}