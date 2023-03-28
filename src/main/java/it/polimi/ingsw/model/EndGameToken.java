package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

/**
 * The end game token is given when the first player fills the shelf.
 * It is contained as an optional in Board.java, and tells whether the game is to end if not present.
 *
 * @author Francesco Ostidich
 */
public class EndGameToken {

    private static final int endGamePoints = 1;

    /**
     * Getter method to get end game points constant.
     *
     * @return end game points value
     * @author Francesco Ostidich
     */
    public static int getEndGamePoints() {
        return endGamePoints;
    }

    /**
     * It assigns end game token's points to the player who got the first shelf filled.
     *
     * @param player is the player to give points to
     * @author Francesco Ostidich
     */
    public void assignPoints(@NotNull Player player) {
        player.addPoints(endGamePoints);
    }

}
