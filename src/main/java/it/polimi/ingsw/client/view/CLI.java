package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class CLI extends GameView {

    private static final int MAX_PLAYER=4;

    private static final int LEN_COL_ROOM_NAME=9;

    private static final int LEN_COL_CREATOR_NAME=12;

    private static final int LEN_COL_PLAYERS=7;

    public static final int LEN_PREFIX = 5;

    private String chatMessage = "";

    private String dataMessage = "";

    private final Thread scannerThread;

    /**
     * Class constructor.
     *
     * @author Francesco Ostidich
     */
    public CLI() {
        super();
        scannerThread = new Thread(this::scan);
        scannerThread.start();
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public void start() {
        System.out.println("My Shelfie\nLoading...");
    }

    /**
     * @author Francesco Ostidich
     */
    @Override
    public String chooseIPAddress() {
        String scannedIP = playerMessage("Choose IP address:");
        while(!isIPAddress(scannedIP)) {
            scannedIP = playerMessage("Wrong input!\nChoose IP address:");
        }
        return scannedIP;
    }

    /**
     * When IP address string is scanned, it needs to be checked if in right format.
     *
     * @author Francesco Ostidich
     * @param IP is the IP address string
     * @return check's outcome
     */
    private boolean isIPAddress(@NotNull String IP) {
        if(IP.equals("localhost")) return true;
        String[] chunks;
        chunks = IP.split("\\.");
        if(chunks.length!=4)
            return false;
        try{
            for(String number : chunks) {
                int num = Integer.parseInt(number);
                if (num<0 || num>255)
                    return false;
            }
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param input
     * @return
     * @author Abdullah Nasr
     */
    private @Nullable Integer getNumFromString(String input)
    {
        try
        {
            return Integer.parseInt(input);
        }
        catch(NumberFormatException e)
        {
            return null;
        }

    }

    @Override
    public String choosePlayerName() {
        String scannedIP = playerMessage("Choose player name:");
        while(scannedIP.isBlank()) {
            scannedIP = playerMessage("Wrong input!\nChoose player name:");
        }
        return scannedIP;
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public String chooseNewOrJoin() {
        String answer = playerMessage("Type [new] to create new game or [join] to join an existing game:");
        answer = answer.trim().toLowerCase();

        while(!answer.equals("new") && !answer.equals("join"))
        {
            answer = playerMessage("Wrong input!\nType [new] to create new game or [join] to join an existing game:");
            answer=answer.trim().toLowerCase();
        }

        return answer;
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public String chooseNewGameName() {
        String gameName = playerMessage("Choose a new game name:");
        while(gameName.isBlank())
        {
            gameName = playerMessage("Wrong input!\nChoose a new game name:");
        }

        return gameName;
    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public int chooseNewGamePlayerNumber() {
        Integer numPlayer;
        String input =  playerMessage("Choose the number of players [max "+MAX_PLAYER+" players"+"]:");
        numPlayer = getNumFromString(input);

        while(numPlayer==null||numPlayer<2||numPlayer>MAX_PLAYER)
        {
            input =  playerMessage("Please insert a valid number of player!\nChoose the number of the player:");
            numPlayer = getNumFromString(input);
        }

        return numPlayer;
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void notifyGameHasStared() {
        playerMessage("Game started!\n");
    }

    /**
     *
     * @param roomName
     * @param creatorName
     * @param players
     * @param lenMaxRoomName
     * @param lenMaxCreatorName
     * @return
     * @author Abdullah Nasr
     */
    private @NotNull String getRowRoomTable(String roomName, String creatorName, String players, int lenMaxRoomName, int lenMaxCreatorName)
    {
        int distance;
        int marginDistance;
        String rowRoomTable="";

        //content header
        rowRoomTable+="| ";

        rowRoomTable = getString(roomName, lenMaxRoomName, rowRoomTable);

        //creator name
        rowRoomTable = getString(creatorName, lenMaxCreatorName, rowRoomTable);

        //players
        rowRoomTable = getString(players, LEN_COL_PLAYERS, rowRoomTable);

        //bottom header
        rowRoomTable+="\n";
        rowRoomTable+="+-"+"-".repeat(lenMaxRoomName)+"-+-"+"-".repeat(lenMaxCreatorName)+"-+-"+"-".repeat(LEN_COL_PLAYERS)+"-+\n";

        return rowRoomTable;
    }

    private @NotNull String getString(@NotNull String roomName, int lenMaxRoomName, String rowRoomTable) {
        int distance;
        int marginDistance;
        distance = lenMaxRoomName - roomName.length();
        marginDistance=distance/2;

        rowRoomTable+=" ".repeat(marginDistance)+roomName;

        marginDistance = distance%2!=0 ? marginDistance+1 : marginDistance;

        rowRoomTable+=" ".repeat(marginDistance)+" | ";
        return rowRoomTable;
    }

    /**
     *
     * @param rooms
     * @return
     * @author Abdullah Nasr
     */
    public String getTableGameRoom(@NotNull List<GameRoom> rooms)
    {
        StringBuilder tableGameRoom = new StringBuilder();
        int lenMaxRoomName = 0;
        int lenMaxCreatorName = 0;
        int numRoom=1;

        //get the max len of creator/room name to autosize the table
        for(GameRoom gr : rooms)
        {
            int currentLen = gr.gameRoomName().length();
            if(currentLen>lenMaxRoomName)
            {
                lenMaxRoomName = currentLen;
            }

            currentLen = gr.creatorName().length();
            if(currentLen>lenMaxCreatorName)
            {
                lenMaxCreatorName = currentLen;
            }
        }

        lenMaxRoomName = Math.max(lenMaxRoomName, LEN_COL_ROOM_NAME);
        lenMaxCreatorName = Math.max(lenMaxCreatorName, LEN_COL_CREATOR_NAME);

        //to insert also the number of the row in front of the room name
        lenMaxRoomName+=LEN_PREFIX;

        //upper header
        tableGameRoom.append("+-").append("-".repeat(lenMaxRoomName)).append("-+-").append("-".repeat(lenMaxCreatorName)).append("-+-").append("-".repeat(LEN_COL_PLAYERS)).append("-+").append("\n");
        tableGameRoom.append(getRowRoomTable("Room name", "Creator name", "Players", lenMaxRoomName, lenMaxCreatorName));

        for (GameRoom gr : rooms)
        {
            String players = gr.enteredPlayers().size()+"/"+gr.totalPlayers();
            tableGameRoom.append(getRowRoomTable("[" + numRoom + "] " + gr.gameRoomName(), gr.creatorName(), players, lenMaxRoomName, lenMaxCreatorName));
            numRoom++;
        }

        return tableGameRoom.toString();
    }

    /**
     *
     * @param answer
     * @param rooms
     * @return
     */
    private boolean isGameRoomValid(String answer, List<GameRoom> rooms)
    {
        return false;
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public String chooseGameRoom(List<GameRoom> rooms) {

        String gameRoomTable = getTableGameRoom(rooms);
        String answer;

        answer = playerMessage(gameRoomTable+"\nType the room name or insert the number of row: ");
        while(!isGameRoomValid(gameRoomTable,rooms))
        {
            answer = playerMessage(gameRoomTable+"\nInvalid game room!\nType the room name or insert the number of row: ");
        }

        return answer;
    }

    @Override
    public List<Coordinates> pickTiles(int availablePickNumber) {
        return null;
    }

    @Override
    public List<Tile> chooseOrder(List<Tile> selection) {
        return null;
    }

    @Override
    public int chooseColumn() {
        return 0;
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {
    }

    @Override
    public String justScanChat() {
        while(chatMessage == null) {
            try {
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        String temp = chatMessage;
        chatMessage = null;
        return temp;
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
