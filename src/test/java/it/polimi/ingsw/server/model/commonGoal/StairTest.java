package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StairTest {
    Shelf shelf;
    CommonGoal goal;
    @Before
    public void init(){
        shelf = new Shelf();
        goal = new Stair(2);
    }
    @After
    public void end(){
        shelf = null;
        goal = null;
    }
    @Test
    public void checkShouldReturnFalseWithoutTiles(){
        assertFalse(goal.check(shelf));
    }
    @Test
    public void checkShouldReturnFalseWithTiles(){
        int c = 1;
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.CATS);
        tiles.add(Tile.CATS);

        for(int i = 0; i < Shelf.getRowLength(); i++)
            shelf.insertInColumn(tiles, i);

        assertFalse(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void checkShouldReturnTrueStair(){
        int c = 1;
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.CATS);

        for(int i = 0; i < Shelf.getRowLength(); i++){
            for(int j = 0; j < c; j++)
                shelf.insertInColumn(tiles, i);

            c++;
        }

        assertTrue(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void checkShouldReturnTrueReverseStairs(){

        int c = Shelf.getRowLength();
        ArrayList<Tile> tiles1 = new ArrayList<>();
        tiles1.add(Tile.CATS);
        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(Tile.CATS);
        tiles2.add(Tile.CATS);
        ArrayList<Tile> tiles3 = new ArrayList<>();
        tiles3.add(Tile.CATS);
        tiles3.add(Tile.CATS);
        tiles3.add(Tile.CATS);

        shelf.insertInColumn(tiles3, 0);
        shelf.insertInColumn(tiles2, 0);
        shelf.insertInColumn(tiles3, 1);
        shelf.insertInColumn(tiles1, 1);
        shelf.insertInColumn(tiles3, 2);
        shelf.insertInColumn(tiles2, 3);
        shelf.insertInColumn(tiles1, 4);


        assertTrue(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void nameIsStairs(){assertEquals(goal.getCommonGoalName(), "Stair");}
}