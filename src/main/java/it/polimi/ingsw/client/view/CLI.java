package it.polimi.ingsw.client.view;

import it.polimi.ingsw.Debugging;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.sun.javafx.collections.NonIterableChange;
import it.polimi.ingsw.resources.*;

import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Shelf;
import org.jetbrains.annotations.NotNull;

import static com.sun.javafx.collections.NonIterableChange.*;

/**
 * CLI class is to implement GameView UI abstract class.
 * Going in depth, methods called here print something and open a scanner to retrieve returnable information,
 * to be passed to the controller.
 * Checks to see if scanned data is valid is implemented here.
 *
 * @author Francesco Ostidich
 */
public class CLI extends GameClientView {

    private String chatMessage = "";

    private String dataMessage = "";

    private final Thread scannerThread;

    private String playerName;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public CLI(String network) {
        super(network);
        scannerThread = new Thread(this::scan);
        scannerThread.start();
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void start() {
        System.out.println("""

                ___  ___      _____ _          _  __ _     \s
                |  \\/  |     /  ___| |        | |/ _(_)    \s
                | .  . |_   _\\ `--.| |__   ___| | |_ _  ___\s
                | |\\/| | | | |`--. \\ '_ \\ / _ \\ |  _| |/ _ \\
                | |  | | |_| /\\__/ / | | |  __/ | | | |  __/
                \\_|  |_/\\__, \\____/|_| |_|\\___|_|_| |_|\\___|
                         __/ |                             \s
                        |___/                              \s

                Loading...\s"""
        );
        try {
            getClientController().update(new Event(EventID.START, null));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void chooseIPAddress() {
        if (Debugging.isDebugging()) {
            try {
                System.out.println("debugging: setting \"localhost\" as IP address");
                getClientController().update(new Event(EventID.CHOOSE_IP_ADDRESS, "localhost"));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String scannedIP = playerMessage("Choose IP address:");
            while (!InputFormatChecker.isIPAddress(scannedIP)) {
                scannedIP = playerMessage("Wrong input!\nChoose IP address:");
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_IP_ADDRESS, scannedIP));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void choosePlayerName() {
        if (Debugging.isDebugging()) {
            try {
                String randomString = Debugging.randomString();
                System.out.println("debugging: setting \"" + randomString + "\" as player name");
                getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, randomString));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String scannedIP = playerMessage("Choose player name:");
            while (scannedIP.isBlank()) {
                scannedIP = playerMessage("Wrong input!\nChoose player name:");
            }
            try {
                setPlayerName(scannedIP);
                getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, scannedIP));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewOrJoin() {
        if (Debugging.isDebugging()) {
            try {
                String phase = Debugging.getRoomGeneration();
                System.out.println("debugging: setting \"" + phase + "\" as room generation phase");
                getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, phase));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String answer = playerMessage("Type [new] to create new game or [join] to join an existing game:");
            while (!answer.equals("new") && !answer.equals("join")) {
                answer = playerMessage("Wrong input!\nType [new] to create new game or [join] to join an existing game:");
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, answer));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewGameName() {
        if (Debugging.isDebugging()) {
            try {
                System.out.println("debugging: setting \"game\" as game name");
                getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_NAME, "game"));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String gameName = playerMessage("Choose a new game name:");
            while (gameName.isBlank()) {
                gameName = playerMessage("Wrong input!\nChoose a new game name:");
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_NAME, gameName));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewGamePlayerNumber() {
        if (Debugging.isDebugging()) {
            try {
                System.out.println("debugging: setting \"2\" as game player number");
                getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_PLAYER_NUMBER, 2));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            Integer numPlayer;
            String input = playerMessage("Choose the number of players [max " + InputFormatChecker.getMaxPlayer() + " players" + "]:");
            numPlayer = InputFormatChecker.getNumFromString(input);
            while (numPlayer == null || numPlayer < 2 || numPlayer > InputFormatChecker.getMaxPlayer()) {
                input = playerMessage("Please insert a valid number of player!\nChoose the number of the player:");
                numPlayer = InputFormatChecker.getNumFromString(input);
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_PLAYER_NUMBER, numPlayer));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void showUpdatedGameRoom(String roomName) {
        GameRoom room = null;
        for (GameRoom gameRoom : getGameRooms()) {
            if (gameRoom.gameRoomName().equals(roomName)) {
                room = gameRoom;
                break;
            }
        }
        if (room == null) throw new RuntimeException("no such room name present while printing personal room!");
        System.out.println("Room name: " + room.gameRoomName());
        System.out.println("Creator: " + room.creatorName());
        System.out.println("Max players: " + room.totalPlayers());
        System.out.print("Entered players: \t");
        room.enteredPlayers().forEach(player -> System.out.println(player + "\n\t\t\t\t\t\t"));
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void notifyGameHasStared() {
        System.out.println("Game Started!");
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {
        if (Debugging.isDebugging()) {
            try {
                System.out.println("debugging: join \"game\" room");
                getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, "game"));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String gameRoomTable = InputFormatChecker.getTableGameRoom(rooms);
            String answer;
            answer = playerMessage(gameRoomTable + "\nType the room name: ");
            while (!InputFormatChecker.isGameRoomValid(answer, rooms)) {
                answer = playerMessage(gameRoomTable + "\nInvalid game room!\nType the room name: ");
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, answer));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void printBoard(ArrayList<Coordinates> list) {
        Tile[][] boxes = getBoard();
        System.out.println("\n--------------------------------------------------------------------------------------\n");
        System.out.print("\t\t\t\t");
        System.out.print("     ");
        boolean done = false;
        for (int i = 0; i < Board.getRowLength(); i++)
            System.out.print("-(" + i + ")-");
        System.out.println("---> X");
        for (int i = 0; i < Board.getColumnLength(); i++) {
            System.out.print("\t\t\t\t");
            System.out.print(" (" + i + ") ");
            for (int j = 0; j < Board.getRowLength(); j++) {
                if (boxes[j][i] == null)
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "     ");
                else if (boxes[j][i] == Tile.EMPTY) {
                    if (list == null || list.isEmpty())
                        System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[   ]");
                    else {
                        for (Coordinates coords : list)
                            if (coords.x() == j && coords.y() == i) {
                                System.out.print((char) 27 + "[31m" + (char) 27 + "[49m" + "[   ]");
                                done = true;
                                break;
                            }
                        if (!done)
                            System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[   ]");
                        done = false;
                    }
                } else {
                    if (list == null || list.isEmpty()) {
                        System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                        boxes[j][i].printColorForBoard();
                        System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                        done = true;
                    } else
                        for (Coordinates coords : list)
                            if (coords.x() == j && coords.y() == i) {
                                System.out.print((char) 27 + "[31m" + (char) 27 + "[49m" + "[");
                                boxes[j][i].printColorForBoard();
                                System.out.print((char) 27 + "[31m" + (char) 27 + "[49m" + "]");
                                done = true;
                                break;
                            }
                    if (!done) {
                        System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                        boxes[j][i].printColorForBoard();
                        System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                        done = true;
                    }
                    done = false;
                }
            }
            System.out.println();
        }
        System.out.println("\t\t\t\t  |\n\t\t\t\t  V Y\n--------------------------------------------------------------------------------------");
    }

    public void printBookshelves() {
        String Space;
        if (getNames().size() == 2)
            Space = "\t\t\t\t\t\t";
        else if (getNames().size() == 3)
            Space = "\t\t\t\t";
        else Space = "\t\t";
        System.out.print(Space);
        for (String name : getNames())
            System.out.print("\t" + name + "\t\t\t");
        System.out.println();
        for (int i = Shelf.getColumnLength() - 1; i >= 0; i--) {
            printShelfRow(i, Space);
            System.out.println();
        }
    }

    public void printShelfRow(int coloumn, String space) {
        System.out.print(space);
        for (String name : getNames()) {
            Tile[][] currentShelf = getPlayerShelves().get(name);
            for (int j = 0; j < Shelf.getRowLength(); j++) {
                if (currentShelf[j][coloumn] == null)
                    System.out.print("[ ]");
                else
                    System.out.print("[" + currentShelf[j][coloumn].toString().charAt(0) + "]");
            }
            System.out.print("\t\t");
        }
    }

    public void printCommonGoals() {
        System.out.println("\n--------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t\t\tCommon goal 1 : " + getCommonGoal1());
        System.out.println("\t\t\t\t\t\tCommon goal 2 : " + getCommonGoal2());
        System.out.println("--------------------------------------------------------------------------------------\n");
    }

    @Override
    public void pickTiles(int availablePickNumber) {
        int x, y;
        ArrayList<Coordinates> list = new ArrayList<>();
        String[] coords;
        boolean attempt;
        boolean retry = false;
        for (int i = 0; i < availablePickNumber; i++) {
            do {
                int print = availablePickNumber - i;
                attempt = true;
                try {
                    printCommonGoals();
                    printBookshelves();
                    printBoard(list);
                    if (retry)
                        System.out.println("\t\t\t\tThe Tile was alredy chosen or invalid input");
                    System.out.println("\t\t\t\tYou can pick more " + print + " tiles");
                    coords = playerMessage("\t\t\t\tchoose one tile using coordinates writing them in the following format: x,y\n").split(",");
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);
                    Coordinates temp = new Coordinates(x, y);
                    if (list.isEmpty())
                        list.add(temp);
                    else if (!list.contains(temp))
                        list.add(temp);
                    else {
                        attempt = false;
                        retry = true;
                    }
                } catch (Exception e) {
                    printCommonGoals();
                    printBookshelves();
                    printBoard(list);
                    attempt = false;
                    retry = true;
                    System.out.println("FORMAT ERROR" + e);
                }
            } while (!attempt);
            printCommonGoals();
            printBookshelves();
            printBoard(list);
            String msg = "\t\t\t\tIf you picked the tiles you wanted press [y],";
            msg = msg + "\n\t\t\t\tIf you're not satisfied of your choice and you would repick press [r],";
            msg = msg + "\n\t\t\t\tIf you want to pick other Tiles press[c]";
            String response = playerMessage(msg);
            if (response.equals("y"))
                break;
            if (response.equals("r")) {
                list.clear();
                i = -1;
            }
            if (i == availablePickNumber - 1 && !response.equals("r")) {
                printCommonGoals();
                printBookshelves();
                printBoard(list);
                System.out.println("\t\t\t\tyou have already chosen the maximum number of available tiles");
                do {
                    msg = "\t\t\t\tIf you picked the tiles you wanted press [y],";
                    msg = msg + "\n\t\t\t\tIf you're not satisfied of your choice and you would repick press [r],";
                    response = playerMessage(msg);
                } while (!response.equals("r") && !response.equals("y"));
                if (response.equals("r")) {
                    list.clear();
                    i = -1;
                } else break;
            }
        }
        try {
            System.out.println("\t\t\t\tYou chose the following coordinates:" + list);
            getClientController().update(new Event(EventID.PICK_TILES, list));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseOrder(@NotNull List<Tile> selection) { //13 rows + 2 di "-------"
        ArrayList<ArrayList<Tile>> resultSet = OrderChoice(selection);
        int choice = -1;
        do {
            try {
                printPossibleChoices(resultSet);
                printSingleShelf();
                choice = Integer.parseInt(playerMessage("Choose the order of your picked tiles:\t"));
            } catch (Exception e) {
                System.out.println("FORMAT ERROR" + e);
            }
        } while (choice >= resultSet.size() || choice < 0);
        try {
            getClientController().update(new Event(EventID.CHOOSE_ORDER, resultSet.get(choice)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseColumn() {
        int column = 0;
        boolean attempt;
        do {
            attempt = true;
            try {
                column = Integer.parseInt(playerMessage("Choose column: "));
            } catch (Exception e) {
                System.out.println("invalid input, try again");
                attempt = false;
            }
        } while (!attempt);
        try {
            getClientController().update(new Event(EventID.CHOOSE_COLUMN, column));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerIsPlaying(String playerName) {
        printCommonGoals();
        printBookshelves();
        printBoard(null);
        System.out.println("\t\t\t\t" + playerName + " is currently playing his turn");
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {
        System.out.println(playerName + " received " + token + " points from common goal");
    }

    @Override
    public void assignPersonalGoalPoints(@NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " received " + entry.getValue() + "points from Personal goal");
    }

    @Override
    public void assignAdjacentGoalPoints(@NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " received " + entry.getValue() + "points from adjacent tiles");
    }

    @Override
    public void announceWinner(String winnerName, @NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " has " + entry.getValue() + " points");
        System.out.println("The winner is: " + winnerName);
    }

    @Override
    public void disconnected() {
        System.out.println("Disconnected from server. Trying to reconnect...");
    }

    @Override
    public void justScanChat() {
        while (chatMessage == null) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        String temp = chatMessage;
        chatMessage = null;
        try {
            getClientController().update(new Event(EventID.JUST_SCAN_CHAT, temp));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void justPrintChat(@NotNull Queue<String> messages) {
        messages.forEach(System.out::println);
    }

    /**
     * Scanner is always open on separated thread.
     * If message written is of chat type it's written in chatMessage, otherwise in dataMessage.
     *
     * @author Francesco Ostidich
     */
    private void scan() {
        String prefix = "/msg ";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String temp = scanner.nextLine();
            if (temp.startsWith(prefix))
                chatMessage = temp.substring(prefix.length());
            else
                dataMessage = temp;
        }
    }

    /**
     * Method returns player inserted string (null if timout time is reached).
     *
     * @param timeOut      is the timeout number in second
     * @param inputMessage is the message to print posting the requested data
     * @return player's written data
     * @author Francesco Ostidich
     */
    private String playerMessage(String inputMessage, int timeOut) {
        int i = timeOut + 1;
        System.out.print(inputMessage + " ");
        dataMessage = null;
        while (dataMessage == null && i > 0) {
            try {
                //noinspection BusyWait
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            } finally {
                i--;
                System.out.print(i + " ");
            }
        }
        System.out.println();
        String temp = dataMessage;
        dataMessage = null;
        return temp;
    }

    /**
     * Method returns player inserted string (no timeout is waited).
     *
     * @param inputMessage is the message to print posting the requested data
     * @return player's written data
     * @author Francesco Ostidich
     */
    private String playerMessage(String inputMessage) {
        System.out.print(inputMessage + " ");
        dataMessage = null;
        while (dataMessage == null) {
            try {
                //noinspection BusyWait
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println();
        String temp = dataMessage;
        dataMessage = null;
        return temp;
    }

    private @NotNull ArrayList<ArrayList<Tile>> OrderChoice(List<Tile> list) {
        ArrayList<ArrayList<Tile>> result = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> temp = new ArrayList<>(list);
        if (list.size() == 1) {
            result.add(temp);
        } else if (list.size() == 2) {
            result.add(temp);
            temp = new ArrayList<>();
            temp.add(list.get(1));
            temp.add(list.get(0));
            if (!result.contains(temp))
                result.add(temp);
        } else if (list.size() == 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    ArrayList<Tile> help = new ArrayList<>(list);
                    temp = new ArrayList<>();
                    temp.add(list.get(i));
                    help.remove(i);
                    temp.add(help.get(j % 2));
                    temp.add(help.get((j + 1) % 2));
                    if (!result.contains(temp))
                        result.add(temp);
                }
            }
        } else {
            throw new RuntimeException("You picked too much tiles");
        }
        return result;
    }

    public void printPossibleChoices(@NotNull ArrayList<ArrayList<Tile>> choices) {
        System.out.println("-------------------------------------------------------------------------\n");
        for (int i = 0; i < choices.size(); i++) {
            for (int j = 0; j < 7 - choices.size(); j++)
                System.out.print("\t");
            System.out.print(" (" + i + ")");
        }
        System.out.println("\n");
        for (int i = 0; i < choices.get(0).size(); i++) {
            for (int num = 0; num < choices.size(); num++) {
                for (int j = 0; j < 7 - choices.size(); j++)
                    System.out.print("\t");
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                choices.get(num).get(i).printColorForBoard();
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
            }
            System.out.println();
        }
        System.out.println("\n-------------------------------------------------------------------------\n");
    }

    public void printSingleShelf() {
        for (int i = 0; i < Shelf.getRowLength(); i++)
            System.out.print(" (" + i + ") ");
        System.out.println();
        for (int i = Shelf.getColumnLength() - 1; i >= 0; i--) {
            for (int j = 0; j < Shelf.getRowLength(); j++) {
                if (getPlayerShelves().get(playerName)[j][i] == null)
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[   ]");
                else {
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                    getPlayerShelves().get(playerName)[j][i].printColorForBoard();
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                }
            }
            System.out.println();
        }
    }

}
