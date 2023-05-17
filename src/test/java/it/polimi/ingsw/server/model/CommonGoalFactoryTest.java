package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.commonGoal.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalFactoryTest {

    @Test
    public void getCommonGoal_shouldReturnAllCommonGoals() {
        int numberOfPlayers = 2;
        Set<CommonGoal> expectedCommonGoals = new HashSet<>();
        expectedCommonGoals.add(new SixGroupsOfTwo(numberOfPlayers));
        expectedCommonGoals.add(new FourGroupsOfFour(numberOfPlayers));
        expectedCommonGoals.add(new Corners(numberOfPlayers));
        expectedCommonGoals.add(new TwoSquaresOfFour(numberOfPlayers));
        expectedCommonGoals.add(new ThreeColumnsOfSix(numberOfPlayers));
        expectedCommonGoals.add(new EightTilesNoPattern(numberOfPlayers));
        expectedCommonGoals.add(new OneDiagonalOfFive(numberOfPlayers));
        expectedCommonGoals.add(new FourRowsOfFive(numberOfPlayers));
        expectedCommonGoals.add(new TwoNoRepetitionColumns(numberOfPlayers));
        expectedCommonGoals.add(new TwoNoRepetitionRows(numberOfPlayers));
        expectedCommonGoals.add(new FiveTilesX(numberOfPlayers));
        expectedCommonGoals.add(new Stair(numberOfPlayers));
        Set<CommonGoal> actualCommonGoals = new HashSet<>();
        int count = 0;
        while (actualCommonGoals.size() < expectedCommonGoals.size()) {
            actualCommonGoals.add(CommonGoalFactory.getCommonGoal(numberOfPlayers));
        }
        for (CommonGoal commonGoal1 : expectedCommonGoals) {
            for (CommonGoal commonGoal2 : actualCommonGoals) {
                if (commonGoal1.getClass() == commonGoal2.getClass()) ++count;
            }
        }
        Assert.assertEquals(count, expectedCommonGoals.size());
    }

}