package it.polimi.ingsw;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Debugging {

    private static final boolean debugging = true;

    /**
     * No arguments: network types randomly assigned.<br>
     * Two arguments: arguments used as respective network types.
     */
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static final String[] connections = {};

    private static String roomGeneration;

    private static String configuration;

    @SuppressWarnings("ConstantConditions")
    public static String getNetworkType() {
        if (connections.length == 0) {
            Random r = new Random();
            if (r.nextBoolean()) {
                System.out.println("DEBUG: returning \"" + "Socket" + "\" as network type");
                return "Socket";
            } else {
                System.out.println("DEBUG: returning \"" + "RMI" + "\" as network type");
                return "RMI";
            }
        } else if (configuration.equals("1")) {
            System.out.println("DEBUG: returning \"" + connections[0] + "\" as network type");
            return connections[0];
        } else {
            System.out.println("DEBUG: returning \"" + connections[1] + "\" as network type");
            return connections[1];
        }
    }

    public static void setConfiguration(String configuration) {
        Debugging.configuration = configuration;
        System.out.println("DEBUG: setting \"" + configuration + "\" as configuration number");
    }

    public static boolean isDebugging() {
        return debugging;
    }

    public static @NotNull String getRoomGeneration() {
        if (configuration.equals("1")) {
            System.out.println("DEBUG: setting \"" + "new" + "\" as room generation phase");
            return "new";
        } else {
            System.out.println("DEBUG: setting \"" + "join" + "\" as room generation phase");
            return "join";
        }
    }

    public static void fillShelf(Shelf shelf) {
        Random random = new Random();
        List<Tile> tiles = new LinkedList<>(Arrays.stream(Tile.values()).toList());
        tiles.remove(Tile.EMPTY);
        for (int i = 0; i < Shelf.getRowLength(); ++i) {
            for (int j = 0; j < Shelf.getColumnLength() - 1; j++) {
                shelf.getPositions()[i][j] = tiles.get(random.nextInt(tiles.size()));
            }
        }
        shelf.getPositions()[3][Shelf.getColumnLength() - 1] = shelf.getPositions()[0][Shelf.getColumnLength() - 2];
        shelf.getPositions()[4][Shelf.getColumnLength() - 1] = shelf.getPositions()[0][Shelf.getColumnLength() - 3];
        shelf.getPositions()[0][Shelf.getColumnLength() - 2] = null;
        shelf.getPositions()[0][Shelf.getColumnLength() - 3] = null;
        shelf.getPositions()[1][Shelf.getColumnLength() - 2] = null;
        System.out.println("DEBUG: filling shelves");
    }

    public static @NotNull String randomString() {
        Random random = new Random();
        return random.ints(97, 123)
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static void clearBoard(@NotNull Board board) {
        for (int i = 0; i < Board.getRowLength(); i++)
            for (int j = 0; j < Board.getColumnLength(); j++)
                if (board.getSpaces()[i][j] != Tile.EMPTY && board.getSpaces()[i][j] != null)
                    board.getSpaces()[i][j] = Tile.EMPTY;
        board.getSpaces()[3][1] = Tile.FRAMES;
        board.getSpaces()[4][1] = Tile.GAMES;
        board.getSpaces()[4][5] = Tile.TROPHIES;
        board.getSpaces()[4][4] = Tile.CATS;
        System.out.println("DEBUG: clearing board");
    }

}
