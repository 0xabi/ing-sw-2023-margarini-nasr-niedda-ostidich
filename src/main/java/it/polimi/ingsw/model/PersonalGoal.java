package it.polimi.ingsw.model;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class PersonalGoal {

    private final Set<PersonalGoal> alreadyChosen = new HashSet<>();

    private final Map<Coordinates, Tile> matches;

    public PersonalGoal(){
        //matches is constructed from one of a given set of matches
        matches = new HashMap<>();
    }

    public void assignPoints(Player player) {
        //assigns points to the player
        //uses method matchesShared()
    }

    private int matchesShared(Shelf shelf){
        //gives back the number from 0 to 6 of matches between the personal goal card and the shel
        return 0;
    }

}
