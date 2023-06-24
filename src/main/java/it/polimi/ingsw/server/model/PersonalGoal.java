package it.polimi.ingsw.server.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.general.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Tile;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
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

    private static final int PERSONAL_GOAL_NUMBER = 12;

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
        Gson gson = new Gson();
        JsonElement pointsElement = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/personalGoalPointsMap.json"))));
        return new HashMap<>(gson.fromJson(pointsElement, new TypeToken<HashMap<Integer, Integer>>() {
        }.getType()));
    }

    /**
     * Constructor for attribute matches from json file.
     *
     * @param personalGoalNumber used to get a specific personal goal since different players can't have same personal goal
     * @throws ConfigFileNotFoundException if file is not readable by FileReader
     * @author Francesco Ostidich
     */
    private void matchesConstructor(int personalGoalNumber) {
        JsonElement matchesElements = JsonParser.parseReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                ClassLoader.getSystemResourceAsStream("configFiles/personalGoalMatchesMap.json"))));
        JsonObject matchesObject = matchesElements.getAsJsonObject();
        JsonArray jsonCoordinates;
        for (Tile tile : Tile.values()) {
            if (tile != Tile.EMPTY) {
                jsonCoordinates = matchesObject.
                        get(String.valueOf(personalGoalNumber)).
                        getAsJsonObject().
                        get(tile.toString().toUpperCase()).
                        getAsJsonArray();
                matches.put(tile, new Coordinates(jsonCoordinates.get(0).getAsInt(), jsonCoordinates.get(1).getAsInt()));
            }
        }
    }

    /**
     * Checks the matches between player's personal goal and shelf.
     * Adds respective points to player's points.
     *
     * @param player is the player to check the matches and add points to
     * @author Francesco Ostidich
     */
    protected int assignPoints(@NotNull Player player) {
        int p = points.get(matchesShared(player.getShelf()));
        player.addPoints(p);
        return p;
    }

    /**
     * Checks the number of matches between player's personal goal and shelf, and returns it.
     *
     * @param shelf is the player's shelf
     * @return the number of matches
     * @author Francesco Ostidich
     */
    protected int matchesShared(Shelf shelf) {
        int matchesNumber = 0;
        for (Tile tile : matches.keySet()) {
            Coordinates coordinates = new Coordinates(matches.get(tile).x(), Shelf.getColumnLength() - 1 - matches.get(tile).y());
            if (shelf.getPosition(coordinates) == tile) {
                matchesNumber++;
            }
        }
        return matchesNumber;
    }

    /**
     * Getter for personal goal points.
     *
     * @return points map
     * @author Francesco Ostidich
     */
    public static Map<Integer, Integer> getPoints() {
        return points;
    }

    /**
     * Getter for personal goal matches.
     *
     * @return matches map
     * @author Francesco Ostidich
     */
    public Map<Tile, Coordinates> getMatches() {
        return matches;
    }

    /**
     * Getter for personal goal ID number.
     *
     * @return personal goal ID number
     * @author Francesco Ostidich
     */
    public int getPersonalGoalID() {
        return personalGoalID;
    }

    /**
     * Getter for personal goal number.
     *
     * @return personal goal number
     * @author Francesco Ostidich
     */
    public static int getPersonalGoalNumber() {
        return PERSONAL_GOAL_NUMBER;
    }

}
