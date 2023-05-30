package it.polimi.ingsw;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GUIApp;
import it.polimi.ingsw.client.view.handler.SceneHandler;
import javafx.application.Application;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClientApp {

    public static void main(String @NotNull [] args) {
        if (args.length == 0) {
            args = new String[2];
            args[0] = null;
            args[1] = null;
        } else if (args.length == 1) {
            String old = args[0];
            args = new String[2];
            args[0] = old;
            args[1] = null;
        }
        Debugging.setNetworkType(args[1]);
        if (Objects.equals(args[0], "GUI")) {
            GUI gui = new GUI();
            SceneHandler.setGUI(gui);
            gui.start();
            Application.launch(GUIApp.class);
        } else new CLI();
    }

}
