package it.polimi.ingsw.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * At the end of the game set it's needed to count how many groups and respective points are found in a player's shelf.
 * Groups' sizes and points are to be found in a map imported from json config file.
 *
 * @author Abdullah Nasr
 */
public class AdjacentTilesGoal {

    private static final Map<Integer, Integer> groupPoints = getGroupPointsFromFile();

    /**
     * It assigns points to the player based on the groups it has managed to build up.
     *
     * @param player is the player to give points to
     * @author Francesco Ostidich
     */
    public static void assignPoints(Player player) {
        for(int groupSize: groupPoints.keySet())
            player.addPoints(calculateGroupPoints(groupSize, player.getShelf()));
    }

    /**
     * //TODO java doc is to be written
     *
     * @param groupSize
     * @param shelf
     * @return
     * @author Abdullah Nasr
     */
    private static int calculateGroupPoints(int groupSize, Shelf shelf) {
        //TODO method code is to be written
        return 0;
    }

    /**
     * Generates a map with group points, retrieving data from json config file.
     *
     * @return map of groups' points retrieved from json config file
     * @author Abdullah Nasr
     */
    private static @NotNull Map<Integer, Integer> getGroupPointsFromFile() {
        //TODO method code is to be written (reading from json)
        return new HashMap<>();
    }

}
