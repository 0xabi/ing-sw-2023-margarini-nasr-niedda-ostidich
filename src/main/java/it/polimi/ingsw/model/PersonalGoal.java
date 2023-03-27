package it.polimi.ingsw.model;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class PersonalGoal {

    private final Set<PersonalGoal> alreadyChosen = new HashSet<>();

    private final Map<Tile, Coordinates> matches;

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
        for(Tile tile: matches.keySet()) {
            if(shelf.getPosition(matches.get(tile)).equals(tile)) {
                matchesNumber++;
            }
        }
        return matchesNumber;
    }

}
