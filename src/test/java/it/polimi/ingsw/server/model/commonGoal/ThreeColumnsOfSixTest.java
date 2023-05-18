package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ThreeColumnsOfSixTest {

    private CommonGoal commonGoal;

    private Shelf shelf;

    @Before
    public void init() {
        commonGoal = new ThreeColumnsOfSix(2);
        shelf = new Shelf();
    }

    @After
    public void end() {
        commonGoal = null;
        shelf = null;
    }

    @Test
    public void check_true() {
        List<Tile> tiles = new LinkedList<>();
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 0);
        tiles.clear();
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 1);
        tiles.clear();
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 2);
        tiles.clear();
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        shelf.insertInColumn(tiles, 3);
        tiles.clear();
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 4);
        Assert.assertTrue(commonGoal.check(shelf));
    }

    @Test
    public void check_false() {
        List<Tile> tiles = new LinkedList<>();
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.CATS);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 1);
        tiles.clear();
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.CATS);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 2);
        tiles.clear();
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.TROPHIES);
        shelf.insertInColumn(tiles, 3);
        tiles.clear();
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.GAMES);
        shelf.insertInColumn(tiles, 4);
        Assert.assertFalse(commonGoal.check(shelf));
    }
    
}