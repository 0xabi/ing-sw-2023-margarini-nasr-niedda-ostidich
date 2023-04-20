package it.polimi.ingsw.client.clientController;

import it.polimi.ingsw.client.networkHandler.GameNetworkHandler;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

import java.util.Scanner;

/**
 * The game view controller is to receive messages from the network handler, and consequentially call methods on the view.
 * On the other side it has to retrieve events and data from the view and package, in order to be sent to the server controller
 * to be processed.
 *
 * @author Francesco Ostidich
 */
public class GameViewController {

    private static ViewAccess viewAccess;

    private static GameNetworkHandler gameNetworkHandler;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("GUI or CLI? ");
        String UI = scanner.nextLine();
        System.out.println();
        System.out.println("RMI or Socket? ");
        String Network = scanner.nextLine();
        System.out.println();
        if(UI.equals("GUI"))
            viewAccess = new ViewAccess(new GUI());
        else
            viewAccess = new ViewAccess(new CLI());
        if(Network.equals("RMI"))
            do {
                lobbyActions();
                gameNetworkHandler.connectionRMIToServer();
            } while (!viewAccess.waitForEndGameAction().equals("quit"));
        else
            do {
                lobbyActions();
                gameNetworkHandler.connectionSocketToServer();
                viewAccess.waitForEndGameAction();
            } while (viewAccess.waitForEndGameAction().equals("quit"));
        System.out.println("Closing...");
    }

    /**
     * Defines all actions needed from the player before connecting to the server.
     *
     * @author Francesco Ostidich
     */
    private static void lobbyActions() {
        viewAccess.start();
        String IP = viewAccess.chooseIPAddress();
        String playerName = viewAccess.choosePlayerName();
        gameNetworkHandler = new GameNetworkHandler(IP, playerName, viewAccess);
    }

}
