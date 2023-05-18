package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.resources.exceptions.UnavailableInsertionException;

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
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    protected String getName() {
        return name;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    protected int getPoints() {
        return points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    protected PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    protected Shelf getShelf() {
        return shelf;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param points
     * @author Pietro Andrea Niedda
     */
    protected void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * //TODO java doc is to be written
     *
     * @return
     * @author Pietro Andrea Niedda
     */
    protected int checkAvailablePickNumber() {
        int picks = 3;
        for (int i = 0; i < Shelf.getColumnLength(); i++)
            if ((Shelf.getColumnLength() - shelf.getTilesInColumn(i)) < picks)
                picks = Shelf.getRowLength() - shelf.getTilesInColumn(i);
        return picks;
    }

    /**
     * //TODO java doc is to be written
     *
     * @param tiles
     * @author Pietro Andrea Niedda
     */
    protected void insertTiles(List<Tile> tiles, int column) {
        if (!shelf.insertInColumn(tiles, column)) {
            throw new UnavailableInsertionException("cannot insert selection in shelf");
        }
    }

}
