package it.polimi.ingsw.model;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class PersonalGoal {

    private Set<PersonalGoal> alredyChoosen;
    private Map<Coordinates, Tile> matches;

    public PersonalGoal(){
        Map<Coordinates, Tile> matches = new HashMap<>();
        Set<PersonalGoal> alredyChoosen = new HashSet<>();
    }

    public void assignPoints(Player){

    }

    private int matchesShared(Shelf shelf){

    }
}
