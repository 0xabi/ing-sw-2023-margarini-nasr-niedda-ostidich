package it.polimi.ingsw.resources.interfaces;

import it.polimi.ingsw.server.model.Board;

/**
 * Defines all the actions doable on the view.
 *
 * @author Francesco Ostidich
 */
public interface ViewActions {

    /**
     * Notifies the view to show the board.
     *
     * @param board is the board to be shown
     * @author Francesco Ostidich
     */
    void displayBoard(Board board);

}
