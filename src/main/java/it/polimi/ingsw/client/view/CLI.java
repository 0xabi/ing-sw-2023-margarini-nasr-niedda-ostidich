package it.polimi.ingsw.client.view;

import it.polimi.ingsw.Debugging;
import it.polimi.ingsw.general.*;

import java.rmi.RemoteException;
import java.util.*;

import it.polimi.ingsw.server.model.Board;
import org.jetbrains.annotations.NotNull;
import it.polimi.ingsw.Debugging.Watch;

/**
 * CLI class is to implement GameView UI abstract class.
 * Going in depth, methods called here print something and open a scanner to retrieve returnable information,
 * to be passed to the controller.
 * Checks to see if scanned data is valid is implemented here.
 *
 * @author Francesco Ostidich
 */
public class CLI extends GameClientView {

    private String chatMessage;

    private String dataMessage;

    private String currentPlayer;

    private String playerName;

    Watch pickTilesReqToServer = new Watch("pickTilesReqToServer");

    Watch chooseOrderReqToServer = new Watch("chooseOrderReqToServer");
    Watch columnReqToServer = new Watch("columnReqToServer");

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public CLI() {
        super();
        Thread scannerThread = new Thread(this::scan);
        scannerThread.start();
        Thread chatThread = new Thread(this::justScanChat);
        chatThread.start();
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

                Loading...\s
                """
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
                System.out.println("DEBUG: setting \"localhost\" as IP address");
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
                playerName = Debugging.randomString();
                System.out.println("DEBUG: setting \"" + playerName + "\" as player name");
                setPlayerName(playerName);
                getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, playerName));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            playerName = playerMessage("Choose player name:");
            while (playerName.isBlank()) {
                playerName = playerMessage("Wrong input!\nChoose player name:");
            }
            try {
                setPlayerName(playerName);
                getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, playerName));
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
                getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, Debugging.getRoomGeneration()));
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
                System.out.println("DEBUG: setting \"game\" as room name");
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
                System.out.println("DEBUG: setting \"2\" as room size");
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

        System.out.print("Waiting for players... \t");
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
                System.out.println("DEBUG: choosing \"game\" room to join");
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

    /**
     * Prints the game board with tiles and their corresponding colors.
     *
     * @param list a list of Coordinates representing the highlighted cells
     *
     * @author Edoardo Margarini
     */

    public void printBoard(ArrayList<Coordinates> list) {
        Tile[][] boxes = getBoard();
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
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
                    }
                    done = false;
                }
            }
            printCommonGoalsInRow(i);
            if (i == 3)
                System.out.print("\t\t\t\t\t\tYour Personal Goal is:");
            if (i > 3)
                printPersonalGoal(i - 4);
            System.out.println();
        }
        System.out.print("\t\t\t\t  |  ");
        for (int i = 0; i < Board.getColumnLength(); i++)
            System.out.print("     ");
        printPersonalGoal(Board.getColumnLength() - 4);
        System.out.println("\n\t\t\t\t  V Y\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Prints the bookshelves of each player, including the current player's name highlighted.
     *
     * @author Edoardo Margarini
     */

    public void printBookshelves() {
        String Space;
        if (getNames().size() == 2)
            Space = "\t\t\t\t\t";
        else if (getNames().size() == 3)
            Space = "\t\t\t";
        else Space = "\t";
        System.out.print(Space + "\t");
        for (String name : getNames())
            if (name.equals(currentPlayer))
                System.out.print("\t\t" + (char) 27 + "[4m" + (char) 27 + "[49m" + (char) 27 + "[39m" + name + (char) 27 + "[0m" + "\t\t\t\t\t");
            else
                System.out.print("\t\t" + (char) 27 + "[49m" + (char) 27 + "[39m" + name + "\t\t\t\t\t");
        System.out.println();
        for (int i = getGameParameters().get("shelfColumnLength") - 1; i >= 0; i--) {
            printShelfRow(i , Space + "\t");
            System.out.println();
        }
        System.out.println();
        if (getCommonGoal1GivenPlayers().size() > 0) {
            System.out.print("Cg1" + Space);
            for (String name : getNames())
                if (getCommonGoal1GivenPlayers().containsValue(name)) {
                    System.out.print("\t");
                    getCommonGoal1GivenPlayers().keySet().forEach((k) -> {
                        if (getCommonGoal1GivenPlayers().get(k).equals(name))
                            System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[" + (char) 27 + "[41m" + (char) 27 + "[30m" + " " + getCommonGoal1GivenPlayers().get(k) + " " + (char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                    });
                    System.out.print("\t\t\t");
                } else System.out.print("\t\t\t\t\t");
            System.out.println();
        }
        if (getCommonGoal2GivenPlayers().size() > 0) {
            System.out.print("Cg2" + Space);
            for (String name : getNames())
                if (getCommonGoal2GivenPlayers().containsValue(name)) {
                    System.out.print("\t");
                    getCommonGoal2GivenPlayers().keySet().forEach((k) -> {
                        if (getCommonGoal2GivenPlayers().get(k).equals(name))
                            System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[" + (char) 27 + "[41m" + (char) 27 + "[30m" + " " + getCommonGoal2GivenPlayers().get(k) + " " + (char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                    });
                    System.out.print("\t\t\t");
                } else System.out.print("\t\t\t\t\t");
            System.out.println();
        }
    }

    /**
     * Prints a row of tiles from the bookshelves for a specific column.
     *
     * @param column the column index of the shelf to print
     * @param space  additional space for formatting purposes
     *
     * @author Edoardo Margarini
     */

    public void printShelfRow(int column, String space) {
        System.out.print(space);
        for (String name : getNames()) {
            Tile[][] currentShelf = getPlayerShelves().get(name);
            for (int j = 0; j < getGameParameters().get("shelfRowLength"); j++) {
                if (currentShelf[j][column] == null)
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[   ]");
                else {
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                    currentShelf[j][column].printColorForBoard();
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                }

            }
            System.out.print("\t\t");
        }
    }

    /**
     * Prints the common goals and their corresponding token stacks in a specific row.
     *
     * @param i the row index where the common goals are printed
     *
     * @author Edaardo Margarini
     */

    public void printCommonGoalsInRow(int i) {
        if (i == 1) {
            System.out.print("\t\t\t\t\t\tCommon goal 1 : " + getCommonGoal1());
            System.out.print("\t\t" + (char) 27 + "[49m" + (char) 27 + "[39m" + "[" + (char) 27 + "[41m" + (char) 27 + "[30m" + " " + getCommonGoal1TokenStack().peek() + " " + (char) 27 + "[49m" + (char) 27 + "[39m" + "]");
        }
        if (i == 2) {
            System.out.print("\t\t\t\t\t\tCommon goal 2 : " + getCommonGoal2());
            System.out.print("\t\t" + (char) 27 + "[49m" + (char) 27 + "[39m" + "[" + (char) 27 + "[41m" + (char) 27 + "[30m" + " " + getCommonGoal2TokenStack().peek() + " " + (char) 27 + "[49m" + (char) 27 + "[39m" + "]");
        }
    }

    @Override
    public void pickTiles(int availablePickNumber) {
        int x, y;
        ArrayList<Coordinates> list = new ArrayList<>();
        currentPlayer = playerName;
        String[] coords;
        boolean attempt;
        boolean retry = false;
        for (int i = 0; i < availablePickNumber; i++) {
            do {
                int print = availablePickNumber - i;
                attempt = true;
                try {
                    printScenario1(list);
                    if (retry)
                        System.out.println("\t\t\t\tThe Tile was already chosen or invalid input");
                    System.out.println("\t\t\t\tYou can pick more " + print + " tiles");
                    if (Debugging.isDebugging() && !Debugging.isAlreadyPicket()) {
                        if (Debugging.getConfiguration().equals("1")) {
                            System.out.println("DEBUG: picking tile \"3,1\"");
                            coords = "3,1".split(",");
                        } else {
                            System.out.println("DEBUG: picking tile \"4,4\"");
                            coords = "4,4".split(",");
                        }
                    } else {
                        coords = playerMessage("\t\t\t\tchoose one tile using coordinates writing them in the following format: x,y\n").split(",");
                    }
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
                    printScenario1(list);
                    attempt = false;
                    retry = true;
                    System.out.println("FORMAT ERROR" + e);
                }
            } while (!attempt);
            printScenario1(list);
            String msg = "\t\t\t\tIf you picked the tiles you wanted press [y],";
            msg = msg + "\n\t\t\t\tIf you're not satisfied of your choice and you would pick again press [r],";
            msg = msg + "\n\t\t\t\tIf you want to pick other Tiles press[c]";
            String response;
            if (Debugging.isDebugging() && !Debugging.isAlreadyPicket()) {
                response = "y";
                System.out.println("DEBUG: confirming selection with \"y\"");
            } else {
                response = playerMessage(msg);
            }
            if (response.equals("y"))
                break;
            if (response.equals("r")) {
                list.clear();
                i = -1;
            }
            if (i == availablePickNumber - 1) {
                printScenario1(list);
                System.out.println("\t\t\t\tyou have already chosen the maximum number of available tiles");
                do {
                    msg = "\t\t\t\tIf you picked the tiles you wanted press [y],";
                    msg = msg + "\n\t\t\t\tIf you're not satisfied of your choice and you would pick again press [r],";
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
            pickTilesReqToServer.start();
            getClientController().update(new Event(EventID.PICK_TILES, list));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseOrder(@NotNull List<Tile> selection) { //13 rows + 2 of "-------"
        pickTilesReqToServer.stop();
        ArrayList<ArrayList<Tile>> resultSet = OrderChoice(selection);
        int choice = -1;
        do {
            try {
                printScenario2(resultSet);
                if (resultSet.size() == 1)
                    choice = 0;
                else if (Debugging.isDebugging() && !Debugging.isAlreadyPicket()) {
                    choice = 0;
                    System.out.println("DEBUG: choosing order 0");
                } else {
                    choice = Integer.parseInt(playerMessage("Choose the order of your picked tiles:\t"));
                }
            } catch (Exception e) {
                System.out.println("FORMAT ERROR " + e);
            }
        } while (choice >= resultSet.size() || choice < 0);
        try {

            chooseOrderReqToServer.start();
            getClientController().update(new Event(EventID.CHOOSE_ORDER, resultSet.get(choice)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseColumn() {
        chooseOrderReqToServer.stop();
        int column = 0;
        boolean attempt;
        do {
            attempt = true;
            try {
                if (Debugging.isDebugging() && !Debugging.isAlreadyPicket()) {
                    column = 2;
                    System.out.println("DEBUG: choosing column 2");
                    Debugging.flipAlreadyPicket();
                } else {
                    column = Integer.parseInt(playerMessage("\t\t\t\tChoose column: "));
                    if(column < 0 || column > getGameParameters().get("shelfRowLength") - 1){
                        System.out.println("\t\t\t\tinvalid input, try again");
                        attempt = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("\t\t\t\tinvalid input, try again");
                attempt = false;
            }
        } while (!attempt);
        try {

            columnReqToServer.start();
            getClientController().update(new Event(EventID.CHOOSE_COLUMN, column));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerIsPlaying(String playerName) {
        if (Debugging.isDebugging() && Debugging.isAlreadyPicket()) {
            Debugging.checkTestTile(getPlayerShelves().get(getPlayerName())[0][0]);
            Debugging.connectionStatistics();
        }
        columnReqToServer.stop();
        currentPlayer = playerName;
        printScenario1(null);
        System.out.println("\t\t\t\t" + playerName + " is currently playing his turn\n");
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {
        System.out.println(playerName + " received " + token + " points from common goal");
    }

    @Override
    public void assignPersonalGoalPoints(@NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " received " + entry.getValue() + " points from personal goal");
    }

    @Override
    public void assignAdjacentGoalPoints(@NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " received " + entry.getValue() + " points from adjacent tiles");
    }

    @Override
    public void announceWinner(String winnerName, @NotNull Map<String, Integer> points) {
        for (Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " has " + entry.getValue() + " points");
        System.out.println("The winner is: " + winnerName);
    }

    @Override
    public void chooseRMIorSocket() {
        if (Debugging.isDebugging()) {
            try {
                getClientController().update(new Event(EventID.CHOOSE_RMI_OR_SOCKET, Debugging.getNetworkType()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            String scanned = playerMessage("Choose network type:");
            while (!InputFormatChecker.isNetworkType(scanned)) {
                scanned = playerMessage("Wrong input!\nChoose network type:");
            }
            try {
                getClientController().update(new Event(EventID.CHOOSE_RMI_OR_SOCKET, scanned));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void disconnected() {
        System.out.println("Disconnected from server. Trying to reconnect...");
    }

    @Override
    public void justScanChat() {
        //noinspection InfiniteLoopStatement
        while (true) {
            while (chatMessage == null) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(200);
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
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void justPrintChat() {
        System.out.println();
        getChatMessages().forEach(System.out::println);
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

    /**
     * Orders the given list of tiles in different permutations and returns a list of ordered tile combinations.
     *
     * @param list the list of tiles to be ordered
     * @return an ArrayList of ArrayLists of Tile objects representing the ordered tile combinations
     * @throws RuntimeException if the number of tiles in the list is not valid for ordering
     *
     * @author Edoardo Margarini
     */


    private @NotNull ArrayList<ArrayList<Tile>> OrderChoice(List<Tile> list) {
        ArrayList<ArrayList<Tile>> result = new ArrayList<>();
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

    /**
     * Prints the possible choices scenario, displaying the available choices for the player.
     * The choices are displayed in a grid format, with their corresponding indices.
     * Each choice is represented by a tile and its color is printed.
     *
     * @param choices The list of possible choices for the player.
     * @author Edoardo Margarini
     */
    public void printPossibleChoices(@NotNull ArrayList<ArrayList<Tile>> choices) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < choices.size(); i++) {
            System.out.print("\t\t\t");
            for (int j = 0; j < 7 - choices.size(); j++)
                System.out.print("\t");
            System.out.print(" (" + i + ")");
        }
        System.out.println();
        for (int i = choices.get(0).size() - 1; i >= 0; i--) {
            for (int num = 0; num < choices.size(); num++) {
                System.out.print("\t\t\t");
                for (int j = 0; j < 7 - choices.size(); j++)
                    System.out.print("\t");
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                choices.get(num).get(i).printColorForBoard();
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
            }
            System.out.println();
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    /**
     * Prints the single shelf scenario, including the player's personal goal and the contents of the shelf.
     * The personal goal and shelf contents are displayed row by row.
     * The shelf row indices and the player's personal goal are also printed.
     *
     * @author Edoardo Margarini
     */

    public void printSingleShelf() {
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < getGameParameters().get("shelfRowLength"); i++)
            System.out.print(" (" + i + ") ");
        System.out.println("\t\t\t\t\t\t\t\tYour Personal Goal is:");
        for (int i = getGameParameters().get("shelfColumnLength") - 1; i >= 0; i--) {
            System.out.print("\t\t\t\t\t");
            for (int j = 0; j < getGameParameters().get("shelfRowLength"); j++) {
                if (getPlayerShelves().get(getPlayerName())[j][i] == null)
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[   ]");
                else {
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                    getPlayerShelves().get(getPlayerName())[j][i].printColorForBoard();
                    System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
                }
            }
            printPersonalGoal(getGameParameters().get("shelfColumnLength") - 1 - i);
            System.out.println();
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    /**
     * Prints the game scenario where the bookshelves and the board are displayed.
     *
     * @param board the list of coordinates representing the board state
     * @author Edoardo Margarini
     */

    public void printScenario1(ArrayList<Coordinates> board) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        justPrintChat();
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        printBookshelves();
        printBoard(board);
    }

    /**
     * Prints the game scenario where the possible choices and the single shelf are displayed.
     *
     * @param choices the list of choices available to the player
     * @author Edoardo Margarini
     */

    public void printScenario2(ArrayList<ArrayList<Tile>> choices) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        printPossibleChoices(choices);
        printSingleShelf();
    }

    public void printPersonalGoal(int column) {
        Coordinates temp;
        System.out.print("\t\t\t\t\t\t\t\t");
        for (int j = 0; j < getGameParameters().get("shelfRowLength"); j++) {
            temp = new Coordinates(j, column);
            if (getMapPersonalGoal().get( temp ) == null)
                System.out.print((char)27 + "[49m" + (char) 27 + "[39m" + "[   ]");
            else if (getPlayerShelves().get(getPlayerName())[j][getGameParameters().get("shelfColumnLength") - column - 1] == getMapPersonalGoal().get(temp)) {
                System.out.print((char)27 + "[4m" + (char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                getMapPersonalGoal().get(temp).printColorForBoard();
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]" + (char) 27 + "[0m");
            } else {
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "[");
                getMapPersonalGoal().get(temp).printColorForBoard();
                System.out.print((char) 27 + "[49m" + (char) 27 + "[39m" + "]");
            }
        }
        System.out.print("\t\t");
    }

}
