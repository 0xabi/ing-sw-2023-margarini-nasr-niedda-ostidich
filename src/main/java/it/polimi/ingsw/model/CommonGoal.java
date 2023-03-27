package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoPlayerNumberException;

import java.util.HashMap;
import java.util.Map;

public abstract class CommonGoal {

    private final ScoringTokenStack tokens;

    private final Map<Integer, Player> givenPlayers;

    public CommonGoal() {
        throw new NoPlayerNumberException("no player number provided");
    }

    public CommonGoal(int playerNumber) {
        tokens = new ScoringTokenStack(playerNumber);
        givenPlayers = new HashMap<Integer, Player>();
    }

    public Map<Integer, Player> getGivenPlayers() {
        return givenPlayers;
    }

    public abstract boolean check(Shelf shelf);

    public void assignPoints(Player player) {
        player.addPoints(tokens.pop());
    }

}
