package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonGoal {

    private static final Set<CommonGoal> alreadyChosen = new HashSet<CommonGoal>();

    private final ScoringTokenStack tokens;

    private final Map<Integer, Player> givenPlayers;

    public CommonGoal() {
        throw new RuntimeException("No player number provided!");
    }

    public CommonGoal(int playerNumber) {
        tokens = new ScoringTokenStack(playerNumber);
        givenPlayers = new HashMap<Integer, Player>();
    }

    public Map<Integer, Player> getGivenPlayers() {
        return givenPlayers;
    }

    public boolean check(Shelf shelf) {
        //check
        return false;
    }

    public void assignPoints(Player player) {
        player.addPoints(tokens.pop());
    }

}
