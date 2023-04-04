package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import org.jetbrains.annotations.NotNull;

/**
 * Four tiles of the same type in the four corners of the bookshelf.
 *
 * @author Pietro Andrea Niedda
 */
public class Corners extends CommonGoal {

    public Corners(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        Coordinates co1 = new Coordinates(0, 0);
        Coordinates co2 = new Coordinates(0, shelf.getRowNumber()-1);
        Coordinates co3 = new Coordinates(shelf.getColumnNumber()-1, 0);
        Coordinates co4 = new Coordinates(shelf.getColumnNumber()-1, shelf.getRowNumber()-1);

        if(shelf.getPosition(co1) != null)
            return (shelf.getPosition(co1) == shelf.getPosition(co2)) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co3))) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co4)));
        return false;

    }

}
