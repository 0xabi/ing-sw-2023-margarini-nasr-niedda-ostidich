package it.polimi.ingsw.resources.interfaces;

/**
 * Action doable from network to controller.
 *
 * @author Francesco Ostidich
 */
public interface ClientNetworkToController {

    /**
     * When game ends, before closing connection, game is notified as ended.
     *
     * @author Francesco Ostidich
     */
    void endGame();

}
