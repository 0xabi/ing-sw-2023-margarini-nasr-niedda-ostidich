package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.GameRoom;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InputFormatCheckerTest {

    private static final List<GameRoom> gameRooms = new ArrayList<>();

    private static final List<String> players1 = new ArrayList<>();

    private static final List<String> players2 = new ArrayList<>();

    private static final List<String> players3 = new ArrayList<>();

    @BeforeClass
    public static void init() {
        players1.add("John Beppe");
        players1.add("Willy Willy");
        players2.add("Lukas");
        players2.add("Frank");
        players3.add("Pikachu");
        players3.add("isola");
        GameRoom gr1 = new GameRoom("Room name 1", "Creator name 1", 4, players1);
        GameRoom gr2 = new GameRoom("Room name 2", "Creator name 2", 3, players2);
        GameRoom gr3 = new GameRoom("Room name 3", "Creator name 3", 3, players3);
        gameRooms.add(gr1);
        gameRooms.add(gr2);
        gameRooms.add(gr3);
    }

    @Test
    public void isIpAddress_RandomInput_ReturnFalseIfInvalid() {
        assertTrue(InputFormatChecker.isIPAddress("localhost"));
        assertTrue(InputFormatChecker.isIPAddress("192.168.1.1"));
        assertFalse(InputFormatChecker.isIPAddress("test"));
        assertFalse(InputFormatChecker.isIPAddress("192.168"));
    }

    @Test
    public void getNumFromString_RandomInput_ReturnNullIfInvalid() {
        assertNull(InputFormatChecker.getNumFromString("test"));
        assertEquals((Integer) 30, InputFormatChecker.getNumFromString("30"));
    }

    @Test
    public void getRowRoomTable_RoomInfo_AFormattedRow() {
        String expected = """
                +------------------+----------------+---------+
                |    Room name     |  Creator name  | Players |\s
                +------------------+----------------+---------+
                | [1] Room name 1  | Creator name 1 |   2/4   |\s
                +------------------+----------------+---------+
                | [2] Room name 2  | Creator name 2 |   2/3   |\s
                +------------------+----------------+---------+
                | [3] Room name 3  | Creator name 3 |   2/3   |\s
                +------------------+----------------+---------+
                """;
        assertEquals(expected, InputFormatChecker.getTableGameRoom(gameRooms));
    }

    @Test
    public void isGameRoomValid_RandomInput_ReturnFalseIfInvalid() {
        assertTrue(InputFormatChecker.isGameRoomValid("1", gameRooms));
        assertTrue(InputFormatChecker.isGameRoomValid("room name 1", gameRooms));
        assertFalse(InputFormatChecker.isGameRoomValid("0", gameRooms));
        assertFalse(InputFormatChecker.isGameRoomValid("testName", gameRooms));
    }

}