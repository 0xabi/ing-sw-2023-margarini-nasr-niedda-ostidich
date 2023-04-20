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

    /**
     * It assigns end game token's points to the player who got the first shelf filled.
     *
     * @param player is the player to give points to
     * @author Francesco Ostidich
     */
    protected static void assignPoints(@NotNull Player player) {
        player.addPoints(END_GAME_POINTS);
    }

    /**
     * Getter for end game points
     *
     * @author Francesco Ostidich
     * @return end game token points number
     */
    public int getEndGamePoints() {
        return END_GAME_POINTS;
    }
}
