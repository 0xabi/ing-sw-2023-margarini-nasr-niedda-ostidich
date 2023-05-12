package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TwoSquaresOfFourTest {

    private Shelf shelf;
    private ArrayList<Tile> tilesToInsert;
    CommonGoal cg;

    private static final int numOfPlayers = 3;

    @Before
    public void init()
    {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
        cg = new TwoSquaresOfFour(numOfPlayers);
    }

    @After
    public void end()
    {
        shelf = null;
        tilesToInsert = null;
        cg = null;
    }

    @Test
    public void check_OneIsolatedSquare_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));
        assertFalse(cg.check(shelf));
    }

    @Test
    public void check_TwoIsolatedSquareDifferentTypes_ReturnTrue()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertTrue(shelf.insertInColumn(tilesToInsert,1));

        assertTrue(cg.check(shelf));

    }

    @Test
    public void check_TwoIsolatedSquareSameTypes_ReturnTrue()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));
        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertTrue(shelf.insertInColumn(tilesToInsert,1));

        assertTrue(cg.check(shelf));
    }

    @Test
    public void check_TwoNoIsolatedSquare_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-2));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-3));

        assertFalse(cg.check(shelf));
    }

    @Test
    public void check_TwoDifferentAdjacentSquare_ReturnTrue()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);

        assertTrue(shelf.insertInColumn(tilesToInsert,col-2));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-3));

        assertTrue(cg.check(shelf));
    }

    @Test
    public void check_NoTwoSquares_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        int col = Shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);

        assertTrue(shelf.insertInColumn(tilesToInsert,col-2));

        tilesToInsert.add(Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert,col-3));

        assertFalse(cg.check(shelf));

    }
}