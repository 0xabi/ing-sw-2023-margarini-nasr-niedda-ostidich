package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import java.util.Set;
import java.util.HashSet;

public class CommonGoal6 extends CommonGoal {

    @Override
    public boolean check(Shelf shelf) {

        int count;
        Set<Tile>  tiles = new HashSet<>();
        tiles.add( Tile.CATS);
        tiles.add(Tile.BOOKS);
        tiles.add(Tile.FRAMES);
        tiles.add(Tile.PLANTS);
        tiles.add(Tile.TROPHIES);
        tiles.add(Tile.GAMES);

        for(Tile tile : tiles){
            count = 0;
            for(int i = 0; i < shelf.getRow(); i++)
                for(int j = 0; j < shelf.getColumn(); j++)
                    if(shelf.getTile(i, j) == tile) count++;

            if(count >= 8) return true;
        }

        return false;
    }

}
