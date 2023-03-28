package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;

/**
 * Four tiles of the same type in the four corners of the bookshelf.
 *
 * @author Pietro Andrea Niedda
 */
public class Corners extends CommonGoal {

    @Override
    public boolean check(Shelf shelf) {

        Coordinates co1 = new Coordinates(0, 0);
        Coordinates co2 = new Coordinates(0, 5);
        Coordinates co3 = new Coordinates(6, 0);
        Coordinates co4 = new Coordinates(6, 5);

        if(shelf.getPosition(co1) != null)
            return (shelf.getPosition(co1) == shelf.getPosition(co2)) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co3))) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co4)));

        return false;

    }

}
