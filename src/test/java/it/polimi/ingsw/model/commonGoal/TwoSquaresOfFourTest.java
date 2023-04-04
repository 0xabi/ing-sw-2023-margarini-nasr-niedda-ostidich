package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TwoSquaresOfFourTest {

    private Shelf shelf;
    private ArrayList<Tile> tilesToInsert;
    CommonGoal cg;

    int numOfPlayers=3;

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

        int col = shelf.getColumnNumber() -1;

        assertTrue(shelf.insertInColumn(tilesToInsert,col));
        assertTrue(shelf.insertInColumn(tilesToInsert,col-1));
        assertFalse(cg.check(shelf));
    }

    @Test
    public void check_TwoIsolatedSquareDifferentTypes_ReturnTrue()
    {

    }

    @Test
    public void check_TwoIsolatedSquareSameTypes_ReturnTrue()
    {

    }

    @Test
    public void check_TwoNoIsolatedSquare_ReturnFalse()
    {

    }

    @Test
    public void check_TwoDifferentAdjacentSquare_ReturnTrue()
    {

    }

    @Test
    public void check_NoTwoSquares_ReturnFalse()
    {

    }



}