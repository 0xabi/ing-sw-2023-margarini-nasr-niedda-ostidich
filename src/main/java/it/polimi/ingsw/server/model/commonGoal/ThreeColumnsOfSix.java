package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Three columns each formed by 6 tiles of maximum three different types.
 * One column can show the same or a different combination of another column.
 *
 * @author Francesco Ostidich
 */
public class ThreeColumnsOfSix extends CommonGoal {

    private static final int linesNumber = 3;

    private static final int tilesTypes = 3;

    private static final String commonGoalName = "ThreeColumnsOfSix";

    public ThreeColumnsOfSix(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        Tile[][] matrix = shelf.getPositions().clone();
        Set<Tile> tiles = new HashSet<>();
        int count = 0;
        int present = 0;
        for (int i = 0; i < Shelf.getRowLength(); i++) {
            for (int j = 0; j < Shelf.getColumnLength(); j++) {
                if (matrix[i][j] != null) {
                    present++;
                    tiles.add(matrix[i][j]);
                }
            }
            if (tiles.size() <= tilesTypes && present == Shelf.getColumnLength()) count++;
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
