package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionRows extends CommonGoal {

    private final int NUM_DIFFERENT_TILES = 5;
    private final int TIMES=2;

    private boolean checkRow(Shelf shelf, int row)
    {
        ArrayList<Tile> checkedList = new ArrayList<>();
        int col = shelf.getColumn();
        boolean different = true;
        int nDifferent =0;
        Tile current;

        for(int j=0;j<col;j++)
        {
            current = shelf.getPosition(new Coordinates(j,row));

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
        int row = shelf.getRow();

        //for each row
        for(int i=0;i<row;i++)
        {
            if(checkRow(shelf,i))
            {
                count++;

                if(count>=TIMES)
                    return true;
            }
        }

        return false;
    }

}
