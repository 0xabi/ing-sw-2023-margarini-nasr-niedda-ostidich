package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TwoNoRepetitionColumnsTest {

    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    CommonGoal cg;

    private static final int numOfPlayers = 3;

    @Before
    public void init()
    {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
        cg = new TwoNoRepetitionColumns(numOfPlayers);
    }

    @After
    public void end()
    {
        shelf = null;
        tilesToInsert = null;
        cg = null;
    }

    @Test
    public void check_oneNoRepeatedColumn_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertFalse(cg.check(shelf));


    }

    @Test
    public void check_twoNoRepeatedColumnAdjacent_ReturnTrue()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertTrue(shelf.insertInColumn(tilesToInsert,1));
        assertTrue(cg.check(shelf));

    }

    @Test
    public void check_twoNoRepeatedColumn_ReturnTrue()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertTrue(shelf.insertInColumn(tilesToInsert,2));
        assertTrue(cg.check(shelf));
    }

    @Test
    public void check_oneAlmostNoRepeatedColumn_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertFalse(cg.check(shelf));

    }

    @Test
    public void check_oneNoRepColumnOneAlmostNoRepColumn_ReturnFalse()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.GAMES);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        tilesToInsert.set(0,Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert,2));
        assertFalse(cg.check(shelf));
    }

}