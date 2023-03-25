package it.polimi.ingsw.model;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class PersonalGoal {

    private Set<PersonalGoal> alredyChoosen;
    private Map<Coordinate, Tile> matches;

    public PersonalGoal(){
        Map<Coordinate, Tile> matches = new HashMap<>();
        Set<PersonalGoal> alredyChoosen = new HashSet<>();
    }

    public void assignPoints(Player player){

    }

    private int matchesShared(Shelf shelf){

    }
}
