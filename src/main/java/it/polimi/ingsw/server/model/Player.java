package it.polimi.ingsw.server.model;

import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.general.exceptions.UnavailableInsertionException;

import java.util.List;

/**
 * A player is constructed on his unique name. It has his own shelf and points (initialized as 0 and only addable).
 * Players are called to pick, choose order, and insert tiles from board to shelf. The process is completely checked to
 * avoid illegal moves.
 *
 * @author Pietro Andrea Niedda
 */
public class Player {

    private final String name;

    private int points;

    private final PersonalGoal personalGoal;

    private final Shelf shelf;

    /**
     * Class constructor.
     *
     * @param name               is the player's name
     * @param personalGoalNumber is the sorted out number for player's personal goal
     * @author Pietro Andrea Niedda
     */
    public Player(String name, int personalGoalNumber) {
        this.name = name;
        points = 0;
        shelf = new Shelf();
        personalGoal = new PersonalGoal(personalGoalNumber);
    }

    /**
     * Getter for the name of the player.
     *
     * @return the name of the player
     * @author Pietro Andrea Niedda
     */
    protected String getName() {
        return name;
    }

    /**
     * Getter for the points scored from the player.
     *
     * @return the points scored from the player
     * @author Pietro Andrea Niedda
     */
    protected int getPoints() {
        return points;
    }

    /**
     * Getter for the personal goal of the player.
     *
     * @return the personal goal of the player
     * @author Pietro Andrea Niedda
     */
    protected PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Getter for the shelf of the player.
     *
     * @return the shelf of the player
     * @author Pietro Andrea Niedda
     */
    protected Shelf getShelf() {
        return shelf;
    }

    /**
     * This method increments the points of the player by a specified amount.
     *
     * @param points the amount of which player's points will be incremented
     * @author Pietro Andrea Niedda
     */
    protected void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * This method analyses the shelf of the player due to find the maximum number of tiles that can be inserted inside
     * the columns.
     *
     * @return the maximum number of selectable tiles to be inserted in the shelf
     * @author Pietro Andrea Niedda
     */
    protected int checkAvailablePickNumber() {
        int picks = 0, x;
        for (int i = 0; i < Shelf.getRowLength(); i++) {
            x = (Shelf.getColumnLength() - shelf.getTilesInColumn(i));
            if (x > picks)
                picks = x;
        }
        return Math.min(picks, 3);
    }

    /**
     * This method updates the shelf of the player, inserting the selected tiles, in the selected column, if possible.
     * If not possible, an exception wil be thrown, advising the player that the selection was not allowed.
     *
     * @param tiles the list of tiles that are to be inserted in the shelf
     * @param column the column in which to insert the tiles
     * @author Pietro Andrea Niedda
     */
    protected void insertTiles(List<Tile> tiles, int column) {
        if (!shelf.insertInColumn(tiles, column)) {
            throw new UnavailableInsertionException("cannot insert selection in shelf");
        }
    }

}
