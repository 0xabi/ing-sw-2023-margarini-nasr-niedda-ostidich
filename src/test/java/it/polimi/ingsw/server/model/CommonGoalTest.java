package it.polimi.ingsw.server.model;

import org.junit.Assert;
import org.junit.Test;

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

}