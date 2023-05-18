package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * Five tiles of the same type forming an X.
 *
 * @author Francesco Ostidich
 */
public class FiveTilesX extends CommonGoal {

    private static final String commonGoalName = "FiveTilesX";

    public FiveTilesX(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        Tile[][] matrix = shelf.getPositions();
        for (int i = 1; i < Shelf.getRowLength() - 1; i++)
            for (int j = 1; j < Shelf.getColumnLength() - 1; j++) {
                if (matrix[i][j] != null &&
                        matrix[i][j] == matrix[i - 1][j - 1] &&
                        matrix[i][j] == matrix[i - 1][j + 1] &&
                        matrix[i][j] == matrix[i + 1][j - 1] &&
                        matrix[i][j] == matrix[i + 1][j + 1])
                    return true;
            }
        return false;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
