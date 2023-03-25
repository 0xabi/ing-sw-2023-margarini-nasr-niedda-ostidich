package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;

public class CommonGoal3 extends CommonGoal {

    @Override
    public boolean check(Shelf shelf) {

        Coordinate co1 = new Coordinate(0, 0);
        Coordinate co2 = new Coordinate(0, 5);
        Coordinate co3 = new Coordinate(6, 0);
        Coordinate co4 = new Coordinate(6, 5);

        if(shelf.getPosition(co1) != Tile.EMPTY)
            if((shelf.getPosition(co1) == shelf.getPosition(co2)) && ((shelf.getPosition(co1) == shelf.getPosition(co3))) && ((shelf.getPosition(co1) == shelf.getPosition(co4))))
                return true;

        return false;

    }

}
