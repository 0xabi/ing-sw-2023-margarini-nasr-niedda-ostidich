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
 * //TODO java doc is to be written
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
     * Class constructor
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
     * //TODO java doc is to be written
     *
     * @author Edoardo Margarini
     */
    protected void setEndGameToken(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<EndGameToken> endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Edoardo Margarini
     */
    public Tile[][] getSpaces() {
        return spaces;
    }

    /**
     * //TODO java doc is to be written
     *
     * @author Edoardo Margarini
     */
    protected void setSpace(@NotNull Coordinates coords, Tile tile) {
        spaces[coords.x()][coords.y()] = tile;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Edoardo Margarini
     */
    protected Bag getBag() {
        return bag;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Edoardo Margarini
     */
    protected Optional<EndGameToken> getEndGameToken() {
        return endGameToken;
    }

    /**
     * //TODO java doc is to be written
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
     * //TODO java doc is to be written
     *
     * @param selection
     * @return
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
     * A series of checks that allow to know if a move is allowed
     *
     * @param selection is a list of coords of the board
     * @return true if every check has positive feedback, false if one check has negative feedback
     * @author Edoardo Margarini
     */
    protected boolean checkSelection(@NotNull List<Coordinates> selection) {
        //checks the player has chosen max 3 tiles
        if (selection.size() > 3)
            return false;
        //checks the player has chosen available tiles (!=null !=Tile.EMPTY)
        for (Coordinates e : selection) {
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
     * Deletes tiles from board, they are ready to be placed on a shelf
     *
     * @param selection is a list of coords of the board
     * @author Edoardo Margarini
     */
    private void emptyTiles(@NotNull List<Coordinates> selection) {
        selection.forEach((e) -> spaces[e.x()][e.y()] = Tile.EMPTY);
    }

    /**
     * Checks if the board is refillable
     *
     * @return true or false
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
     * Checks if a tile has no other adjacent tiles
     *
     * @param coords of a space in the board
     * @return true or false
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
     * Adjacent tile
     *
     * @param coords of a space in the board
     * @return a list of adjacent Tile
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
     * //TODO java doc is to be written
     *
     * @param coordinates
     * @return
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