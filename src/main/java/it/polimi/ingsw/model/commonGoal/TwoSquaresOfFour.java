package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

/**
 * Two groups each containing 4 tiles of the same type in a 2x2 square.
 * The tiles of one square can be different from those of the other square.
 *
 * @author Abdullah Nasr
 */
public class TwoSquaresOfFour extends CommonGoal {

    private final int DIM_SQUARE = 2;
    private final int TIMES=2;

    private boolean isolatedSquare(Shelf shelf, Coordinates cords)
    {
        int x = cords.x();
        int y = cords.y();
        int row = shelf.getRow()-1;
        int col = shelf.getColumn()-1;
        Tile type = shelf.getPosition(cords);
        Tile checkType;


        for(int i=0;i<DIM_SQUARE;i++)
        {

            //check up
             if(x!=0)
             {
                 checkType = shelf.getPosition(new Coordinates(x-1,y+i));

                 if(checkType!=null && type==checkType)
                     return false;
             }

             //check down
             if(x+DIM_SQUARE-1!=row)
             {
                 checkType = shelf.getPosition(new Coordinates(x+DIM_SQUARE,y+i));

                 if(checkType!=null && type==checkType)
                     return false;
             }

            //check left
            if(y!=0)
            {
                checkType = shelf.getPosition(new Coordinates(x+i,y-1));

                if(checkType!=null && type==checkType)
                    return false;
            }

            //check right
            if(y+DIM_SQUARE-1!=col)
            {
                checkType = shelf.getPosition(new Coordinates(x+i,y+DIM_SQUARE));

                if(checkType!=null && type==checkType)
                    return false;
            }

        }

        return true;
    }
    private boolean checkSquareToBottomRight(Shelf shelf, Coordinates cords)
    {
        int x = cords.x();
        int y = cords.y();
        int row = shelf.getRow()-1;
        int col = shelf.getColumn()-1;

        //not out of the shelf
        if(y+DIM_SQUARE-1 <=  col && x+DIM_SQUARE-1 <= row )
        {
            for(int i=0;i<DIM_SQUARE;i++)
            {
                Tile currentTile;
                Tile nextTile;

                //check if the row have the same type
                for(int j=0;j<DIM_SQUARE-1;j++)
                {
                    currentTile = shelf.getPosition(new Coordinates(x+i,y+j));
                    nextTile = shelf.getPosition(new Coordinates(x+i,y+j+1));

                    if(currentTile==null || nextTile==null || currentTile!=nextTile)
                        return false;
                }

                //check if the current row is the same to the next row
                //it's enough to check the first tile of the row because the row check we did
                if(i!=DIM_SQUARE-1)
                {
                    currentTile = shelf.getPosition(new Coordinates(x+i,y));
                    nextTile = shelf.getPosition(new Coordinates(x+i+1,y));

                    if(currentTile==null || nextTile==null || currentTile!=nextTile)
                        return false;
                }
            }

            return true;
        }
        return false;
    }

    private int countIsolatedSquares(Shelf shelf)
    {
        int count=0;
        int row = shelf.getRow() -1;
        int col = shelf.getColumn() -1;
        Coordinates currentCoordinate;

       for(int x=0;x<=row;x++)
       {
           for(int y=0;y<=col;y++)
           {
                currentCoordinate = new Coordinates(x,y);

                if(checkSquareToBottomRight(shelf,currentCoordinate) && isolatedSquare(shelf, currentCoordinate))
                    count++;
           }
       }

       return count;
    }

    /**
     * <p>Implements abstract method of CommonGoal.</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Abdullah Nasr
     */
    @Override
    public boolean check(Shelf shelf) {

        if(countIsolatedSquares(shelf)>=TIMES)
            return true;

        return false;
    }

}
