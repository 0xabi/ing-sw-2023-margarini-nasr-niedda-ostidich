package it.polimi.ingsw.server.model.commonGoal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;

public class FiveTilesXTest {

    private Shelf shelf;

    private LinkedList<Tile> tilesToInsert;

    private FiveTilesX commonGoal;

    private static final int numOfPlayers = 3;

    @Before
    public void init() {
        shelf = new Shelf();
        tilesToInsert = new LinkedList<>();
        commonGoal = new FiveTilesX(numOfPlayers);
    }

    @After
    public void end() {
        shelf = null;
        tilesToInsert = null;
        commonGoal = null;
    }

    @Test
    public void testCheckYesFiveTilesX() {
        tilesToInsert = new LinkedList<>(Arrays.asList(Tile.BOOKS, Tile.BOOKS, Tile.BOOKS, Tile.BOOKS, Tile.BOOKS));
        assertTrue(shelf.insertInRow(tilesToInsert, 0));
        assertTrue(shelf.insertInRow(tilesToInsert, 1));
        assertTrue(shelf.insertInRow(tilesToInsert, 2));
        assertTrue(shelf.insertInRow(tilesToInsert, 3));
        assertTrue(commonGoal.check(shelf));
    }

    @Test
    public void testCheckNoFiveTilesX() {
        tilesToInsert = new LinkedList<>(Arrays.asList(Tile.BOOKS, Tile.FRAMES, Tile.BOOKS, Tile.BOOKS, Tile.FRAMES));
        assertTrue(shelf.insertInRow(tilesToInsert, 1));
        assertTrue(shelf.insertInRow(tilesToInsert, 2));
        assertTrue(shelf.insertInRow(tilesToInsert, 3));
        assertTrue(shelf.insertInRow(tilesToInsert, 4));
        assertFalse(commonGoal.check(shelf));
    }

    @Test
    public void testCheckNoFiveTilesXEmpty() {
        assertFalse(commonGoal.check(shelf));
    }

    @Test
    public void testCheckNoFiveTilesXIncompleteRow() {
        tilesToInsert = new LinkedList<>(Arrays.asList(Tile.BOOKS, Tile.FRAMES, Tile.BOOKS, Tile.BOOKS));
        assertTrue(shelf.insertInRow(tilesToInsert, 0));
        tilesToInsert = new LinkedList<>(Arrays.asList(Tile.BOOKS, Tile.FRAMES));
        assertTrue(shelf.insertInRow(tilesToInsert, 1));
        assertFalse(commonGoal.check(shelf));
    }

}