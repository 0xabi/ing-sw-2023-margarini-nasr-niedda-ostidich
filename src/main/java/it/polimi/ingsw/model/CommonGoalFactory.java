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

    private static final int commonGoalNumber = 12;

    /**
     * Getter for common goal number.
     * @return common goal number constant
     * @author Francesco Ostidich
     */
    public static int getCommonGoalNumber() {
        return commonGoalNumber;
    }

    /**
     * Returns a random common goal.
     *
     * @return a random common goal
     * @throws CommonGoalNotPresentException if the random generated number does not belong to a respective common goal constructor
     * @author Francesco Ostidich
     */
    public static CommonGoal getCommonGoal(int playerNumber) {
        Random random = new Random();
        int i = random.nextInt(1, 13);
        return switch (i) {
            case 1 -> new SixGroupsOfTwo(playerNumber);
            case 2 -> new FourGroupsOfFour(playerNumber);
            case 3 -> new Corners(playerNumber);
            case 4 -> new TwoSquaresOfFour(playerNumber);
            case 5 -> new ThreeColumnsOfSix(playerNumber);
            case 6 -> new EightTilesNoPattern(playerNumber);
            case 7 -> new OneDiagonalOfFive(playerNumber);
            case 8 -> new FourRowsOfFive(playerNumber);
            case 9 -> new TwoNoRepetitionColumns(playerNumber);
            case 10 -> new TwoNoRepetitionRows(playerNumber);
            case 11 -> new FiveTilesX(playerNumber);
            case 12 -> new Stair(playerNumber);
            default -> throw new CommonGoalNotPresentException("unexpected value -> " + i);
        };
    }

}
