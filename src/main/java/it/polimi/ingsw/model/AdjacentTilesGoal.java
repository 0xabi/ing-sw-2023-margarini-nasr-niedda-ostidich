package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.exceptions.ConfigFileNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * At the end of the game set it's needed to count how many groups and respective points are found in a player's shelf.
 * Groups' sizes and points are to be found in a map imported from json config file.
 *
 * @author Abdullah Nasr
 */
public class AdjacentTilesGoal {

    private static final Map<Integer, Integer> groupPoints = getGroupPointsFromFile();

    private static Tile[][] copy;

    private static final Queue<Coordinates> currAdjTiles = new LinkedList<>();


    /**
     * It assigns points to the player based on the groups it has managed to build up.
     *
     * @param player is the player to give points to
     * @author Abdullah Nasr
     */
    public static void assignPoints(@NotNull Player player) {
        player.addPoints(calculatePoints(player.getShelf()));
    }

    /**
     * count the number of tiles in the block in coordinate x,y
     * @param x coordinate x
     * @param y coordinate y
     * @author Abdullah Nasr
     */
    @SuppressWarnings("DuplicatedCode")
    private static int countTilesBlock(int x, int y){
        int count=0;
        int xTemp;
        int yTemp;

        currAdjTiles.addAll(adjacentTile(x, y));
        copy[x][y]=Tile.EMPTY;
        count++;

        while(currAdjTiles.size()>0) {

            xTemp=currAdjTiles.peek().x();
            yTemp=currAdjTiles.peek().y();
            currAdjTiles.remove();
            for(Coordinates e : adjacentTile(xTemp, yTemp)){
                if(!currAdjTiles.stream().toList().contains(e))
                    currAdjTiles.add(e);
            }
            copy[xTemp][yTemp]=Tile.EMPTY;
            count++;
        }
        return count;
    }

    /**
     * returns the points gained by the player with adjacent item tiles rules
     * @param shelf player's shelf
     * @return the total points gained with adjacent item tiles rules
     * @author Abdullah Nasr
     */
    private static int calculatePoints(@NotNull Shelf shelf) {
        int count;
        int totPoints =0;
        int maxTilesGroup = new ArrayList<>(groupPoints.keySet()).get(groupPoints.size()-1);

        copy = shelf.getPositions().clone();

        for (int i = 0; i < shelf.getRowNumber(); i++)
            for (int j = 0; j < shelf.getColumnNumber(); j++)
                if (copy[i][j] != Tile.EMPTY && copy[i][j] !=null) {
                    count = countTilesBlock(i,j);

                    if(count >= maxTilesGroup)
                        totPoints+= groupPoints.get(maxTilesGroup);
                    else if(groupPoints.containsKey(count))
                        totPoints+= groupPoints.get(count);
                }

        return totPoints;
    }

    /**
     * create a list of coordinates with the adjacent tiles of the tile in coordinate x,y
     * @param x coordinate x
     * @param y coordinate y
     * @return a list of coordinates with the adjacent tiles of tile in coordinate x,y
     * @author Abdullah Nasr
     */
    @SuppressWarnings("DuplicatedCode")
    private static @NotNull List<Coordinates> adjacentTile(int x, int y) {

        List<Coordinates> adjTile = new ArrayList<>();

        if (x <= 4)
            if(copy[x + 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x+1,y);
                adjTile.add(coords);
            }


        if (x>=1)
            if(copy[x - 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x-1,y);
                adjTile.add(coords);
            }

        if (y <= 3)
            if(copy[x ][y+1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x,y+1);
                adjTile.add(coords);
            }

        if (y >= 1)
            if(copy[x ][y-1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x,y-1);
                adjTile.add(coords);
            }

        return adjTile;
    }

    /**
     * Generates a map with group points, retrieving data from json config file.
     *
     * @return map of groups' points retrieved from json config file
     * @author Abdullah Nasr
     */
    private static @NotNull Map<Integer, Integer> getGroupPointsFromFile() {

        HashMap<Integer, Integer> map = new HashMap<>();
        Gson gson = new Gson();

        File input = new File("src/main/java/it/polimi/ingsw/model/configFiles/adjacentTilesGoalGroupPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new FileReader(input));
            map.putAll(gson.fromJson(pointsElement, new TypeToken<HashMap<Integer, Integer>>() {}.getType()));

        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalPointsMap not found");
        }
        return map;
    }

}
