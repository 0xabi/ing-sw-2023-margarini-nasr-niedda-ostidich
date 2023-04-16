package it.polimi.ingsw.server.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.resources.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.resources.exceptions.ConfigFileNotReadableException;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * //TODO java doc is to be written
 * @author Edoardo Margarini
 */
public class Board {

    private static final int rowLength = 9;

    private static final int columnLength = 9;

    private final Tile[][] spaces;

    private final Bag bag = new Bag();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<EndGameToken> endGameToken;

    /**
     * Class constructor
     * @author Edoardo Margarini
     * @param num is players number
     */
    public Board(int num) {
        spaces = new Tile[rowLength][columnLength];
        endGameToken = Optional.of(new EndGameToken());

        File input = new File("src/main/java/it/polimi/ingsw/resources/configFiles/boardSpacesMatrix.json");
        try {
            JsonElement spacesElement = JsonParser.parseReader(new FileReader(input));
            JsonObject spacesObject = spacesElement.getAsJsonObject();

            JsonArray jsonSpaces = spacesObject.get(String.valueOf(num)).getAsJsonArray();
            for(int i = 0; i < rowLength; i++)
                for(int j = 0; j < columnLength; j++) {
                    if(jsonSpaces.get(rowLength*j + i).getAsInt() == 0) //22 = 9*2 + 4
                        spaces[i][j] = null;
                    else if(jsonSpaces.get(rowLength*j + i).getAsInt() == 1)
                        spaces[i][j] = Tile.EMPTY;
                    else throw new ConfigFileNotReadableException("cannot read values else than 1 or 0");
                }
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalPointsMap not found");
        }
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     */
    protected void setEndGameToken(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<EndGameToken> endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     * @return
     */
    protected Tile[][] getSpaces() {
        return spaces;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     */
    protected void setSpace(@NotNull Coordinates coords, Tile tile){
        spaces[coords.x()][coords.y()]=tile;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     * @return
     */
    protected Bag getBag(){
        return bag;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     * @return
     */
    protected Optional<EndGameToken> getEndGameToken() {
        return endGameToken;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     */
    protected void refill() {
        //puts back the ones left on the board
        emptyBoardInBag();

        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (spaces[i][j]== Tile.EMPTY) {
                    Tile t=bag.draw();
                    spaces[i][j] = t;
                }
            }
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     * @param selection
     * @return
     */
    protected List<Tile> selectTiles(List<Coordinates> selection) {

        List<Tile> list = new LinkedList<>();

        if(checkSelection(selection)) {
            for(Coordinates e : selection)
                list.add(spaces[e.x()][e.y()]);

            emptyTiles(selection);

        }
        return list;
    }

    /**
     * A series of checks that allow to know if a move is allowed
     * @author Edoardo Margarini
     * @param selection is a list of coords of the board
     * @return true if every check has positive feedback, false if one check has negative feedback
     * */
    protected boolean checkSelection(@NotNull List<Coordinates> selection) {
        //checks the player has chosen max 3 tiles
        if(selection.size()>3)
            return false;

        //checks the player has chosen available tiles (!=null !=Tile.EMPTY)
        for(Coordinates e : selection) {
            if(spaces[e.x()][e.y()]==null || spaces[e.x()][e.y()]==Tile.EMPTY)
                return false;
        }

        //checks the player has chosen tiles that has a free adjacent
        for(Coordinates e : selection) {
            if(!adjacentTile(e).contains(null) && !adjacentTile(e).contains(Tile.EMPTY))
                return false;
        }

        //checks the player has chosen 1 single tile, in this case all the tests made are sufficient
        if(selection.size()==1)
            return true;

        //checks the player has chosen tiles in column or in row
        List<Integer> listX=new ArrayList<>();
        List<Integer> listY=new ArrayList<>();
        for(Coordinates e : selection){
            listX.add(e.x());
            listY.add(e.y());
        }
        if(!listX.stream().allMatch(s -> s.equals(listX.get(0))) && !listY.stream().allMatch(s -> s.equals(listY.get(0))))
            return false;


        List<Integer> list;

        if(Objects.equals(listX.get(0), listX.get(1)))
            list = listY;
        else
            list = listX;

        Collections.sort(list);

        for(int i=0;i<list.size();i++){
            if(list.get(i)!=list.get(0)+i)
                return false;
        }

        return true;
    }

    /**
     * Deletes tiles from board, they are ready to be placed on a shelf
     * @author Edoardo Margarini
     * @param selection is a list of coords of the board
     */
    private void emptyTiles(@NotNull List<Coordinates> selection) {
        selection.forEach((e)->spaces[e.x()][e.y()]=Tile.EMPTY);
    }

    /**
     * Checks if the board is refillable
     * @author Edoardo Margarini
     * @return true or false
     */
    protected boolean checkToRefill() {

        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j < columnLength; j++) {
                if(!isCompletelyFree(new Coordinates(i, j)) && spaces[i][j]!=null && spaces[i][j]!=Tile.EMPTY)
                    return false;
            }

        return true;
    }


    /**
     * Checks if a tile has no other adjacent tiles
     * @author Edoardo Margarini
     * @param coords of a space in the board
     * @return true or false
     */
    private boolean isCompletelyFree(Coordinates coords) {
        return adjacentTile(coords).contains(Tile.EMPTY) ||
                adjacentTile(coords).contains(null);
    }

    /**
     * Adjacent tile
     * @author Edoardo Margarini
     * @param coords of a space in the board
     * @return a list of adjacent Tile
     */
    private @NotNull List<Tile> adjacentTile(@NotNull Coordinates coords) {

        List<Tile> adjTile = new LinkedList<>();

        if(coords.x()>rowLength)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()+1][coords.y()]);

        if(coords.x()<1)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()-1][coords.y()]);

        if(coords.y()>columnLength)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()][coords.y()+1]);

        if(coords.y()<1)
            adjTile.add(null);
        else
            adjTile.add(spaces[coords.x()][coords.y()-1]);

        return adjTile;
    }

    /**
     * //TODO java doc is to be written
     * @author Edoardo Margarini
     * @param coordinates
     * @return
     */
    protected Tile getTileInBoard(@NotNull Coordinates coordinates) {
        return spaces[coordinates.x()][coordinates.y()];
    }

    /**
     * Puts all the tiles of the board in the bag
     * @author Edoardo Margarini
     */
    private void emptyBoardInBag() {
        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (spaces[i][j] != null && spaces[i][j] != Tile.EMPTY) {
                    bag.addTile(spaces[i][j]);
                    spaces[i][j] = Tile.EMPTY;
                }
            }
    }

}