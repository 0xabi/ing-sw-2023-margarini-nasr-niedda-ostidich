package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.commonGoal.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalTest {

    private final CommonGoal testCommonGoal = new CommonGoal(2) {
        @Override
        public boolean check(Shelf shelf) {
            return true;
        }

        @Override
        public String getCommonGoalName() {
            return "Test Common Goal";
        }
    };

    private final Player player = new Player("player", 1);

    @Test
    public void testGeneral() {
        Assert.assertEquals("Test Common Goal", testCommonGoal.getCommonGoalName());
        Assert.assertTrue(testCommonGoal.check(player.getShelf()));
        Integer token = testCommonGoal.getTokens().getTokenStack().peek();
        testCommonGoal.assignPoints(player);
        Assert.assertEquals(testCommonGoal.getGivenPlayers().get(token), player);
        Assert.assertNotEquals(testCommonGoal.getTokens().getTokenStack().peek(), token);
    }

    @Test
    public void testForGetterName() {
        int numberOfPlayers = 2;
        Set<CommonGoal> commonGoals = new HashSet<>();
        commonGoals.add(new SixGroupsOfTwo(numberOfPlayers));
        commonGoals.add(new FourGroupsOfFour(numberOfPlayers));
        commonGoals.add(new Corners(numberOfPlayers));
        commonGoals.add(new TwoSquaresOfFour(numberOfPlayers));
        commonGoals.add(new ThreeColumnsOfSix(numberOfPlayers));
        commonGoals.add(new EightTilesNoPattern(numberOfPlayers));
        commonGoals.add(new OneDiagonalOfFive(numberOfPlayers));
        commonGoals.add(new FourRowsOfFive(numberOfPlayers));
        commonGoals.add(new TwoNoRepetitionColumns(numberOfPlayers));
        commonGoals.add(new TwoNoRepetitionRows(numberOfPlayers));
        commonGoals.add(new FiveTilesX(numberOfPlayers));
        commonGoals.add(new Stair(numberOfPlayers));
        for (CommonGoal commonGoal : commonGoals) {
            Assert.assertNotNull(commonGoal.getCommonGoalName());
        }
    }

}