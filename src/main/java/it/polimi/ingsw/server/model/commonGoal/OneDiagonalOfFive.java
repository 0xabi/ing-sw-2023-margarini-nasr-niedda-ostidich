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

    private static final String commonGoalName = "OneDiagonalOfFive";

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
        int rowLength = Shelf.getRowLength();
        int colLength = Shelf.getColumnLength();
        for (int i = 0; i < rowLength - 4; i++) {
            for (int j = 0; j < colLength - 4; j++) {
                Tile current = matrix[i][j];
                if (current == null) continue;
                if (current == matrix[i + 1][j + 1] &&
                        current == matrix[i + 2][j + 2] &&
                        current == matrix[i + 3][j + 3] &&
                        current == matrix[i + 4][j + 4]) {
                    return true;
                }
            }
        }
        for (int i = 4; i < rowLength; i++) {
            for (int j = 0; j < colLength - 4; j++) {
                Tile current = matrix[i][j];
                if (current == null) continue;
                if (current == matrix[i - 1][j + 1] &&
                        current == matrix[i - 2][j + 2] &&
                        current == matrix[i - 3][j + 3] &&
                        current == matrix[i - 4][j + 4]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author Edoardo Margarini
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
