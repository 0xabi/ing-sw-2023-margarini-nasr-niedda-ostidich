package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CornersTest {

    Shelf shelf;
    CommonGoal goal;
    @Before
    public void init(){
        shelf = new Shelf();
        goal  = new Corners(2);
    }
    @After
    public void end(){
        shelf = null;
    }
    @Test
    public void checkShouldReturnFalseWithoutTiles(){assertFalse(goal.check(shelf));}
    @Test
    public void checkShouldReturnFalseWithTiles(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BOOKS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.FRAMES);

        for(int i = 0; i < Shelf.getRowLength(); i++)
            shelf.insertInColumn(tiles, i);

        assertFalse(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void checkShouldReturnTrue(){
        ArrayList<Tile> tiles1 = new ArrayList<>();
        ArrayList<Tile> tiles2 = new ArrayList<>();
        tiles2.add(Tile.TROPHIES);
        tiles1.add(Tile.CATS);

        Coordinates co1 = new Coordinates(0, 0);
        Coordinates co2 = new Coordinates(0, Shelf.getColumnLength() - 1);
        Coordinates co3 = new Coordinates(Shelf.getRowLength() - 1, 0);
        Coordinates co4 = new Coordinates(Shelf.getRowLength() - 1, Shelf.getColumnLength() - 1);
        Coordinates coord;

        for(int i = 0; i < Shelf.getColumnLength(); i++)
            for(int j = 0; j < Shelf.getRowLength(); j++) {
                coord = new Coordinates(i, j);
                if(coord == co1 || coord == co2 || coord == co3 || coord == co4)
                    shelf.insertInColumn(tiles1, j);
                else shelf.insertInColumn(tiles2, j);
            }

        assertTrue(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void nameIsCorners(){assertEquals(goal.getCommonGoalName(), "Corners");}

}