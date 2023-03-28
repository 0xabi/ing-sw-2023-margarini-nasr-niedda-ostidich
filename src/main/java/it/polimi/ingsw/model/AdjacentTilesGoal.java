package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * At the end of the game set it's needed to count how many groups and respective points are found in a player's shelf.
 * Groups' sizes and points are to be found in a map imported from json config file.
 *
 * @author
 */
public class AdjacentTilesGoal {

    private static final Map<Integer, Integer> groupPoints = new HashMap<>();

    public static void assignPoints(Player player) {
        for(int groupSize: groupPoints.keySet())
            player.addPoints(calculateGroupPoints(groupSize, player.getShelf()));
    }

    private static int calculateGroupPoints(int groupSize, Shelf shelf) {
        //calculation of points of a groupSize group
        return 0;
    }

}
