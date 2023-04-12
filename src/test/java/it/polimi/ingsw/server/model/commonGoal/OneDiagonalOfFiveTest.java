package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.server.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OneDiagonalOfFiveTest {

    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    private CommonGoal cg;

    private static final int numOfPlayers = 3;
    @Before
    public void init()
    {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
        cg = new OneDiagonalOfFive(numOfPlayers);
    }

    @After
    public void end()
    {
        shelf = null;
        tilesToInsert = null;
        cg = null;
    }


    @Test
    public void DiagonalType1ShouldBeTrue() {

        for (int col = 0; col < shelf.getColumnNumber(); col++) {
            tilesToInsert.add(Tile.CATS);
            shelf.insertInColumn(tilesToInsert, col);
        }

        assertTrue(cg.check(shelf));
    }

    @Test
    public void DiagonalType2ShouldBeTrue() {

        for (int col = 0; col < shelf.getColumnNumber(); col++) {
            tilesToInsert.add(Tile.CATS);
            shelf.insertInColumn(tilesToInsert,shelf.getColumnNumber()-1-col);
        }

        assertTrue(cg.check(shelf));
    }
    @Test
    public void DiagonalType3ShouldBeTrue() {

        tilesToInsert.add(Tile.CATS);
        for (int col = 0; col < shelf.getColumnNumber(); col++) {
            tilesToInsert.add(Tile.CATS);
            shelf.insertInColumn(tilesToInsert, col);
        }

        assertTrue(cg.check(shelf));
    }

    @Test
    public void DiagonalType4ShouldBeTrue() {

        tilesToInsert.add(Tile.CATS);
        for (int col = 0; col < shelf.getColumnNumber(); col++) {
            tilesToInsert.add(Tile.CATS);
            shelf.insertInColumn(tilesToInsert,shelf.getColumnNumber()-1-col);
        }

        assertTrue(cg.check(shelf));
    }

    @Test
    public void ThisShelfShouldNotBeValid(){

        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.PLANTS);
        shelf.insertInColumn(tilesToInsert,0);
        tilesToInsert.clear();
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.FRAMES);
        shelf.insertInColumn(tilesToInsert,3);
        assertFalse(cg.check(shelf));
    }
}
