package it.polimi.ingsw.server.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.resources.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * The personal goal card is represented with a map of six elements where the tiles are the keys of a coordinate.
 * In order to get a different personal goal from another player, GameModel.java extracts a different number for each
 * player that PersonalGoal.java gets and uses to pick a unique matches map.
 * A method to assign points to the player is called at the end of the game: it checks the matches number on the shelf
 * and adds the respective points to the player's points.
 *
 * @author Francesco Ostidich
 */
public class PersonalGoal {

    private static final int personalGoalNumber = 12;

    private final Map<Tile, Coordinates> matches = new HashMap<>();

    private static final Map<Integer, Integer> points = pointsConstructor();

    private final int personalGoalID;

    /**
     * Class constructor.
     *
     * @param personalGoalID is the number the GameModel has sorted to generate an exact personal goal
     * @throws ConfigFileNotFoundException if config json files are not readable by FileReader
     * @author Francesco Ostidich
     */
    public PersonalGoal(int personalGoalID) {
        this.personalGoalID = personalGoalID;
        matchesConstructor(personalGoalID);
    }

    /**
     * Constructor for attribute points from json file.
     *
     * @throws ConfigFileNotFoundException if file is not readable by FileReader
     * @author Francesco Ostidich
     */
    private static @NotNull Map<Integer, Integer> pointsConstructor() {
        Map<Integer, Integer> pointsTmp = new HashMap<>();

        Gson gson = new Gson();
        File input = new File("src/main/java/it/polimi/ingsw/resources/configFiles/personalGoalPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new FileReader(input));
            pointsTmp.putAll(gson.fromJson(pointsElement, new TypeToken<HashMap<Integer, Integer>>() {}.getType()));
            return pointsTmp;
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalPointsMap not found");
        }
    }

    /**
     * Constructor for attribute matches from json file.
     *
     * @author Francesco Ostidich
     * @param personalGoalNumber used to get a specific personal goal since different players can't have same personal goal
     * @throws ConfigFileNotFoundException if file is not readable by FileReader
     */
    private void matchesConstructor(int personalGoalNumber) {
        File input = new File("src/main/java/it/polimi/ingsw/resources/configFiles/personalGoalMatchesMap.json");
        try {
            JsonElement matchesElements = JsonParser.parseReader(new FileReader(input));
            JsonObject matchesObject = matchesElements.getAsJsonObject();

            JsonArray jsonCoordinates;
            for(Tile tile: Tile.values()) {
                if (tile!=Tile.EMPTY)
                {
                    jsonCoordinates = matchesObject.
                            get(String.valueOf(personalGoalNumber)).
                            getAsJsonObject().
                            get(tile.toString().toUpperCase()).
                            getAsJsonArray();
                    matches.put(tile, new Coordinates(jsonCoordinates.get(0).getAsInt(), jsonCoordinates.get(1).getAsInt()));
                }
            }
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalMatchesMap not found");
        }
    }

    /**
     * Checks the matches between player's personal goal and shelf.
     * Adds respective points to player's points.
     *
     * @author Francesco Ostidich
     * @param player is the player to check the matches and add points to
     */
    protected void assignPoints(@NotNull Player player) {
        player.addPoints(points.get(matchesShared(player.getShelf())));
    }

    /**
     * Checks the number of matches between player's personal goal and shelf, and returns it.
     *
     * @author Francesco Ostidich
     * @param shelf is the player's shelf
     * @return the number of matches
     */
    private int matchesShared(Shelf shelf) {
        int matchesNumber = 0;
        for(Tile tile: matches.keySet()) {
            if(shelf.getPosition(matches.get(tile)).equals(tile)) {
                matchesNumber++;
            }
        }
        return matchesNumber;
    }

    /**
     * Getter for personal goal points.
     *
     * @author Francesco Ostidich
     * @return points map
     */
    public static Map<Integer, Integer> getPoints() {
        return points;
    }

    /**
     * Getter for personal goal matches.
     *
     * @author Francesco Ostidich
     * @return matches map
     */
    public Map<Tile, Coordinates> getMatches() {
        return matches;
    }

    /**
     * Getter for personal goal ID number.
     *
     * @author Francesco Ostidich
     * @return personal goal ID number
     */
    public int getPersonalGoalID() {
        return personalGoalID;
    }

    /**
     * Getter for personal goal number.
     *
     * @author Francesco Ostidich
     * @return personal goal number
     */
    public static int getPersonalGoalNumber() {
        return personalGoalNumber;
    }
}
