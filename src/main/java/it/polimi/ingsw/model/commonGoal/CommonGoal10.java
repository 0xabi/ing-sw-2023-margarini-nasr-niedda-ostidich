package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;

public class CommonGoal10 extends CommonGoal {

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
