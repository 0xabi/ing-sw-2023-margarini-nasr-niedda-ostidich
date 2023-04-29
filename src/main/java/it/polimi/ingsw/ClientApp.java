package it.polimi.ingsw;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClientApp {

    public static void main(String @NotNull [] args) {
        if(args.length == 0) {
            args = new String[2];
            args[0] = null;
            args[1] = null;
        }
        else if(args.length == 1) {
            String old = args[0];
            args = new String[2];
            args[0] = old;
            args[1] = null;
        }
        if(Objects.equals(args[0], "GUI")) new GUI(args[1]);
        else new CLI(args[1]);
    }

}
