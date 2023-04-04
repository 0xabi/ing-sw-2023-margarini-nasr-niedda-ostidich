package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 *
 * @author Abdullah Nasr
 */
public class TwoNoRepetitionRows extends CommonGoal {

    private final int NUM_DIFFERENT_TILES = 5;

    private final int TIMES = 2;

    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     * @param playerNumber is the number of player of the game match
     * @author Abdullah Nasr
     */
    public TwoNoRepetitionRows(int playerNumber) {
        super(playerNumber);
    }

    /**
     * //TODO java doc is to be written
     *
     * @param shelf
     * @param row
     * @return
     * @author Adullah Nasr
     */
    private boolean checkRow(@NotNull Shelf shelf, int row) {
        //FIXME warnings to be looked at
        ArrayList<Tile> checkedList = new ArrayList<>();
        int col = shelf.getColumnNumber();
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
     * @author Abdullah Nasr
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        //FIXME warnings to be looked at
        int count = 0;
        int row = shelf.getRowNumber();

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
