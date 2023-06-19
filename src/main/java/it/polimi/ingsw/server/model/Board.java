package it.polimi.ingsw.server.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.general.exceptions.ConfigFileNotReadableException;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

/**
 * The Board class represents the game board in the game. It contains methods to manage and manipulate the board's state.
 * The board consists of a 2D array of tiles representing the spaces on the board.
 * It also includes a bag object for managing the tiles and an optional end game token.
 *
 * <p>The board can be constructed with a specified number of players. It initializes the spaces on the board based on the
 * provided configuration file.</p>
 *
 * <p>Note: This class provides various protected methods that can be used internally or extended by subclasses.</p>
 *
 * @author Edoardo Margarini
 */

public class Board {

    private static final int ROW_LENGTH = 9;

    private static final int COLUMN_LENGTH = 9;

    private final Tile[][] spaces;

    private final Bag bag = new Bag();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<EndGameToken> endGameToken;

    /**
     * Constructs a new board with the specified number of players.
     *
     * @param num is players number
     * @author Edoardo Margarini
     */
    public Board(int num) {
        spaces = new Tile[ROW_LENGTH][COLUMN_LENGTH];
        endGameToken = Optional.of(new EndGameToken());
        JsonElement spacesElement = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/boardSpacesMatrix.json"))));
        JsonObject spacesObject = spacesElement.getAsJsonObject();
        JsonArray jsonSpaces = spacesObject.get(String.valueOf(num)).getAsJsonArray();
        for (int i = 0; i < ROW_LENGTH; i++)
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                if (jsonSpaces.get(ROW_LENGTH * j + i).getAsInt() == 0) //22 = 9*2 + 4
                    spaces[i][j] = null;
                else if (jsonSpaces.get(ROW_LENGTH * j + i).getAsInt() == 1)
                    spaces[i][j] = Tile.EMPTY;
                else throw new ConfigFileNotReadableException("cannot read values else than 1 or 0");
            }
    }

    /**
     * Getter for row length.
     *
     * @return row length int
     * @author Francesco Ostidich
     */
    public static int getRowLength() {
        return ROW_LENGTH;
    }

    /**
     * Getter for column length.
     *
     * @return column length int
     * @author Francesco Ostidich
     */
    public static int getColumnLength() {
        return COLUMN_LENGTH;
    }

    /**
     * Sets the end game token for the board. The end game token is an optional token used to indicate that the game is ending or has ended.
     *
     * @param endGameToken the optional end game token to set for the board
     * @throws NullPointerException if the endGameToken parameter is null
     * @author Edoardo Margarini
     */

    protected void setEndGameToken(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<EndGameToken> endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     * Retrieves the 2D array representing the spaces on the board.
     *
     * @return the 2D array of tiles representing the spaces on the board
     * @author Edoardo Margarini
     */

    public Tile[][] getSpaces() {
        return spaces;
    }

    /**
     * Sets the specified tile in the board at the given coordinates.
     *
     * @param coords the coordinates of the space on the board
     * @param tile the tile to set in the space
     * @throws NullPointerException if the coords parameter is null
     * @author Edoardo Margarini
     */

    protected void setSpace(@NotNull Coordinates coords, Tile tile) {
        spaces[coords.x()][coords.y()] = tile;
    }

    /**
     * Retrieves the bag object associated with the board.
     *
     * @return the bag object
     * @author Edoardo Margarini
     */
    protected Bag getBag() {
        return bag;
    }

    /**
     * Retrieves the optional end game token associated with the board.
     *
     * @return the optional end game token, which may be empty
     * @author Edoardo Margarini
     */
    protected Optional<EndGameToken> getEndGameToken() {
        return endGameToken;
    }

    /**
     * Refills the board with new tiles.
     * Existing empty spaces on the board are filled with tiles drawn from the bag.
     * Any remaining tiles on the board are returned to the bag.
     * The refill process ensures that all empty spaces are filled with new tiles.
     * Once refilled, the board will have a complete set of tiles.
     *
     * @author Edoardo Margarini
     */
    protected void refill() {
        //puts back the ones left on the board
        emptyBoardInBag();
        for (int i = 0; i < ROW_LENGTH; i++)
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                if (spaces[i][j] == Tile.EMPTY) {
                    Tile t = bag.draw();
                    spaces[i][j] = t;
                }
            }
    }

    /**
     * Selects and retrieves the tiles corresponding to the specified coordinates on the board, if the selection is valid.
     *
     * @param selection a list of coordinates representing tiles on the board
     * @return a list of selected tiles if the selection is valid, an empty list otherwise
     * @author Edoardo Margarini
     */

    protected List<Tile> selectTiles(List<Coordinates> selection) {
        List<Tile> list = new LinkedList<>();
        if (checkSelection(selection)) {
            for (Coordinates e : selection)
                list.add(spaces[e.x()][e.y()]);
            emptyTiles(selection);
        }
        return list;
    }

    /**
     * Performs a series of checks to determine if a move is allowed.
     *
     * @param selection a list of coordinates representing tiles on the board
     * @return true if every check has a positive feedback, false if any check has a negative feedback
     * @author Edoardo Margarini
     */

    protected boolean checkSelection(@NotNull List<Coordinates> selection) {
        //checks the player has chosen max 3 tiles
        if (selection.size() > 3)
            return false;
        //checks the player has chosen available tiles (!=null !=Tile.EMPTY)
        for (Coordinates e : selection) {
            if(e.x() >= ROW_LENGTH-1 || e.y() >= COLUMN_LENGTH-1)
                return false;
            if (spaces[e.x()][e.y()] == null || spaces[e.x()][e.y()] == Tile.EMPTY)
                return false;
        }
        //checks the player has chosen tiles that has a free adjacent
        for (Coordinates e : selection) {
            if (!adjacentTile(e).contains(null) && !adjacentTile(e).contains(Tile.EMPTY))
                return false;
        }
        //checks the player has chosen 1 single tile, in this case all the tests made are sufficient
        if (selection.size() == 1)
            return true;
        //checks the player has chosen tiles in column or in row
        List<Integer> listX = new ArrayList<>();
        List<Integer> listY = new ArrayList<>();
        for (Coordinates e : selection) {
            listX.add(e.x());
            listY.add(e.y());
        }
        if (!listX.stream().allMatch(s -> s.equals(listX.get(0))) && !listY.stream().allMatch(s -> s.equals(listY.get(0))))
            return false;
        List<Integer> list;
        if (Objects.equals(listX.get(0), listX.get(1)))
            list = listY;
        else
            list = listX;
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != list.get(0) + i)
                return false;
        }
        return true;
    }

    /**
     * Deletes tiles from the board, making them ready to be placed on a shelf.
     *
     * @param selection a list of coordinates representing tiles on the board
     * @author Edoardo Margarini
     */

    private void emptyTiles(@NotNull List<Coordinates> selection) {
        selection.forEach((e) -> spaces[e.x()][e.y()] = Tile.EMPTY);
    }

    /**
     * Checks if the board is refillable.
     *
     * @return true if the board is refillable, false otherwise
     * @author Edoardo Margarini
     */

    protected boolean checkToRefill() {
        for (int i = 0; i < ROW_LENGTH; i++)
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                if (spaces[i][j] != null && spaces[i][j] != Tile.EMPTY && !isCompletelyFree(new Coordinates(i, j)))
                    return false;
            }
        return true;
    }

    /**
     * Checks if a tile has no other adjacent tiles.
     *
     * @param coords the coordinates of a space on the board
     * @return true if the tile has no adjacent tiles, false otherwise
     * @author Edoardo Margarini
     */

    private boolean isCompletelyFree(Coordinates coords) {
        for (Tile tile : Tile.values()) {
            if (tile != Tile.EMPTY) {
                if (adjacentTile(coords).contains(tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves a list of adjacent tiles to the specified coordinates on the board.
     *
     * @param coords the coordinates of a space on the board
     * @return a list of adjacent tiles
     * @author Edoardo Margarini
     */

    private @NotNull List<Tile> adjacentTile(@NotNull Coordinates coords) {
        List<Tile> adjTile = new LinkedList<>();
        if (coords.x() > ROW_LENGTH - 2)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x() + 1][coords.y()]);
        if (coords.x() < 1)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x() - 1][coords.y()]);
        if (coords.y() > COLUMN_LENGTH - 2)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()][coords.y() + 1]);
        if (coords.y() < 1)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()][coords.y() - 1]);
        return adjTile;
    }

    /**
     * Retrieves the tile corresponding to the specified coordinates on the board.
     *
     * @param coordinates the coordinates of the tile
     * @return the tile corresponding to the specified coordinates
     * @author Edoardo Margarini
     */
    protected Tile getTileInBoard(@NotNull Coordinates coordinates) {
        return spaces[coordinates.x()][coordinates.y()];
    }

    /**
     * Puts all the tiles of the board in the bag
     *
     * @author Edoardo Margarini
     */
    private void emptyBoardInBag() {
        for (int i = 0; i < ROW_LENGTH; i++)
            for (int j = 0; j < COLUMN_LENGTH; j++) {
                if (spaces[i][j] != null && spaces[i][j] != Tile.EMPTY) {
                    bag.addTile(spaces[i][j]);
                    spaces[i][j] = Tile.EMPTY;
                }
            }
    }

}