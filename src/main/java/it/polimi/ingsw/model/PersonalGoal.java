package it.polimi.ingsw.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PersonalGoal {

    private final Set<PersonalGoal> alreadyChosen = new HashSet<>();

    private final Map<Tile, Coordinates> matches;

    private final Map<Integer, Integer> points = new HashMap<>();

    public PersonalGoal() {
        File input = new File("src/main/java/it/polimi/ingsw/model/configFiles/personalGoalPointsMap.json");
        try {
            JsonElement pointsElement = JsonParser.parseReader(new FileReader(input));
            JsonObject pointsObject = pointsElement.getAsJsonObject();

            points.keySet().addAll(pointsObject.keySet().stream().
                    map(Integer::valueOf).
                    collect(Collectors.toSet()));

            points.replaceAll((k, v) -> pointsObject.get(String.valueOf(k)).getAsInt());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //matches is constructed from one of a given set of matches (without duplicates)
        matches = new HashMap<>();
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
