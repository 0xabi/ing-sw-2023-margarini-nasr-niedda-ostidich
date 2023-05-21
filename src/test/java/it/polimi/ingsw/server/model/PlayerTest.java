package it.polimi.ingsw.server.model;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    private final Player kek = new Player("kek", 1);

    @Test
    public void addPoints() {
        Assert.assertEquals(0, kek.getPoints());
        kek.addPoints(18);
        Assert.assertEquals(18, kek.getPoints());
    }

}