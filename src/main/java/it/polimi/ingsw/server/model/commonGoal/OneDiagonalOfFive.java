package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * Five tiles of the same type forming a diagonal.
 *
 * @author Edoardo Margarini
 */
public class OneDiagonalOfFive extends CommonGoal {

    public OneDiagonalOfFive(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Edoardo Margarini
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        Tile[][] matrix = shelf.getPositions();
        int counter = 0;

        for(int i = 0; i < shelf.getColumnNumber()-1; i++)
            if(matrix[i][i]!=matrix[i+1][i+1] || matrix[i][i]==Tile.EMPTY) {
                counter++;
                break;
            }
        for(int i = 0; i < shelf.getColumnNumber()-1; i++)
            if(matrix[i+1][i]!=matrix[i+2][i+1]|| matrix[i+1][i]==Tile.EMPTY) {
                counter++;
                break;
            }
        for(int i =0; i < shelf.getColumnNumber()-1;i++)
            if(matrix[shelf.getRowNumber()-1-i][i]!=matrix[shelf.getRowNumber()-2-i][i+1] ||
                    matrix[shelf.getRowNumber()-1-i][i]==Tile.EMPTY) {
                counter++;
                break;
            }
        for(int i =0; i < shelf.getColumnNumber()-1;i++)
            if(matrix[shelf.getRowNumber()-2-i][i]!=matrix[shelf.getRowNumber()-1-i][i+1] ||
                    matrix[shelf.getRowNumber()-2-i][i]==Tile.EMPTY) {
                counter++;
                break;
            }

        return counter < 4;
    }

}
