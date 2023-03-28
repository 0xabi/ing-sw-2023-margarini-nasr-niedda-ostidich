package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;

/**
 * Two columns each formed by 6 different types of tiles.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionColumns extends CommonGoal {

    private final int NUM_DIFFERENT_TILES = 6;
    private final int TIMES=2;
    private boolean checkCol(Shelf shelf, int col)
    {
        ArrayList<Tile> checkedList = new ArrayList<>();
        int row = shelf.getRow();
        boolean different = true;
        int nDifferent =0;
        Tile current;


        for(int j=0;j<row;j++)
        {
            current = shelf.getPosition(new Coordinates(j,col));

            if(current!=null)
            {
                for (Tile t: checkedList)
                {
                    if(t==current)
                        different=false;
                }

                if(different)
                {
                    nDifferent++;
                    checkedList.add(current);

                    if(nDifferent>=NUM_DIFFERENT_TILES)
                        return true;

                }

            }
        }
        return false;
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

        int count = 0;
        int col = shelf.getColumn();

        //for each column
        for(int i=0;i<col;i++)
        {
            if(checkCol(shelf,i))
            {
                count++;

                if(count>=TIMES)
                    return true;
            }
        }

        return false;
    }

}
