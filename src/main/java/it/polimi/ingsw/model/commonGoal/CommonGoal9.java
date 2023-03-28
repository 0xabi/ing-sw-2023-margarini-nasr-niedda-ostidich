package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;

public class CommonGoal9 extends CommonGoal {

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
