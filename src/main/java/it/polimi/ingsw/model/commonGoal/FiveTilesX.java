package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * Five tiles of the same type forming an X.
 *
 * @author Francesco Ostidich
 */
public class FiveTilesX extends CommonGoal {

    /**
     * <p>Implements abstract method of CommonGoal.</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Francesco Ostidich
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        Tile[][] matrix = shelf.getPositions();
        for(int i = 1; i < Shelf.getRowNumber(); i++)
            for(int j = 1; j < Shelf.getColumnNumber(); j++)
                if(matrix[i][j].equals(matrix[i-1][j-1]) &&
                        matrix[i][j].equals(matrix[i-1][j+1]) &&
                        matrix[i][j].equals(matrix[i+1][j-1]) &&
                        matrix[i][j].equals(matrix[i+1][j+1]))
                    return true;
        return false;
    }

}
