package it.polimi.ingsw.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BagTest{

    Board board;

    @Before
    public void init(){
        board= new Board(4);

    }
    @After
    public void end()
    {
        board = null;
    }

    @Test
    public void bagSizeAfterRefillOfBoard4ShouldBe87(){
        board.refill();
        assertEquals(22*6-45,board.getBag().size());
    }

    @Test
    public void bagSizeAfterDrawShouldBe127(){
        board.setSpace(new Coordinates(4, 1), board.getBag().draw());
        board.setSpace(new Coordinates(4, 2), board.getBag().draw());
        board.setSpace(new Coordinates(4, 3), board.getBag().draw());
        board.setSpace(new Coordinates(5, 2), board.getBag().draw());
        board.setSpace(new Coordinates(3, 2), board.getBag().draw());
        assertEquals(22*6-5,board.getBag().size());
    }
}