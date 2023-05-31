package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.general.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TwoNoRepetitionRowsTest {

    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    private CommonGoal cg;

    private static final int numOfPlayers = 3;

    @Before
    public void init() {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
        cg = new TwoNoRepetitionRows(numOfPlayers);
    }

    @After
    public void end() {
        shelf = null;
        tilesToInsert = null;
        cg = null;
    }

    @Test
    public void check_oneNoRepeatedRow_ReturnFalse() {
        tilesToInsert.add(Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 0));
        tilesToInsert.set(0, Tile.BOOKS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 1));
        tilesToInsert.set(0, Tile.FRAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.set(0, Tile.PLANTS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));
        tilesToInsert.set(0, Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 4));
        assertFalse(cg.check(shelf));
    }

    @Test
    public void check_twoNoRepeatedRowAdjacent_ReturnTrue() {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 0));
        tilesToInsert.set(0, Tile.PLANTS);
        tilesToInsert.set(1, Tile.BOOKS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 1));
        tilesToInsert.set(0, Tile.FRAMES);
        tilesToInsert.set(1, Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.set(0, Tile.GAMES);
        tilesToInsert.set(1, Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));
        tilesToInsert.set(0, Tile.BOOKS);
        tilesToInsert.set(1, Tile.FRAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 4));
        assertTrue(cg.check(shelf));
    }

    @Test
    public void check_twoNoRepeatedRow_ReturnTrue() {
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 0));
        tilesToInsert.set(0, Tile.PLANTS);
        tilesToInsert.set(1, Tile.PLANTS);
        tilesToInsert.set(2, Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 1));
        tilesToInsert.set(0, Tile.CATS);
        tilesToInsert.set(1, Tile.TROPHIES);
        tilesToInsert.set(2, Tile.PLANTS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.set(0, Tile.GAMES);
        tilesToInsert.set(1, Tile.TROPHIES);
        tilesToInsert.set(2, Tile.BOOKS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));
        tilesToInsert.set(0, Tile.BOOKS);
        tilesToInsert.set(1, Tile.TROPHIES);
        tilesToInsert.set(2, Tile.FRAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 4));
        assertTrue(cg.check(shelf));
    }

    @Test
    public void check_oneAlmostNoRepeatedRow_ReturnFalse() {
        tilesToInsert.add(Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 0));
        tilesToInsert.set(0, Tile.BOOKS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 1));
        tilesToInsert.set(0, Tile.FRAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.set(0, Tile.PLANTS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));
        tilesToInsert.set(0, Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 4));
        assertFalse(cg.check(shelf));
    }

    @Test
    public void check_oneNoRepRowOneAlmostNoRepRow_ReturnFalse() {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 0));
        tilesToInsert.set(0, Tile.PLANTS);
        tilesToInsert.set(1, Tile.BOOKS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 1));
        tilesToInsert.set(0, Tile.FRAMES);
        tilesToInsert.set(1, Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.set(0, Tile.GAMES);
        tilesToInsert.set(1, Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));
        tilesToInsert.set(0, Tile.GAMES);
        tilesToInsert.set(1, Tile.FRAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 4));
        assertFalse(cg.check(shelf));
    }

}