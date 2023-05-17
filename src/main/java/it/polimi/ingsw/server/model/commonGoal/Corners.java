package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

/**
 * Four tiles of the same type in the four corners of the bookshelf.
 *
 * @author Pietro Andrea Niedda
 */
public class Corners extends CommonGoal {

    private static final String commonGoalName = "Corners";

    public Corners(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        Coordinates co1 = new Coordinates(0, 0);
        Coordinates co2 = new Coordinates(0, Shelf.getRowLength()-1);
        Coordinates co3 = new Coordinates(Shelf.getColumnLength()-1, 0);
        Coordinates co4 = new Coordinates(Shelf.getColumnLength()-1, Shelf.getRowLength()-1);

        if(shelf.getPosition(co1) != null)
            return (shelf.getPosition(co1) == shelf.getPosition(co2)) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co3))) &&
                    ((shelf.getPosition(co1) == shelf.getPosition(co4)));
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
