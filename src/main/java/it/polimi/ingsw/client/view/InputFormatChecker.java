package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.GameRoom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InputFormatChecker {

    private static final int MAX_PLAYER=4;

    private static final int LEN_COL_ROOM_NAME=9;

    private static final int LEN_COL_CREATOR_NAME=12;

    private static final int LEN_COL_PLAYERS=7;

    private static final int LEN_PREFIX = 5;

    public static int getLenColCreatorName() {
        return LEN_COL_CREATOR_NAME;
    }

    public static int getLenColPlayers() {
        return LEN_COL_PLAYERS;
    }

    public static int getLenColRoomName() {
        return LEN_COL_ROOM_NAME;
    }

    public static int getMaxPlayer() {
        return MAX_PLAYER;
    }

    public static int getLenPrefix() {
        return LEN_PREFIX;
    }

    /**
     * When IP address string is scanned, it needs to be checked if in right format.
     *
     * @author Francesco Ostidich
     * @param IP is the IP address string
     * @return check's outcome
     */
    public static boolean isIPAddress(@NotNull String IP) {
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
     * Transforms string in integer.
     *
     * @param input is the input string
     * @return respective integer
     * @throws NumberFormatException if no number is in the string
     * @author Abdullah Nasr
     */
    public static @Nullable Integer getNumFromString(String input)
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
    public static @NotNull String getRowRoomTable(String roomName, String creatorName, String players, int lenMaxRoomName, int lenMaxCreatorName)
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

    public static @NotNull String getString(@NotNull String roomName, int lenMaxRoomName, String rowRoomTable) {
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
    public static @NotNull String getTableGameRoom(@NotNull List<GameRoom> rooms)
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
    public static boolean isGameRoomValid(String answer, List<GameRoom> rooms)
    {
        return false;
    }

}
