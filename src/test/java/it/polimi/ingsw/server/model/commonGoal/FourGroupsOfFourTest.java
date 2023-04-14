package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FourGroupsOfFourTest {

    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    private CommonGoal cg;

    private static final int numOfPlayers = 3;

    @Before
    public void init()
    {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
        cg = new FourGroupsOfFour(numOfPlayers);
    }

    @After
    public void end()
    {
        shelf = null;
        tilesToInsert = null;
        cg = null;
    }

    @Test
    public void TheCommonGoalWithThisShelfShouldBeAchieved() {

        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 0);
        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 2);
        shelf.insertInColumn(tilesToInsert, 3);
        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 4);


        assertTrue(cg.check(shelf));
    }

    @Test
    public void TheCommonGoalWithThisShelfShouldNotBeAchieved() {

        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 0);
        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 2);
        shelf.insertInColumn(tilesToInsert, 3);
        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 4);


        assertFalse(cg.check(shelf));
    }

}