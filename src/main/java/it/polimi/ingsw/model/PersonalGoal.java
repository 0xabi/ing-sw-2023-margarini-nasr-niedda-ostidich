package it.polimi.ingsw.model;

import netscape.javascript.JSObject;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class PersonalGoal {

    private final Set<PersonalGoal> alreadyChosen = new HashSet<>();

    private final Map<Coordinates, Tile> matches;

    private static final Map<Integer, Integer> points = null;

    public PersonalGoal() {

        //matches is constructed from one of a given set of matches (without duplicates)
        matches = new HashMap<>();
    }

    public void assignPoints(Player player) {
        player.addPoints(points.get(matchesShared(player.getShelf())));
    }

    private int matchesShared(Shelf shelf) {
        int matchesNumber = 0;
        for(Coordinates coordinates: matches.keySet()) {
            if(shelf.getPosition(coordinates).equals(matches.get(coordinates))) {
                matchesNumber++;
            }
        }
        return matchesNumber;
    }

}
