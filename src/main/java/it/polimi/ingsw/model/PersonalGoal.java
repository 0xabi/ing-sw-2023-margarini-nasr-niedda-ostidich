package it.polimi.ingsw.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.exceptions.ConfigFileNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class PersonalGoal {

    private final Set<PersonalGoal> alreadyChosen = new HashSet<>();

    private final Map<Tile, Coordinates> matches = new HashMap<>();

    private final Map<Integer, Integer> points = new HashMap<>();

    public PersonalGoal(int personalGoalNumber) {
        pointsConstructor();
        matchesConstructor(personalGoalNumber);
    }

    private void pointsConstructor() {
        File input = new File("src/main/java/it/polimi/ingsw/model/configFiles/personalGoalPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new FileReader(input));
            JsonObject pointsObject = pointsElement.getAsJsonObject();

            points.keySet().addAll(pointsObject.keySet().stream().
                    map(Integer::valueOf).
                    collect(Collectors.toSet()));

            points.replaceAll((k, v) -> pointsObject.get(String.valueOf(k)).getAsInt());
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalPointsMap not found");
        }
    }

    private void matchesConstructor(int personalGoalNumber) {
        File input = new File("src/main/java/it/polimi/ingsw/model/configFiles/personalGoalMatchesMap.json");
        try {
            JsonElement matchesElements = JsonParser.parseReader(new FileReader(input));
            JsonObject matchesObject = matchesElements.getAsJsonObject();

            JsonArray jsonCoordinates;
            for(Tile tile: Tile.values()) {
                jsonCoordinates = matchesObject.
                        get(String.valueOf(personalGoalNumber)).
                        getAsJsonObject().
                        get(tile.toString()).
                        getAsJsonArray();
                matches.put(tile, new Coordinates(jsonCoordinates.get(0).getAsInt(), jsonCoordinates.get(1).getAsInt()));
            }
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("personalGoalMatchesMap not found");
        }
    }

    public void assignPoints(Player player) {
        player.addPoints(points.get(matchesShared(player.getShelf())));
    }

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
