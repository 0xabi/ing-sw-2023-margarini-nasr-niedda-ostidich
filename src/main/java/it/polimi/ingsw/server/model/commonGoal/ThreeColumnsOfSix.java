package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
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

    private static final int linesNumber = 4;

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
        Tile[][] matrix = shelf.getPositions();
        Set<Tile> tiles = new HashSet<>();
        int count = 0;
        for(int i = 0; i < Shelf.getColumnNumber(); i++) {
            for(int j = 0; j < Shelf.getRowNumber(); j++) {
                if(matrix[i][j] != null)
                    tiles.add(matrix[i][j]);
            }
            if(tiles.size() >= tilesTypes) count++;
            tiles.clear();
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
