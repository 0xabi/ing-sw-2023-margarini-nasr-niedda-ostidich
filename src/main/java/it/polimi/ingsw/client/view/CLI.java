package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public CLI(String network) {
        super(network);
        scannerThread = new Thread(this::scan);
        scannerThread.start();
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




                Loading...""");
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void chooseIPAddress() {
        String scannedIP = playerMessage("Choose IP address:");
        while(!InputFormatChecker.isIPAddress(scannedIP)) {
            scannedIP = playerMessage("Wrong input!\nChoose IP address:");
        }
        getClientController().update(new Event(EventID.CHOOSE_IP_ADDRESS, scannedIP));
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void choosePlayerName() {
        String scannedIP = playerMessage("Choose player name:");
        while(scannedIP.isBlank()) {
            scannedIP = playerMessage("Wrong input!\nChoose player name:");
        }
        getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, scannedIP));
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewOrJoin() {
        String answer = playerMessage("Type [new] to create new game or [join] to join an existing game:");
        answer = answer.trim().toLowerCase();

        while(!answer.equals("new") && !answer.equals("join"))
        {
            answer = playerMessage("Wrong input!\nType [new] to create new game or [join] to join an existing game:");
            answer=answer.trim().toLowerCase();
        }

        getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, answer));
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewGameName() {
        String gameName = playerMessage("Choose a new game name:");
        while(gameName.isBlank())
        {
            gameName = playerMessage("Wrong input!\nChoose a new game name:");
        }

        getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_NAME, gameName));
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseNewGamePlayerNumber() {
        Integer numPlayer;
        String input =  playerMessage("Choose the number of players [max "+InputFormatChecker.getMaxPlayer()+" players"+"]:");
        numPlayer = InputFormatChecker.getNumFromString(input);

        while(numPlayer==null||numPlayer<2||numPlayer>InputFormatChecker.getMaxPlayer())
        {
            input =  playerMessage("Please insert a valid number of player!\nChoose the number of the player:");
            numPlayer = InputFormatChecker.getNumFromString(input);
        }

        getClientController().update(new Event(EventID.CHOOSE_NEW_GAME_PLAYER_NUMBER, numPlayer));
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void notifyGameHasStared() {
        playerMessage("Game started!\n");
      }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {

        String gameRoomTable = InputFormatChecker.getTableGameRoom(rooms);
        String answer;

        answer = playerMessage(gameRoomTable+"\nType the room name or insert the number of row: ");
        while(!InputFormatChecker.isGameRoomValid(gameRoomTable,rooms))
        {
            answer = playerMessage(gameRoomTable+"\nInvalid game room!\nType the room name or insert the number of row: ");
        }

        getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, answer));
    }

    @Override
    public void pickTiles(int availablePickNumber) {
        int x = 0, y = 0;
        ArrayList<Coordinates> list = new ArrayList<>();
        String[] coords;
        boolean attempt = true;

        for(int i = 0; i < availablePickNumber; i++){
            do {
                attempt = true;
                try {
                    coords = playerMessage("choose using the following format: x,y\n").split("\\,");
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);
                }
                catch (Exception e) {
                    System.out.println("invalid input, try again ");
                    attempt = false;
                }
            }while(!attempt);

            list.add(new Coordinates(x, y));

            if (i < 3 && playerMessage("done?").equals("y")) i = 4;
        }

        getClientController().update(new Event(EventID.PICK_TILES, list));
    }

    @Override
    public void chooseOrder(List<Tile> selection) {
        ArrayList<Tile> temp = new ArrayList<>();
        ArrayList<Integer> choosenIndex = new ArrayList<>();
        boolean imputIsValid = false;

        int index = 0;
        System.out.println("Choose order: ");

        for(int i = 0; i < selection.size(); i++){

            do{
                do {
                    imputIsValid = true;
                    try {
                        index = Integer.parseInt(playerMessage("Choose tile in " + i + " position: "));
                    }
                    catch (Exception e) {
                        System.out.println("invalid input, try again ");
                        imputIsValid = false;
                    }
                }while(!imputIsValid);


                if((index <selection.size() && index > -1) || choosenIndex.contains(index))
                    System.out.println("Invalid value, try again");

                else temp.add(selection.get(index));
            }while(!(index < selection.size() && index > -1) && !choosenIndex.contains(index));

            choosenIndex.add(index);
            temp.add(selection.get(index));
        }

        getClientController().update(new Event(EventID.PICK_TILES, temp));
    }

    @Override
    public void chooseColumn() {
        int column = 0;
        boolean attempt = true;

        do {
            attempt = true;
            try {
                column = Integer.parseInt(playerMessage("Choose column: "));
            } catch (Exception e) {
                System.out.println("invalid input, try again");
                attempt = false;
            }
        }while(!attempt);

        getClientController().update(new Event(EventID.CHOOSE_COLUMN, column));
    }

    @Override
    public void playerIsPlaying(String playerName) {
        System.out.println(playerName + "is currently playing his turn");
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {
        System.out.println(playerName + " recieved " + token + " points from commongoal");
    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {
        for(Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " recieved " + entry.getValue() + "points from Personal goal");
    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {
        for(Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " recieved " + entry.getValue() + "points from adjacents tiles");
    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {
        for(Map.Entry<String, Integer> entry : points.entrySet())
            System.out.println(entry.getKey() + " has " + entry.getValue() + " points");

        System.out.println("The winner is: " + winnerName);
    }

    @Override
    public void disconnected() {
        System.out.println("Disconnected from server. Trying to reconnect...");
    }

    @Override
    public void justScanChat() {
        while(chatMessage == null) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        String temp = chatMessage;
        chatMessage = null;
        getClientController().update(new Event(EventID.JUST_SCAN_CHAT, temp));
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void justPrintChat(String message) {
        System.out.println(message);
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
        while(true) {
            String temp = scanner.nextLine();
            if(temp.startsWith(prefix))
                chatMessage = temp.substring(prefix.length());
            else
                dataMessage = temp;
        }
    }

    /**
     * Method returns player inserted string (null if timout time is reached).
     *
     * @author Francesco Ostidich
     * @param timeOut is the timeout number in second
     * @param inputMessage is the message to print posting the requested data
     * @return player's written data
     */
    private String playerMessage(String inputMessage, int timeOut) {
        int i = timeOut + 1;
        System.out.print(inputMessage + " ");
        dataMessage = null;
        while(dataMessage == null && i > 0) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
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
     * @author Francesco Ostidich
     * @param  inputMessage is the message to print posting the requested data
     * @return player's written data
     */
    private String playerMessage(String inputMessage) {
        System.out.print(inputMessage + " ");
        dataMessage = null;
        while(dataMessage == null) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println();
        String temp = dataMessage;
        dataMessage = null;
        return temp;
    }

}
