package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {


    Player player;
    @Before
    public void init(){player= new Player("whoever", 1);}
    @After
    public void end(){
        player = null;
    }
    @Test
    public void getNameShouldReturnName(){
        assertEquals("whoever", player.getName());
    }
    @Test
    public void PointsShouldBe3(){
        player.addPoints(3);
        assertEquals(3, player.getPoints());
    }
    @Test
    public void getPersonalGoalSholdReturnPersonalGoalZero(){
        assertEquals(1, player.getPersonalGoal().getPersonalGoalID());
    }
    @Test
    public void checkAvailablePickNumberShouldReturn3(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);

        player.getShelf().insertInColumn(tiles, 0);
        player.getShelf().insertInColumn(tiles, 1);
        player.getShelf().insertInColumn(tiles, 2);
        player.getShelf().insertInColumn(tiles, 3);
        player.getShelf().insertInColumn(tiles, 4);

        assertEquals(player.checkAvailablePickNumber(),3);
    }
    @Test
    public void checkAvailablePickNumberShouldReturn2(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.CATS);


        player.getShelf().insertInColumn(tiles, 0);
        player.getShelf().insertInColumn(tiles, 1);
        player.getShelf().insertInColumn(tiles, 2);
        player.getShelf().insertInColumn(tiles, 3);
        player.getShelf().insertInColumn(tiles, 4);

        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);

        player.getShelf().insertInColumn(tiles, 0);
        player.getShelf().insertInColumn(tiles, 1);
        player.getShelf().insertInColumn(tiles, 2);
        player.getShelf().insertInColumn(tiles, 3);
        player.getShelf().insertInColumn(tiles, 4);

        assertEquals(player.checkAvailablePickNumber(),2);
    }
    @Test
    public void checkAvailablePickNumberShouldReturn1() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);

        player.getShelf().insertInColumn(tiles, 0);
        player.getShelf().insertInColumn(tiles, 1);
        player.getShelf().insertInColumn(tiles, 2);
        player.getShelf().insertInColumn(tiles, 3);
        player.getShelf().insertInColumn(tiles, 4);

        tiles.add(Tile.CATS);

        player.getShelf().insertInColumn(tiles, 0);
        player.getShelf().insertInColumn(tiles, 1);
        player.getShelf().insertInColumn(tiles, 2);
        player.getShelf().insertInColumn(tiles, 3);
        player.getShelf().insertInColumn(tiles, 4);

        assertEquals(player.checkAvailablePickNumber(), 1);
    }
}