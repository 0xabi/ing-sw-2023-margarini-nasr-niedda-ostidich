package it.polimi.ingsw.client.view;

import it.polimi.ingsw.general.GameRoom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InputFormatChecker {

    private static final int MAX_PLAYER = 4;

    private static final int LEN_COL_ROOM_NAME = 9;

    private static final int LEN_COL_CREATOR_NAME = 12;

    private static final int LEN_COL_PLAYERS = 7;

    private static final int LEN_PREFIX = 5;

    public static int getMaxPlayer() {
        return MAX_PLAYER;
    }

    /**
     * When IP address string is scanned, it needs to be checked if in right format.
     *
     * @param IP is the IP address string
     * @return check's outcome
     * @author Francesco Ostidich
     */
    public static boolean isIPAddress(@NotNull String IP) {
        if (IP.equals("localhost")) return true;
        String[] chunks;
        chunks = IP.split("\\.");
        if (chunks.length != 4)
            return false;
        try {
            for (String number : chunks) {
                int num = Integer.parseInt(number);
                if (num < 0 || num > 255)
                    return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Transforms string in integer.
     *
     * @param input is the input string
     * @return respective integer or null if no number is in the string
     * @author Abdullah Nasr
     */
    public static @Nullable Integer getNumFromString(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * The function gives a string with the information about the room, the string it's a row with different column
     * and each column contains different information about the room(name, creator's name, number of players ecc.)
     *
     * @param roomName          The room's name.
     * @param creatorName       The room creator's name.
     * @param players           Indicates the number of joined players / total players.
     * @param lenMaxRoomName    Indicates the number of character of the longest room's name to adjust the size of the table.
     * @param lenMaxCreatorName Indicates the number of character of the longest creator's name to adjust the size of the table.
     * @return A formatted row with different column with information about the room
     * @author Abdullah Nasr
     */
    public static @NotNull String getRowRoomTable(String roomName, String creatorName, String players, int lenMaxRoomName, int lenMaxCreatorName) {
        String rowRoomTable = "";
        //content header
        rowRoomTable += "| ";
        rowRoomTable = getStringWithColumn(roomName, lenMaxRoomName, rowRoomTable);
        //creator name
        rowRoomTable = getStringWithColumn(creatorName, lenMaxCreatorName, rowRoomTable);
        //players
        rowRoomTable = getStringWithColumn(players, LEN_COL_PLAYERS, rowRoomTable);
        //bottom header
        rowRoomTable += "\n";
        rowRoomTable += "+-" + "-".repeat(lenMaxRoomName) + "-+-" + "-".repeat(lenMaxCreatorName) + "-+-" + "-".repeat(LEN_COL_PLAYERS) + "-+\n";
        return rowRoomTable;
    }

    /**
     * Given a table row, you specify which column you want to add, specifying the maximum
     * number of characters this column can contain so that the function can do auto-sizing
     *
     * @param columnName       the column name we want to add
     * @param lenMaxColumnName the maximum number of characters that this column will contain
     * @param rowRoomTable     the row string to which we will add the column
     * @return the row room string attached with a new specified column
     * @author Abdullah Nasr
     */
    public static @NotNull String getStringWithColumn(@NotNull String columnName, int lenMaxColumnName, String rowRoomTable) {
        int distance;
        int marginDistance;
        distance = lenMaxColumnName - columnName.length();
        marginDistance = distance / 2;
        rowRoomTable += " ".repeat(marginDistance) + columnName;
        marginDistance = distance % 2 != 0 ? marginDistance + 1 : marginDistance;
        rowRoomTable += " ".repeat(marginDistance) + " | ";
        return rowRoomTable;
    }

    /**
     * @param rooms available in the server
     * @return a table representation of the rooms
     * @author Abdullah Nasr
     */
    public static @NotNull String getTableGameRoom(@NotNull List<GameRoom> rooms) {
        StringBuilder tableGameRoom = new StringBuilder();
        int lenMaxRoomName = 0;
        int lenMaxCreatorName = 0;
        int numRoom = 1;
        //get the max len of creator/room name to autosize the table
        for (GameRoom gr : rooms) {
            int currentLen = gr.gameRoomName().length();
            if (currentLen > lenMaxRoomName) {
                lenMaxRoomName = currentLen;
            }
            currentLen = gr.creatorName().length();
            if (currentLen > lenMaxCreatorName) {
                lenMaxCreatorName = currentLen;
            }
        }
        lenMaxRoomName = Math.max(lenMaxRoomName, LEN_COL_ROOM_NAME);
        lenMaxCreatorName = Math.max(lenMaxCreatorName, LEN_COL_CREATOR_NAME);
        //to insert also the number of the row in front of the room name
        //lenMaxRoomName += LEN_PREFIX;
        //upper header
        tableGameRoom.append("+-").append("-".repeat(lenMaxRoomName)).append("-+-").append("-".repeat(lenMaxCreatorName)).append("-+-").append("-".repeat(LEN_COL_PLAYERS)).append("-+").append("\n");
        tableGameRoom.append(getRowRoomTable("Room name", "Creator name", "Players", lenMaxRoomName, lenMaxCreatorName));
        for (GameRoom gr : rooms) {
            String players = gr.enteredPlayers().size() + "/" + gr.totalPlayers();
            tableGameRoom.append(getRowRoomTable(gr.gameRoomName(), gr.creatorName(), players, lenMaxRoomName, lenMaxCreatorName));
            numRoom++;
        }
        return tableGameRoom.toString();
    }

    /**
     * The user can indicate the game room writing the room's name or with the number of the table's row
     * that represent the room.
     *
     * @param answer The user answer
     * @param rooms  The rooms available in the server
     * @return false if the user's input is valid, true otherwise
     */
    public static boolean isGameRoomValid(String answer, @NotNull List<GameRoom> rooms) {
        for (GameRoom currentRoom : rooms) {
            if (currentRoom.gameRoomName().equalsIgnoreCase(answer))
                return true;
        }
        return answer.equals("back") || answer.equals("refresh");
    }

    public static boolean isNetworkType(@NotNull String scanned) {
        return (scanned.equals("RMI") || scanned.equals("Socket"));
    }

}
