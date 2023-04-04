package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Two columns each formed by 6 different types of tiles.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionColumns extends CommonGoal {

    private final int NUM_DIFFERENT_TILES = 6;

    private final int TIMES=2;

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     * @param playerNumber is the number of player of the game match
     * @author Abdullah Nasr
     */
    public TwoNoRepetitionColumns(int playerNumber) {
        super(playerNumber);
    }

    /**
     * //TODO java doc is to be written
     *
     * @param shelf
     * @param col
     * @return
     * @author Abdullah Nasr
     */
    private boolean checkCol(@NotNull Shelf shelf, int col) {
        //FIXME warnings to be looked at
        ArrayList<Tile> checkedList = new ArrayList<>();
        int row = shelf.getRowNumber();
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
     * @author Abdullah Nasr
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        //FIXME warnings to be looked at
        int count = 0;
        int col = shelf.getColumnNumber();

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
