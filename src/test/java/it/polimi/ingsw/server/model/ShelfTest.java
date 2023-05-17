package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ShelfTest {

    private Shelf shelf;

    private ArrayList<Tile> tilesToInsert;

    @Before
    public void init() {
        shelf = new Shelf();
        tilesToInsert = new ArrayList<>();
    }

    @After
    public void end() {
        shelf = null;
        tilesToInsert = null;
    }

    @Test
    public void getPositions_TilesIsInitialized_ReturnTiles() {
        assertNotNull(shelf.getPositions());
    }

    @Test
    public void getPosition_ValidCoordinates_ReturnTile() {
        int x;
        int y;
        x = 0;
        y = 0;
        assertNotNull(shelf.getPosition(new Coordinates(x, y)));
        x = Shelf.getRowLength() - 1;
        y = Shelf.getColumnLength() - 1;
        assertNotNull(shelf.getPosition(new Coordinates(x, y)));
    }

    @Test
    public void getPosition_InvalidCoordinates_ReturnNull() {
        int x;
        int y;
        x = Shelf.getColumnLength();
        y = Shelf.getColumnLength();
        assertNull(shelf.getPosition(new Coordinates(x, y)));
        assertNull(shelf.getPosition(null));
    }

    @Test
    public void insertInColumn_TilesInput_TilesInShelfWithSuccess() {
        int row = Shelf.getRowLength();
        int col = 0;
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.PLANTS);
        assertTrue(shelf.insertInColumn(tilesToInsert, col));
        assertEquals(Tile.CATS, shelf.getPosition(new Coordinates(row - 1, col)));
        assertEquals(Tile.BOOKS, shelf.getPosition(new Coordinates(row - 2, col)));
        assertEquals(Tile.PLANTS, shelf.getPosition(new Coordinates(row - 3, col)));
    }

    @Test
    public void insertInColumn_InvalidInput_ReturnFalse() {
        //empty tiles or null list
        tilesToInsert.add(Tile.EMPTY);
        assertFalse(shelf.insertInColumn(null, 0));
        assertFalse(shelf.insertInColumn(tilesToInsert, 0));
        //invalid column
        tilesToInsert.remove(0);
        tilesToInsert.add(Tile.CATS);
        assertFalse(shelf.insertInColumn(tilesToInsert, Shelf.getColumnLength()));
    }

    @Test
    public void insertInColumn_TilesInputWithNoSpaceInColumn_ReturnFalse() {
        int col = 0;
        //add tiles and leave 2 spaces
        for (int i = 0; i < Shelf.getRowLength() - 2; i++) {
            tilesToInsert.add(Tile.CATS);
        }
        shelf.insertInColumn(tilesToInsert, col);
        tilesToInsert.clear();
        //try to add 3 tiles (with 2 spaces)
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        assertFalse(shelf.insertInColumn(tilesToInsert, 0));
    }

    @Test
    public void getTilesInColumn_ValidColumn_ReturnNumOfTiles() {
        int col = 0;
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        assertTrue(shelf.insertInColumn(tilesToInsert, col));
        assertEquals(Shelf.getRowLength() - tilesToInsert.size(), shelf.getTilesInColumn(0));
    }

    @Test
    public void getTilesInColumn_InvalidColumn_ReturnZero() {
        assertEquals(0, shelf.getTilesInColumn(Shelf.getColumnLength()));
    }

    @Test
    public void isFull_ShelfFull_ReturnTrue() {
        //prepare tiles
        for (int i = 0; i < Shelf.getRowLength(); i++) {
            tilesToInsert.add(Tile.CATS);
        }
        //insert tiles in each column
        for (int i = 0; i < Shelf.getColumnLength(); i++) {
            shelf.insertInColumn(tilesToInsert, i);
        }
        assertTrue(shelf.isFull());
    }

    @Test
    public void isFull_ShelfIsNotFull_ReturnFalse() {
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.CATS);
        shelf.insertInColumn(tilesToInsert, 0);
        assertFalse(shelf.isFull());
    }

}