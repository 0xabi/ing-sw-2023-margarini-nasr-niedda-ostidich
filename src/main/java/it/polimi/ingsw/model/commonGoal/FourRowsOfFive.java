package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
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

    public FourRowsOfFive(int playerNumber) {
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
        for(int i = 0; i < shelf.getRowNumber(); i++) {
            for(int j = 0; j < shelf.getColumnNumber(); j++) {
                if(matrix[i][j] != null)
                    tiles.add(matrix[i][j]);
            }
            if(tiles.size()>=tilesTypes) count++;
            tiles.clear();
        }
        return count >= linesNumber;
    }

}
