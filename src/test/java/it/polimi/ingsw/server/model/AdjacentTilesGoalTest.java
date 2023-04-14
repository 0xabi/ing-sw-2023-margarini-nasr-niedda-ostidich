package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AdjacentTilesGoalTest {

    private Player player;
    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    @Before
    public void init() {
        player = new Player("Name", 1);
        shelf = player.getShelf();
        tilesToInsert = new ArrayList<>();
    }

    @After
    public void end()
    {
        player = null;
        shelf = null;
        tilesToInsert=null;
    }

    @Test
    public void assignPoints_3TilesHorizontal_TwoPoints()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        assertTrue(shelf.insertInColumn(tilesToInsert, 0));

        AdjacentTilesGoal.assignPoints(player);
        assertEquals(2,player.getPoints());

    }

    @Test
    public void assignPoints_GroupOf3_GroupOf7_GroupOf4_13Points()
    {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);

        assertTrue(shelf.insertInColumn(tilesToInsert, 0));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);

        assertTrue(shelf.insertInColumn(tilesToInsert, 1));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));
        tilesToInsert.clear();

        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 3));

        AdjacentTilesGoal.assignPoints(player);
        assertEquals(13,player.getPoints());

    }

    @Test
    public void assignPoints_GroupOf15_GroupOf6_GroupOf9_24Points()
    {
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        assertTrue(shelf.insertInColumn(tilesToInsert,0));
        assertTrue(shelf.insertInColumn(tilesToInsert,1));

        tilesToInsert.clear();
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert, 2));

        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert,3));

        tilesToInsert.clear();
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.GAMES);
        assertTrue(shelf.insertInColumn(tilesToInsert,4));

        AdjacentTilesGoal.assignPoints(player);
        assertEquals(24, player.getPoints());
    }

    @Test
    public void assignPoints_Group8_Group4_Group5_Group4_Group3_21Points()
    {
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.PLANTS);

        assertTrue(shelf.insertInColumn(tilesToInsert,0));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.FRAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.PLANTS);

        assertTrue(shelf.insertInColumn(tilesToInsert,1));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.TROPHIES);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.PLANTS);

        assertTrue(shelf.insertInColumn(tilesToInsert,2));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.GAMES);
        tilesToInsert.add(Tile.PLANTS);
        tilesToInsert.add(Tile.PLANTS);

        assertTrue(shelf.insertInColumn(tilesToInsert,3));

        tilesToInsert.clear();

        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.BOOKS);

        assertTrue(shelf.insertInColumn(tilesToInsert,4));

        AdjacentTilesGoal.assignPoints(player);
        assertEquals(21,player.getPoints());



    }
}