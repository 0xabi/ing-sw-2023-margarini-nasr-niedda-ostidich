package it.polimi.ingsw.server.model;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>The class is abstract and have concrete common goals that inherits itself, overriding check method.</p>
 * <p>If check method returns true, points are assigned to the player, popping points from the scoring token stack.
 * A map remembers who has already taken the points, in order to not call the check method again on him.
 * Different player number's game have different scoring token stacks.</p>
 *
 * @author Francesco Ostidich
 */
public abstract class CommonGoal {

    private final ScoringTokenStack tokens;

    private final Map<Integer, Player> givenPlayers;

    /**
     * <p>Class constructor.</p>
     * <p>It generates tokens stack and given players map.</p>
     *
     * @param playerNumber is the number of player of the game match
     * @author Francesco Ostidich
     */
    public CommonGoal(int playerNumber) {
        tokens = new ScoringTokenStack(playerNumber);
        givenPlayers = new HashMap<>();
    }

    /**
     * Getter for given players map.
     *
     * @return given players map
     * @author Francesco Ostidich
     */
    protected Map<Integer, Player> getGivenPlayers() {
        return givenPlayers;
    }

    /**
     * <p>Common goals implemented specifically in concrete subclasses</p>
     * <p>Checks if player's shelf got the common goal.</p>
     *
     * @param shelf is the players shelf to check to
     * @return boolean true if check succeeds.
     * @author Francesco Ostidich
     */
    public abstract boolean check(Shelf shelf);

    /**
     * Assigns points to the player if check is true.
     *
     * @param player is the player to assign points to
     * @author Francesco Ostidich
     */
    protected void assignPoints(@NotNull Player player) {
        int token = tokens.pop();
        player.addPoints(token);
        givenPlayers.put(token, player);
    }

    /**
     * Getter for common goal name.
     *
     * @return name string
     * @author Francesco Ostidich
     */
    public abstract String getCommonGoalName();

    /**
     * Getter for common goal scoring token stack.
     *
     * @return token points stack
     * @author Francesco Ostidich
     */
    public ScoringTokenStack getTokens() {
        return tokens;
    }

    /**
     * check if another common goal object is the same to this common goal
     * @param cg the common goal in which to check if is equal
     * @return true if the common goal have the same name of the common goal passed by parameter, false otherwise
     * @author Abdullah Nasr
     */
    public boolean isEqual(CommonGoal cg) {
       return cg.getCommonGoalName().equals(getCommonGoalName());
    }
}
