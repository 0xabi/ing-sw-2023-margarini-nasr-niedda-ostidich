package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import java.util.Set;
import java.util.HashSet;

/**
 * Eight tiles of the same type. There's no restriction about the position of these tiles.
 *
 * @author Pietro Andrea Niedda
 */
public class EightTilesNoPattern extends CommonGoal {

    /**
     * <p>Implements abstract method of CommonGoal.</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(Shelf shelf) {

        int count;
        Set<Tile> tiles = new HashSet<>();
        tiles.add(Tile.CATS);
        tiles.add(Tile.BOOKS);
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.PLANTS);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);

        for(Tile tile : tiles) {
            count = 0;
            for(int i = 0; i < shelf.getRow(); i++)
                for(int j = 0; j < shelf.getColumn(); j++)
                    if(shelf.getPosition(new Coordinates(i, j)) == tile) count++;

            if(count >= 8) return true;
        }

        return false;
    }

}
