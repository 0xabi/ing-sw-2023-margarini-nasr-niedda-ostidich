package it.polimi.ingsw.server.model;

import org.jetbrains.annotations.NotNull;

/**
 * The end game token is given when the first player fills the shelf.
 * It is contained as an optional in Board.java, and tells whether the game is to end if not present.
 *
 * @author Francesco Ostidich
 */
public class EndGameToken {

    private static final int END_GAME_POINTS = 1;

    private final int endGamePoints;

    public EndGameToken() {
        this.endGamePoints = END_GAME_POINTS;
    }

    /**
     * It assigns end game token's points to the player who got the first shelf filled.
     *
     * @param player is the player to give points to
     * @author Francesco Ostidich
     */
    protected static void assignPoints(@NotNull Player player) {
        player.addPoints(getEndGamePoints());
    }

    /**
     * Getter for end game points
     *
     * @return end game token points number
     * @author Francesco Ostidich
     */
    public static int getEndGamePoints() {
        return END_GAME_POINTS;
    }

}
