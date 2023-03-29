package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoal.*;
import it.polimi.ingsw.model.exceptions.CommonGoalNotPresentException;
import java.util.Random;

/**
 * Used to get a random common Goal.
 *
 * @author Francesco Ostidich
 */
public class CommonGoalFactory {

    /**
     * Returns a random common goal.
     *
     * @return a random common goal
     * @throws CommonGoalNotPresentException if the random generated number does not belong to a respective common goal constructor
     * @author Francesco Ostidich
     */
    public static CommonGoal getCommonGoal() {
        Random random = new Random();
        int i = random.nextInt(1, 13);
        return switch (i) {
            case 1 -> new SixGroupsOfTwo();
            case 2 -> new FourGroupsOfFour();
            case 3 -> new Corners();
            case 4 -> new TwoSquaresOfFour();
            case 5 -> new ThreeColumnsOfSix();
            case 6 -> new EightTilesNoPattern();
            case 7 -> new OneDiagonalOfFive();
            case 8 -> new FourRowsOfFive();
            case 9 -> new TwoNoRepetitionColumns();
            case 10 -> new TwoNoRepetitionRows();
            case 11 -> new FiveTilesX();
            case 12 -> new Stair();
            default -> throw new CommonGoalNotPresentException("unexpected value -> " + i);
        };
    }

}
