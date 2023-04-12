package it.polimi.ingsw.server.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.ConfigFileNotFoundException;
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

    private final Map<Tile, Coordinates> matches = new HashMap<>();

    private  final Map<Integer, Integer> points = new HashMap<>();

    /**
     * Class constructor.
     *
     * @param personalGoalNumber is the number the GameModel has sorted to generate an exact personal goal
     * @throws ConfigFileNotFoundException if config json files are not readable by FileReader
     * @author Francesco Ostidich
     */
    public PersonalGoal(int personalGoalNumber) {
        pointsConstructor();
        matchesConstructor(personalGoalNumber);
    }

    /**
     * Constructor for attribute points from json file.
     *
     * @throws ConfigFileNotFoundException if file is not readable by FileReader
     * @author Francesco Ostidich
     */
    private void pointsConstructor() {
        Gson gson = new Gson();
        File input = new File("src/main/java/it/polimi/ingsw/resources/configFiles/personalGoalPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new FileReader(input));
            points.putAll(gson.fromJson(pointsElement, new TypeToken<HashMap<Integer, Integer>>() {}.getType()));


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
    public void assignPoints(@NotNull Player player) {
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

}
