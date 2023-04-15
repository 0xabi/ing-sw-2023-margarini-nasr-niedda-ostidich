package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.HashSet;

/**
 * Eight tiles of the same type. There's no restriction about the position of these tiles.
 *
 * @author Pietro Andrea Niedda
 */
public class EightTilesNoPattern extends CommonGoal {

    private static final int sameTypeTiles = 8;

    private static final String commonGoalName = "EightTilesNoPattern";

    public EightTilesNoPattern(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        int count;
        Set<Tile> tiles = new HashSet<>();
        for(Tile tile: Tile.values())
            if(tile != null)
                tiles.add(tile);

        for(Tile tile: tiles) {
            count = 0;
            for(int i = 0; i < shelf.getColumnNumber(); i++)
                for(int j = 0; j < shelf.getRowNumber(); j++)
                    if(shelf.getPosition(new Coordinates(i, j)) != Tile.EMPTY &&
                            shelf.getPosition(new Coordinates(i, j)) == tile) count++;
            if(count >= sameTypeTiles) return true;
        }

        return false;
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public String getCommonGoalName() {
        return commonGoalName;
    }

}
