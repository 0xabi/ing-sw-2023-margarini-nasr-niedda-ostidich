package it.polimi.ingsw.model;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class BoardTest{
    private Board board;
    private List<Coordinates> Coords;

    private static final int rowLength = 9;

    private static final int columnLength = 9;



    @Before
    public void init(){
        board= new Board(4);
        Coords = new ArrayList<Coordinates>();

    }
    @After
    public void end()
    {
       board = null;
       Coords = null;
    }

    @Test
    public void EmptyBoardShouldBeRefillable(){
        assertTrue(board.checkToRefill());
    }

    @Test
    public void ThisCoords5ShouldBeValid(){
        board.setSpace(4, 4, Tile.BOOKS);
        board.setSpace(3, 4, Tile.BOOKS);
        board.setSpace(5, 4, Tile.BOOKS);

        Coords.add(new Coordinates(4, 4));
        Coords.add(new Coordinates(3, 4));
        Coords.add(new Coordinates(5, 4));
        assertTrue(board.checkSelection(Coords));
    }
    @Test
    public void Board1ShouldBeRefillable() {
        board.setSpace(4, 2, Tile.BOOKS);
        board.setSpace(4, 4, Tile.BOOKS);
        assertTrue(board.checkToRefill());

    }
    @Test
    public void Board2ShouldNotBeRefillable() {
        board.setSpace(4, 2, Tile.BOOKS);
        board.setSpace(4, 3, Tile.CATS);
        assertFalse(board.checkToRefill());

    }
    @Test
    public void TheseCoords1ShouldBeValid(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.BOOKS);
        board.setSpace(4, 3, Tile.CATS);

        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 3));
        assertTrue(board.checkSelection(Coords));
    }

    @Test
    public void TheseCoords2ShouldNotBeValid(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.EMPTY);
        board.setSpace(4, 3, Tile.CATS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 3));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void TheseCoordsShouldNotBeValidBecauseAreTooMuch(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.EMPTY);
        board.setSpace(4, 3, Tile.CATS);
        board.setSpace(4, 4, Tile.CATS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 3));
        Coords.add(new Coordinates(4, 4));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void TheseCoordsShouldNotBeValidBecauseAreNotConsecutive(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(4, 4, Tile.CATS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 4));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void ThisCoordShouldBeValid(){
        board.setSpace(4, 1, Tile.BOOKS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        assertTrue(board.checkSelection(Coords));
    }


    @Test
    public void TheseCoords3ShouldNotBeValid(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(4, 4, Tile.CATS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 4));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void TheseCoords4ShouldNotBeValid(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(5, 4, Tile.CATS);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(5, 4));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void TheseCoordsShouldNotBeValidBecauseTilesAreNotFree(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(4, 3, Tile.CATS);
        board.setSpace(5, 2, Tile.TROPHIES);
        board.setSpace(3, 2, Tile.TROPHIES);

        List<Coordinates> Coords = new ArrayList<Coordinates>();
        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 3));
        assertFalse(board.checkSelection(Coords));
    }

    @Test
    public void RefillBoardShouldNotHaveEmptyTiles(){
        board.refill();
        assertTrue(checkNoEmptyTilesInBoard());

    }

    private boolean checkNoEmptyTilesInBoard(){
        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
               if(board.getSpaces()[i][j]==Tile.EMPTY)
                   return false;
            }
        return true;
    }

    @Test
    public void TilesPickedShouldBeBooksFramesCats(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(4, 3, Tile.CATS);

        List<Tile> list = new ArrayList<Tile>();
        list.add(Tile.BOOKS);
        list.add(Tile.FRAMES);
        list.add(Tile.CATS);

        Coords.add(new Coordinates(4, 1));
        Coords.add(new Coordinates(4, 2));
        Coords.add(new Coordinates(4, 3));

        assertTrue(board.selectTiles(Coords).equals(list));
    }

    @Test
    public void RefillNoEmptyBoardShouldBePossible(){
        board.setSpace(4, 1, Tile.BOOKS);
        board.setSpace(4, 2, Tile.FRAMES);
        board.setSpace(4, 3, Tile.CATS);
        board.setSpace(5, 2, Tile.TROPHIES);
        board.setSpace(3, 2, Tile.TROPHIES);

        board.refill();

        assertTrue(checkNoEmptyTilesInBoard());

    }
}