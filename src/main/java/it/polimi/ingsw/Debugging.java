package it.polimi.ingsw;

import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

public class Debugging {

    private static final boolean debugging = false;

    private static String generatedConnection;

    private static long time = 0;

    private static int startingPlayer;

    public static void checkStartingPlayer(boolean startingPlayer) {
        System.out.println("DEBUG: shelf trial log checking starting player");
        if (startingPlayer) Debugging.startingPlayer = Integer.parseInt(configuration);
        else if (configuration.equals("1")) Debugging.startingPlayer = 2;
        else Debugging.startingPlayer = 1;
    }

    private static boolean testTile;

    public static void checkTestTile(Tile testTile) {
        System.out.println("DEBUG: shelf trial log checking test tile");
        Debugging.testTile = testTile != null;
    }

    public static void calculateTime() {
        if (time == 0)
            time = System.nanoTime();
        else {
            long newTime = System.nanoTime();
            long difference = newTime - time;
            time = newTime;
            StringBuilder collectionTime = new StringBuilder();
            List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcBean : gcBeans) {
                collectionTime.append(gcBean.getCollectionTime()).append("\t");
                collectionTime.append(gcBean.getCollectionCount()).append("\t");
            }
            System.out.println("DEBUG: shelf trial log calculating time");
            String contentToAppend = "elapsed:\t" + difference + "  \t" + newTime + "\t|\tgc:\t" + collectionTime + "|\toutcome:\t";
            //noinspection DuplicatedCode
            String fullPath =  System.getenv("USERPROFILE")+"\\Documents\\GitHub\\MyShelfie\\src\\main\\resources\\shelfTrial.txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));
                writer.append(contentToAppend);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred while appending the string: " + e.getMessage());
            }
        }
    }


    public static void connectionStatistics() {
        System.out.println("DEBUG: shelf trial log writing");
        String contentToAppend = generatedConnection + testTile + "\t\t";
        //noinspection DuplicatedCode
        String fullPath = System.getenv("USERPROFILE")+"\\Documents\\GitHub\\MyShelfie\\src\\main\\resources\\shelfTrial.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));
            writer.append(contentToAppend);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the string: " + e.getMessage());
        }
    }

    public static void connectionStatisticsNewLine() {
        System.out.println("DEBUG: shelf trial log writing (new line)");
        String fullPath = System.getenv("USERPROFILE")+"\\Documents\\GitHub\\MyShelfie\\src\\main\\resources\\shelfTrial.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));
            writer.append("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the string: " + e.getMessage());
        }
    }

    public static void main(String @NotNull [] args) {
        if (args.length != 0) {
            ClientApp.main(new String[]{args[0], args[1]});
        } else {
            if (isDebugging()) connectionStatisticsNewLine();
            ServerApp.main(new String[]{});
        }
    }

    /**
     * No arguments: network types randomly assigned.<br>
     * Two arguments: arguments used as respective network types.
     */
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private static final String[] connections = {};

    public static String getConfiguration() {
        return configuration;
    }

    private static String roomGeneration;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isAlreadyPicket() {
        return alreadyPicket;
    }

    public static void flipAlreadyPicket() {
        alreadyPicket = !alreadyPicket;
        System.out.println("DEBUG: setting \"" + alreadyPicket + "\" as \"already picket\" boolean");
    }

    private static boolean alreadyPicket = false;

    private static String configuration;

    @SuppressWarnings("ConstantConditions")
    public static String getNetworkType() {
        if (connections.length == 0) {
            Random r = new Random();
            if (r.nextBoolean()) {
                System.out.println("DEBUG: returning \"" + "Socket" + "\" as network type");
                generatedConnection = "Socket\t";
                return "Socket";
            } else {
                System.out.println("DEBUG: returning \"" + "RMI" + "\" as network type");
                generatedConnection = "RMI\t\t";
                return "RMI";
            }
        } else if (configuration.equals("1")) {
            System.out.println("DEBUG: returning \"" + connections[0] + "\" as network type");
            if (connections[0].equals("Socket")) generatedConnection = "Socket\t";
            else generatedConnection = "RMI\t\t";
            return connections[0];
        } else {
            System.out.println("DEBUG: returning \"" + connections[1] + "\" as network type");
            if (connections[1].equals("Socket")) generatedConnection = "Socket\t";
            else generatedConnection = "RMI\t\t";
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
