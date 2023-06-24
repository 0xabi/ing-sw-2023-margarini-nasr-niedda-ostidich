package it.polimi.ingsw.server.model;

import org.junit.Assert;
import org.junit.Test;

public class ScoringTokenStackTest {

    @Test
    public void getter() {
        ScoringTokenStack stack = new ScoringTokenStack(2);
        Assert.assertEquals(3, stack.getTokenStack().size());
    }

}