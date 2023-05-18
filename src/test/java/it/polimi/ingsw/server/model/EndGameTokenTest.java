package it.polimi.ingsw.server.model;

import org.junit.Assert;
import org.junit.Test;

public class EndGameTokenTest {

    private final Player player = new Player("player", 1);

    private final EndGameToken egt = new EndGameToken();

    @Test
    public void assignPoints_ShouldAddToPlayer() {
        EndGameToken.assignPoints(player);
        Assert.assertEquals(player.getPoints(), EndGameToken.getEndGamePoints());
    }

}