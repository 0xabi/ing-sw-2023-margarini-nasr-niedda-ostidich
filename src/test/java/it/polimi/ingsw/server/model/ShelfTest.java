package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class ShelfTest {

    private Shelf shelf;

    private List<Tile> tilesToInsert;

    @Before
    public void init() {
        shelf = new Shelf();
        tilesToInsert = new LinkedList<>();
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
        assertNull(shelf.getPosition(new Coordinates(x, y)));
        x = Shelf.getRowLength() - 1;
        y = Shelf.getColumnLength() - 1;
        assertNull(shelf.getPosition(new Coordinates(x, y)));
    }

    @Test
    public void getPosition_InvalidCoordinates_ReturnThrow() {
        int x;
        int y;
        x = Shelf.getColumnLength();
        y = Shelf.getColumnLength();
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getPosition(new Coordinates(x, y)));
    }

    @Test
    public void insertInColumn_TilesInput_TilesInShelfWithSuccess() {
        int col = 0;
        tilesToInsert.add(Tile.CATS);
        tilesToInsert.add(Tile.BOOKS);
        tilesToInsert.add(Tile.PLANTS);
        assertTrue(shelf.insertInColumn(tilesToInsert, col));
        assertEquals(Tile.CATS, shelf.getPosition(new Coordinates(col, 0)));
        assertEquals(Tile.BOOKS, shelf.getPosition(new Coordinates(col, 1)));
        assertEquals(Tile.PLANTS, shelf.getPosition(new Coordinates(col, 2)));
    }

    @Test
    public void insertInColumn_InvalidInput_Throws() {
        //empty tiles or null list
        tilesToInsert.add(Tile.EMPTY);
        assertThrows(IllegalArgumentException.class, () -> shelf.insertInColumn(tilesToInsert, 0));
        //invalid column
        tilesToInsert.remove(0);
        tilesToInsert.add(Tile.CATS);
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.insertInColumn(tilesToInsert, Shelf.getColumnLength()));
    }

    @Test
    public void insertInColumn_TilesInputWithNoSpaceInColumn_ReturnFalse() {
        int col = 0;
        //add tiles and leave 2 spaces
        for (int i = 0; i < Shelf.getColumnLength() - 2; i++) {
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
        assertEquals(Shelf.getColumnLength() - tilesToInsert.size(), shelf.getTilesInColumn(0));
    }

    @Test
    public void getTilesInColumn_InvalidColumn_Throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> shelf.getTilesInColumn(Shelf.getRowLength()));
    }

    @Test
    public void isFull_ShelfFull_ReturnTrue() {
        //prepare tiles
        for (int i = 0; i < Shelf.getColumnLength(); i++) {
            tilesToInsert.add(Tile.CATS);
        }
        //insert tiles in each column
        for (int i = 0; i < Shelf.getRowLength(); i++) {
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