package it.polimi.ingsw;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Debugging {

    private static final boolean debugging = true;

    private static String roomGeneration;

    private static String networkType;

    public static String getNetworkType() {
        return networkType;
    }

    public static void setNetworkType(String networkType) {
        Debugging.networkType = networkType;
    }

    public static boolean isDebugging() {
        return debugging;
    }

    public static void setRoomGeneration(@NotNull String roomGeneration) {
        if (roomGeneration.equals("Socket")) Debugging.roomGeneration = "new";
        else Debugging.roomGeneration = "join";
    }

    public static String getRoomGeneration() {
        return roomGeneration;
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
        shelf.getPositions()[0][Shelf.getColumnLength()-2] = null;
        shelf.getPositions()[0][Shelf.getColumnLength()-3] = null;
        shelf.getPositions()[1][Shelf.getColumnLength()-2] = null;
    }

    public static @NotNull String randomString() {
        Random random = new Random();
        return random.ints(97, 123)
                .limit(7)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
