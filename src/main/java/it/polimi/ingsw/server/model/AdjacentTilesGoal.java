package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.general.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
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
    public static int assignPoints(@NotNull Player player) {
        int p = calculatePoints(player.getShelf());
        player.addPoints(p);
        return p;
    }

    /**
     * count the number of tiles in the block in coordinate x,y
     *
     * @param x coordinate x
     * @param y coordinate y
     * @author Abdullah Nasr
     */
    @SuppressWarnings("DuplicatedCode")
    private static int countTilesBlock(int x, int y) {
        int count = 0;
        int xTemp;
        int yTemp;
        currAdjTiles.addAll(adjacentTile(x, y));
        copy[x][y] = null;
        count++;
        while (currAdjTiles.size() > 0) {
            xTemp = currAdjTiles.peek().x();
            yTemp = currAdjTiles.peek().y();
            currAdjTiles.remove();
            for (Coordinates e : adjacentTile(xTemp, yTemp)) {
                if (!currAdjTiles.stream().toList().contains(e))
                    currAdjTiles.add(e);
            }
            copy[xTemp][yTemp] = null;
            count++;
        }
        return count;
    }

    /**
     * returns the points gained by the player with adjacent item tiles rules
     *
     * @param shelf player's shelf
     * @return the total points gained with adjacent item tiles rules
     * @author Abdullah Nasr
     */
    private static int calculatePoints(@NotNull Shelf shelf) {
        int count;
        int totPoints = 0;
        int maxTilesGroup = new LinkedList<>(groupPoints.keySet()).get(groupPoints.size() - 1);
        copy = shelf.cloneShelf().getPositions();
        for (int i = 0; i < Shelf.getRowLength(); i++)
            for (int j = 0; j < Shelf.getColumnLength(); j++)
                if (copy[i][j] != null && copy[i][j] != null) {
                    count = countTilesBlock(i, j);
                    if (count >= maxTilesGroup)
                        totPoints += groupPoints.get(maxTilesGroup);
                    else if (groupPoints.containsKey(count))
                        totPoints += groupPoints.get(count);
                }
        return totPoints;
    }

    /**
     * create a list of coordinates with the adjacent tiles of the tile in coordinate x,y
     *
     * @param x coordinate x
     * @param y coordinate y
     * @return a list of coordinates with the adjacent tiles of tile in coordinate x,y
     * @author Abdullah Nasr
     */
    private static @NotNull List<Coordinates> adjacentTile(int x, int y) {
        List<Coordinates> adjTile = new LinkedList<>();
        if (x < Shelf.getRowLength() - 1)
            if (copy[x + 1][y] == copy[x][y] && copy[x][y] != null && copy[x][y] != null) {
                Coordinates coords = new Coordinates(x + 1, y);
                adjTile.add(coords);
            }
        if (x >= 1)
            if (copy[x - 1][y] == copy[x][y] && copy[x][y] != null && copy[x][y] != null) {
                Coordinates coords = new Coordinates(x - 1, y);
                adjTile.add(coords);
            }
        if (y < Shelf.getColumnLength() - 1)
            if (copy[x][y + 1] == copy[x][y] && copy[x][y] != null && copy[x][y] != null) {
                Coordinates coords = new Coordinates(x, y + 1);
                adjTile.add(coords);
            }
        if (y >= 1)
            if (copy[x][y - 1] == copy[x][y] && copy[x][y] != null && copy[x][y] != null) {
                Coordinates coords = new Coordinates(x, y - 1);
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
        //JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/adjacentTilesGoalGroupPointsMap.json"))));
        //Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/personalGoalMatchesMap.json")))
        //File input = new File("configFiles/adjacentTilesGoalGroupPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/adjacentTilesGoalGroupPointsMap.json"))));
            map.putAll(gson.fromJson(pointsElement, new TypeToken<HashMap<Integer, Integer>>() {
            }.getType()));
        } catch (RuntimeException e) {
            throw new ConfigFileNotFoundException("personalGoalPointsMap not readed");
        }
        return map;
    }

    /**
     * Getter for adjacent goal group points.
     *
     * @return groups points map
     * @author Abdullah Nasr
     */
    public static Map<Integer, Integer> getGroupPoints() {
        return groupPoints;
    }

}
