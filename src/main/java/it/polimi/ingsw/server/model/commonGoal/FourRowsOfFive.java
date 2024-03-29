package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Four lines each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 *
 * @author Francesco Ostidich
 */
public class FourRowsOfFive extends CommonGoal {

    private static final int linesNumber = 4;

    private static final int tilesTypes = 3;

    private static final String commonGoalName = "FourRowsOfFive";

    public FourRowsOfFive(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        Tile[][] matrix = shelf.cloneShelf().getPositions();
        Set<Tile> tiles = new HashSet<>();
        int count = 0;
        int present = 0;
        for (int j = 0; j < Shelf.getColumnLength(); j++) {
            for (int i = 0; i < Shelf.getRowLength(); i++) {
                if (matrix[i][j] != null) {
                    present++;
                    tiles.add(matrix[i][j]);
                }
            }
            if (tiles.size() <= tilesTypes && present == Shelf.getRowLength()) count++;
            tiles.clear();
            present = 0;
        }
        return count >= linesNumber;
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
