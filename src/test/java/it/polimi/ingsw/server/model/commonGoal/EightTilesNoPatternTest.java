package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class EightTilesNoPatternTest {
    Shelf shelf;
    CommonGoal goal;
    @Before
    public void init(){
        shelf = new Shelf();
        goal  = new EightTilesNoPattern(2);
    }
    @After
    public void end(){
        shelf = null;
    }
    @Test
    public void checkShouldReturnFalase(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BOOKS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.FRAMES);

        for(int i = 0; i < shelf.getRowLength(); i++)
            shelf.insertInColumn(tiles, i);

        assertFalse(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void checkSholdReturnTrue(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.BOOKS);
        tiles.add(Tile.CATS);
        tiles.add(Tile.FRAMES);

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < shelf.getRowLength(); j++)
                shelf.insertInColumn(tiles, j);

        assertTrue(goal.check(shelf));

        shelf = new Shelf();
    }
    @Test
    public void nameIsEightTilesNoPattern(){
        assertEquals(goal.getCommonGoalName(), "EightTilesNoPattern");
    }
}